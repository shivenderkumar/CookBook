package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
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

public class CamerXFragment extends Fragment {

    Button btn_takepicture;
    ImageButton imageButton_back;

    ///CameraX
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    ImageCapture imageCapture;
    private ImageProxy imageProxy;

    // fragment communication MutableLivedata                       
    private ImageProxyViewModel imageProxyViewModel;

    ImageView imageView_top, imageView_bottom, imageView_flashButton;
    int sqaureWidth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("ZZZZZZZZZZZZZZZ ONCREATEVIEW CAMERAXFRAGMENT CALLED");

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_camer_x, container, false);

        init(root);
        displayTransparentImageView();
        setClickListeners();
        init_preview_imagecamera_usecase(root);

        return  root;
    }

    void init(View root) {
        btn_takepicture = root.findViewById(R.id.btn_takepicture);
        imageButton_back = root.findViewById(R.id.imageview_upload_back);
        previewView = root.findViewById(R.id.previewView);
        imageView_flashButton = root.findViewById(R.id.btn_flashmode);
        imageView_top = root.findViewById(R.id.imageview_top);
        imageView_bottom = root.findViewById(R.id.imageview_bottom);

    }

    void displayTransparentImageView() {
        sqaureWidth = getSqaureWidth();
        imageView_top.getLayoutParams().height = sqaureWidth;
        imageView_bottom.getLayoutParams().height = sqaureWidth;
        System.out.println(" SCREEN NEW HEIGHT WIDHT WIDHT WIDHT :"+(sqaureWidth+24));
        imageView_top.invalidate();
        imageView_bottom.invalidate();
    }

    int getSqaureWidth(){
        int activityWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int activityHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();

        activityWidth = activityWidth / 2;
        activityHeight = activityHeight / 2;

        return activityHeight - activityWidth -24;      // statusbar height is 24 according to google guidelines
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
        if( !imageView_flashButton.isActivated()){
            imageView_flashButton.setActivated(true);
            imageView_flashButton.setBackground(getResources().getDrawable(R.drawable.ic_baseline_flash_on_white_24));
            if(imageCapture != null){
               imageCapture.setFlashMode(ImageCapture.FLASH_MODE_ON);
            }
           // System.out.println("SSSSSSSSSSSSS FLASH STATE : TRUE PRESSED FLASH ON "+imageView_flashButton.isActivated());
        }
        else{
            imageView_flashButton.setActivated(false);
            imageView_flashButton.setBackground(getResources().getDrawable(R.drawable.ic_baseline_flash_off_white_24));
           // System.out.println("SSSSSSSSSSSSS FLASH STATE : NOT FALSE PRESSED FLASH OFF "+imageView_flashButton.isActivated());
            if(imageCapture != null){
                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_OFF);
            }
        }
    }

    private void init_preview_imagecamera_usecase(View root) {
        ///camera X
        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
      //  previewView = root.findViewById(R.id.previewView);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                //preview use case
                Preview preview = new Preview.Builder()
                        .build();

                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // imagecapture use case
                imageCapture = new ImageCapture.Builder()
                        .setTargetRotation(root.getDisplay().getRotation())
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

        imageCapture.takePicture(ContextCompat.getMainExecutor(getContext()),new ImageCapture.OnImageCapturedCallback(){
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                imageProxy = image;
                makeFragmentTransaction();
               // image.close();
               // super.onCaptureSuccess(image);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                System.out.println("EEEEEEEEEEEEEE ERROR IN TAKE PUCTURE ON IMAGECAPTURES CALLBACK : "+exception.getMessage());
                super.onError(exception);
            }
        });

    }

    void makeFragmentTransaction(){
        setImageProxyToMLD();
        ftReplaceCameraXCIF();
    }

    private void setImageProxyToMLD() {
      //  System.out.println("ZZZZZZZZZZZZZZZ NEW IMAGE PROXY IS SET IMAGEPROXY : "+imageProxy.toString()+" // MLD :"+imageProxyViewModel.getMutableLiveDataIP().toString());

        imageProxyViewModel = new ViewModelProvider(requireParentFragment()).get(ImageProxyViewModel.class);
        imageProxyViewModel.setMutableLiveDataIP(imageProxy);

        imageProxyViewModel.setSqaureWidht(sqaureWidth);
    }

    void ftReplaceCameraXCIF(){
        setImageProxyToMLD();
        System.out.println("ZZZZZZZZZZZZZZZ FT to CAMERAXCAPTUREDIMAGE FRAGMENT FROM CAMERAX FRAGMENT");

        Fragment fragment_camerax_imagecaptured = new CameraXCapturedImageFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.parent_fragment_container, fragment_camerax_imagecaptured,"TAG_FRAGMENT_CAMERAX_IMAGECAPTURED").commit();

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