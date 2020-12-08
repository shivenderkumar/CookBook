package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.ViewPort;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Rational;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.common.util.concurrent.ListenableFuture;
import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.ui.upload.UploadFragment;
import com.shivenderkumar.kitchenbook.ui.upload.viewmodelcamerx.ImageProxyViewModel;

import java.util.concurrent.ExecutionException;

import static android.view.Surface.ROTATION_0;

public class CamerXFragment extends Fragment {

    ImageButton btn_takepicture;
    ImageButton imageButton_back;
    ImageView imageView_flashButton, imageview_preview_square;

    ///CameraX
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    ImageCapture imageCapture;


    // fragment communication MutableLivedata
    private ImageProxy imageProxy;
    private ImageProxyViewModel imageProxyViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("ZZZZZZZZZZZZZZZ ONCREATEVIEW CAMERAXFRAGMENT CALLED");

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_camer_x, container, false);

        init(root);
        setClickListeners();
        init_preview_imagecamera_usecase(root);

        return root;
    }

    void init(View root) {
        btn_takepicture = root.findViewById(R.id.btn_takepicture);
        imageButton_back = root.findViewById(R.id.imageview_upload_back);
        previewView = root.findViewById(R.id.previewView);
        imageView_flashButton = root.findViewById(R.id.btn_flashmode);

        imageview_preview_square = root.findViewById(R.id.imageview_preview_square);

    }


    void setClickListeners() {
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_backClick();
            }
        });

        btn_takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_takepictureClick();
            }
        });
        imageView_flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFlashMode();
            }
        });

    }

    private void changeFlashMode() {
        if (!imageView_flashButton.isActivated()) {
            imageView_flashButton.setActivated(true);
            imageView_flashButton.setBackground(getResources().getDrawable(R.drawable.ic_baseline_flash_on_white_24));
            if (imageCapture != null) {
                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_ON);
            }

        } else {
            imageView_flashButton.setActivated(false);
            imageView_flashButton.setBackground(getResources().getDrawable(R.drawable.ic_baseline_flash_off_white_24));
            if (imageCapture != null) {
                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_OFF);
            }
        }
    }

    @SuppressLint({"RestrictedApi", "UnsafeExperimentalUsageError"})
    private void init_preview_imagecamera_usecase(View root) {
        ///camera X
        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                //preview use case
                Preview preview = new Preview.Builder()
                        .build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                int pw = previewView.getMeasuredWidth();
                int ph = previewView.getMeasuredHeight();
                System.out.println("11111111111 pw :"+pw+" ph :"+ph);

                // imagecapture use case
                imageCapture = new ImageCapture.Builder()
                        .setTargetRotation(root.getDisplay().getRotation())
                        .setTargetResolution(new Size(pw,pw))
                        .setFlashMode(ImageCapture.FLASH_MODE_OFF)
                        .build();

                // camera selector
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture, preview);

            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
                System.out.println("ERROR IN CAMERAX PROVIDER AVAILABILTY : " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(getContext()));

    }

    void btn_takepictureClick() {

        imageCapture.takePicture(ContextCompat.getMainExecutor(getContext()), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                imageProxy = image;
                makeFragmentTransaction();
                // image.close();
                // super.onCaptureSuccess(image);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                System.out.println("EEEEEEEEEEEEEE ERROR IN TAKE PUCTURE ON IMAGECAPTURES CALLBACK : " + exception.getMessage());
                super.onError(exception);
            }
        });

    }

    void makeFragmentTransaction() {
        setImageProxyToMLD();
        ftReplaceCameraXCIF();
    }

    private void setImageProxyToMLD() {
        //  System.out.println("ZZZZZZZZZZZZZZZ NEW IMAGE PROXY IS SET IMAGEPROXY : "+imageProxy.toString()+" // MLD :"+imageProxyViewModel.getMutableLiveDataIP().toString());

        imageProxyViewModel = new ViewModelProvider(requireParentFragment()).get(ImageProxyViewModel.class);
        imageProxyViewModel.setMutableLiveDataIP(imageProxy);

    }

    void ftReplaceCameraXCIF() {
        setImageProxyToMLD();
        System.out.println("ZZZZZZZZZZZZZZZ FT to CAMERAXCAPTUREDIMAGE FRAGMENT FROM CAMERAX FRAGMENT");

        Fragment fragment_camerax_imagecaptured = new CameraXCapturedImageFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.parent_fragment_container, fragment_camerax_imagecaptured, "TAG_FRAGMENT_CAMERAX_IMAGECAPTURED").commit();

    }

    void imageButton_backClick() {
        getActivity().onBackPressed();
    }



























    @Override
    public void onPause() {
        System.out.println("ZZZZZZZZZZZZZZZ ON PAUSE CAMERAXFRAGMENT CALLED");
        super.onPause();
    }


    @Override
    public void onResume() {
        System.out.println("ZZZZZZZZZZZZZZZ ON RESUME CAMERAXFRAGMENT CALLED");
        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("ZZZZZZZZZZZZZZZ ON START CAMERAXFRAGMENT CALLED");

        super.onStart();
    }

    @Override
    public void onStop() {
        System.out.println("ZZZZZZZZZZZZZZZ ON STOP CAMERAXFRAGMENT CALLED");

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("ZZZZZZZZZZZZZZZ ONDESTROYVIEW CAMERAXFRAGMENT CALLED");
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        System.out.println("ZZZZZZZZZZZZZZZ ON DESTROY CAMERAXFRAGMENT CALLED");
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("ZZZZZZZZZZZZZZZ ON CREATE CAMERAXFRAGMENT CALLED");
        super.onCreate(savedInstanceState);
    }

}