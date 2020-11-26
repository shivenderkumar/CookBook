package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageProxy;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.ui.upload.viewmodelcamerx.ImageProxyViewModel;

import java.io.File;

public class CameraXCapturedImageFragment extends Fragment {

    File file_image;
    ImageButton imageButton_close, imageButton_check;
    ImageView imageview_preview;

    boolean flag_backpressed = true;

    // fragment communication MutableLivedata
    private ImageProxyViewModel imageProxyViewModel;
    private ImageProxy imageProxy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("YYYYYYYYYYYYYYY ONCREATEVIEW CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_x_captured_image, container, false);

        changeActivityOnBackPressedFuntionality();

        init(view);
        setOnClickListeners();

        observeImageProxyViewModel();

        return view;
    }

    private void observeImageProxyViewModel() {
        imageProxyViewModel = new ViewModelProvider(requireParentFragment()).get(ImageProxyViewModel.class);
        imageProxyViewModel.getMutableLiveDataIP().observe(getViewLifecycleOwner(), imageProxy1 -> {
            // get latest image captured by cameraX fragment
            System.out.println("YYYYYYYYYYYYYYY CAMERAX IMAGE CAPTUREDFRAGMENT NEW IMAGE PROXY CAPTURED IS OBSERVED IMAGEPROXY : "+ imageProxy1.toString()+" //// MLD "+imageProxyViewModel.getMutableLiveDataIP().toString());

        });
    }

    private void init(View root){
        imageButton_check = root.findViewById(R.id.imageview_upload_check);
        imageButton_close = root.findViewById(R.id.imageview_upload_close);
        imageview_preview = root.findViewById(R.id.imageview_preview);
    }

    private void setOnClickListeners() {
        imageButton_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        imageButton_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment_uploadRecipe = new UploadRecipeFragment();
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.parent_fragment_container,fragment_uploadRecipe,"TAG_FRAGMENT_UPLOAD_RECIPE").commit();

            }
        });

    }


    void changeActivityOnBackPressedFuntionality(){
        //implement on back pressed for fragment
        getActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if( flag_backpressed == true){   // backpressed callback flag true -> change backpressed to back to cameraxpreview fragment
                    // in here you can do logic when backPress is clicked
                    //flag_backpressed = false;
                   // Fragment fragment_camerax_imagecaptured = new CameraXCapturedImageFragment();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.remove(getParentFragmentManager().findFragmentByTag("TAG_FRAGMENT_CAMERAX_IMAGECAPTURED")).commit();

                }
                else{  // backpressed callback flag false -> change backpressed to back to activity normal behaviour backpressed
                    flag_backpressed = false;
                    getActivity().onBackPressed();
                }
            }
        });
        ////////////////////////////////////////
    }



    @Override
    public void onPause() {
        System.out.println("YYYYYYYYYYYYYYY ON PAUSE CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onPause();
    }


    @Override
    public void onResume() {
        System.out.println("YYYYYYYYYYYYYYY ON RESUME CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("YYYYYYYYYYYYYYY ON START CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");

        super.onStart();
    }

    @Override
    public void onStop() {
        System.out.println("YYYYYYYYYYYYYYY ON STOP CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("YYYYYYYYYYYYYYY ON DESTROYVIEW CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        System.out.println("YYYYYYYYYYYYYYY ON DESTROY CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("YYYYYYYYYYYYYYY ON CREATE CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        System.out.println("YYYYYYYYYYYYYYY ON ATTACH CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        System.out.println("YYYYYYYYYYYYYYY ON DEATCH CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");
        super.onDetach();
    }

}