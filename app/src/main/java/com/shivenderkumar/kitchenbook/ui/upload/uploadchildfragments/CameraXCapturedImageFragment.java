package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.ui.upload.UploadFragment;

import java.io.File;

public class CameraXCapturedImageFragment extends Fragment {

    File file_image;
    ImageButton imageButton_back, imageButton_close, imageButton_check;
    ImageView imageview_preview;

    boolean flag_backpressed = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("YYYYYYYYYYYYYYY ONCREATEVIEW CAMERAX IMAGE CAPTUREDFRAGMENT CALLED");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_x_captured_image, container, false);

        //implement on back pressed for fragment
        getActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if( flag_backpressed == true){   // backpressed callback flag true -> change backpressed to back to cameraxpreview fragment
                    // in here you can do logic when backPress is clicked
                    //flag_backpressed = false;
                    Fragment fragment_camerax_imagecaptured = new CameraXCapturedImageFragment();
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

        return view;
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
}