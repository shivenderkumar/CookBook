package com.shivenderkumar.kitchenbook.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.db.KitchenBookDatabaseHelper;
import com.shivenderkumar.kitchenbook.onboardinglogin.OnBoardingActivity;


public class ProfileFragment extends Fragment {

    private KitchenBookDatabaseHelper kitchenBookDatabaseHelper;
    Button button_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        kitchenBookDatabaseHelper = KitchenBookDatabaseHelper.getInstance(getActivity());

        button_logout = view.findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        return view;
    }

    private void signOut(){
        kitchenBookDatabaseHelper.removeUser();
        kitchenBookDatabaseHelper.close();
        Intent intent = new Intent(getActivity(), OnBoardingActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}