package com.shivenderkumar.kitchenbook.ui.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.ui.upload.uploadchildfragments.CamerXFragment;

public class UploadFragment extends Fragment {

    BottomNavigationView navView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        System.out.println("UPLOAD FRAGMENT XXXXXXXXXXXXXXXXXXXXXXX");

        navView = getActivity().findViewById(R.id.nav_view);
        navView.setVisibility(View.GONE);

        View root = inflater.inflate(R.layout.fragment_upload, container, false);

        Fragment fragment_camerax = new CamerXFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.parent_fragment_container, fragment_camerax).commit();

        return root;
    }

    @Override
    public void onDestroyView() {
        navView.setVisibility(View.VISIBLE);
        super.onDestroyView();
    }
}






























//Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
//bindPreview(cameraProvider);

//camera x
//    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
//        Preview preview = new Preview.Builder()
//                .build();
//
//        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                .build();
//
//        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//
//        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
//
//    }
//////////