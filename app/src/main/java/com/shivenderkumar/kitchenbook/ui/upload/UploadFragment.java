package com.shivenderkumar.kitchenbook.ui.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shivenderkumar.kitchenbook.R;

public class UploadFragment extends Fragment {

    BottomNavigationView navView;

    Button btn_takepicture;
    ImageButton imageButton_back, imageButton_close, imageButton_check;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload, container, false);

        navView = getActivity().findViewById(R.id.nav_view);
        navView.setVisibility(View.GONE);

        btn_takepicture = root.findViewById(R.id.btn_takepicture);
        imageButton_back = root.findViewById(R.id.imageview_upload_back);
        imageButton_close = root.findViewById(R.id.imageview_upload_close);
        imageButton_check = root.findViewById(R.id.imageview_upload_check);

        btn_takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_back.setVisibility(View.GONE);
                imageButton_close.setVisibility(View.VISIBLE);
                imageButton_check.setVisibility(View.VISIBLE);
                btn_takepicture.setVisibility(View.GONE);
            }
        });

        imageButton_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_back.setVisibility(View.VISIBLE);
                imageButton_close.setVisibility(View.GONE);
                imageButton_check.setVisibility(View.GONE);
                btn_takepicture.setVisibility(View.VISIBLE);
            }
        });



        return root;
    }

    @Override
    public void onPause() {
        navView.setVisibility(View.VISIBLE);
        super.onPause();
    }
}