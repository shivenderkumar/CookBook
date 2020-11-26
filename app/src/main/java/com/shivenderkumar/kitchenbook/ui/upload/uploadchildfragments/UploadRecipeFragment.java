package com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.shivenderkumar.kitchenbook.R;

public class UploadRecipeFragment extends Fragment {
    boolean flag_backpressed = true;
    ImageButton imageButton;
    Button button_upload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_upload_recipe, container, false);
        changeActivityOnBackPressedFuntionality();

        init(root);
        setOnclickListerner();

        return root;
    }

    void changeActivityOnBackPressedFuntionality(){
        //implement on back pressed for fragment
        getActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if( flag_backpressed == true){   // backpressed callback flag true -> change backpressed to back to camerax_captured_image fragment
                    // in here you can do logic when backPress is clicked
                    //flag_backpressed = false;
                 //   Fragment fragment_camerax_imagecaptured = new CameraXCapturedImageFragment();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.remove(getParentFragmentManager().findFragmentByTag("TAG_FRAGMENT_UPLOAD_RECIPE")).commit();

                }
                else{  // backpressed callback flag false -> change backpressed to back to activity normal behaviour backpressed
                    flag_backpressed = false;
                    getActivity().onBackPressed();
                }
            }
        });
        ////////////////////////////////////////
    }

    void init(View root){
        imageButton = root.findViewById(R.id.imagebutton_back);
        button_upload = root.findViewById(R.id.button_upload);

    }

    void setOnclickListerner(){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // upload image

                //back to cameraX -> remove upload_fragment and remove cameraX_capturedImageFragment
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.remove(getParentFragmentManager().findFragmentByTag("TAG_FRAGMENT_CAMERAX_IMAGECAPTURED")).remove(getParentFragmentManager().findFragmentByTag("TAG_FRAGMENT_UPLOAD_RECIPE")).commit();

            }
        });
    }

    @Override
    public void onPause() {
        System.out.println("XXXXXXXXXXXXXXX ON PAUSE UPLOAD RECIPE FRAGMENT CALLED");
        super.onPause();
    }


    @Override
    public void onResume() {
        System.out.println("XXXXXXXXXXXXXXX ON RESUME UPLOAD RECIPE FRAGMENT CALLED");
        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("XXXXXXXXXXXXXXX ON START UPLOAD RECIPE FRAGMENT CALLED");

        super.onStart();
    }

    @Override
    public void onStop() {
        System.out.println("XXXXXXXXXXXXXXX ON STOP UPLOAD RECIPE FRAGMENT CALLED");

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("XXXXXXXXXXXXXXX ON DESTROYVIEW UPLOAD RECIPE FRAGMENT CALLED");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        System.out.println("XXXXXXXXXXXXXXX ON DESTROY UPLOAD RECIPE FRAGMENT CALLED");
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("XXXXXXXXXXXXXXX ON CREATE UPLOAD RECIPE FRAGMENT CALLED");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        System.out.println("XXXXXXXXXXXXXXX ON ATTACH UPLOAD RECIPE FRAGMENT CALLED");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        System.out.println("XXXXXXXXXXXXXXX ON DEATCH UPLOAD RECIPE FRAGMENT CALLED");
        super.onDetach();
    }


}