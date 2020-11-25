package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.ListenableFuture;
import com.shivenderkumar.kitchenbook.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CamerXFragment extends Fragment {

    Button btn_takepicture;
    ImageButton imageButton_back, imageButton_close, imageButton_check;

    ///CameraX
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;

    ImageCapture imageCapture;

    File f;
    ImageView imageview_preview;
    //////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_camer_x, container, false);

        init(root);
        setClickListeners();

        init_preview_imagecamera_usecase(root);

        return  root;
    }

    private void init_preview_imagecamera_usecase(View root) {
        ///camera X
        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());

        previewView = root.findViewById(R.id.previewView);

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

        //////////

    }

    void btn_takepictureClick() {
        imageButton_back.setVisibility(View.GONE);
        imageButton_close.setVisibility(View.VISIBLE);
        imageButton_check.setVisibility(View.VISIBLE);
        btn_takepicture.setVisibility(View.GONE);

        File file = new File(Environment.getExternalStorageDirectory() + "/pic.jpeg");
        f = file;

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(file).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(getContext()),
                new ImageCapture.OnImageSavedCallback() {

                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        // insert your code here.

                        imageview_preview.setVisibility(View.VISIBLE);
                        if (file.exists()) {
                            imageview_preview.setImageURI(Uri.fromFile(file));
                        }

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        // insert your code here.
                        System.out.println("EXCEPTION ERROR : " + exception.getMessage());
                    }
                });

    }

    void saveCaptureImageUseCase() {
        imageview_preview.setVisibility(View.GONE);

        imageButton_back.setVisibility(View.VISIBLE);
        imageButton_close.setVisibility(View.GONE);
        imageButton_check.setVisibility(View.GONE);
        btn_takepicture.setVisibility(View.VISIBLE);

        Toast.makeText(getContext(), "IMAGE FILE : " + f.getName(), Toast.LENGTH_LONG).show();

    }

    void imageButton_closeClick() {
        imageview_preview.setVisibility(View.INVISIBLE);

        imageButton_back.setVisibility(View.VISIBLE);
        imageButton_close.setVisibility(View.GONE);
        imageButton_check.setVisibility(View.GONE);
        btn_takepicture.setVisibility(View.VISIBLE);

        f.delete();

    }

    ////////////////////////

    void init(View root) {

        btn_takepicture = root.findViewById(R.id.btn_takepicture);
        imageButton_back = root.findViewById(R.id.imageview_upload_back);
        imageButton_close = root.findViewById(R.id.imageview_upload_close);
        imageButton_check = root.findViewById(R.id.imageview_upload_check);

        imageview_preview = root.findViewById(R.id.imageview_preview);

    }

    void setClickListeners() {
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_backClick();
            }
        });

        imageButton_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_closeClick();
            }
        });

        btn_takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_takepictureClick();
            }
        });

        imageButton_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCaptureImageUseCase();
            }
        });
    }

    void imageButton_backClick() {
        getActivity().onBackPressed();
    }

}