package com.shivenderkumar.kitchenbook.dialogfragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.shivenderkumar.kitchenbook.MainActivity;
import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.model.User;

public class WelcomeUserFragment extends DialogFragment {

    public User welcomeuser;
    TextView textview_username;
    CircularImageView imageview_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome_user, container, false);

        textview_username = view.findViewById(R.id.textview_username);
        imageview_user = view.findViewById(R.id.imageview_user);

        textview_username.setText(welcomeuser.getName());

        Uri personPhoto = Uri.parse(welcomeuser.getImage_url());
        Glide
                .with(getActivity())
                .load(personPhoto.toString())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageview_user);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setWindowAnimations(R.style.dialog_slide_animation);                     //dialog&#95;slide&#95;animation);
    }

}