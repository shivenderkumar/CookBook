package com.shivenderkumar.kitchenbook.onboardinglogin.screens;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.shivenderkumar.kitchenbook.MainActivity;
import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.db.KitchenBookDatabaseHelper;
import com.shivenderkumar.kitchenbook.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    int RC_SIGN_IN =0;

    GoogleSignInClient mGoogleSignInClient;

    private KitchenBookDatabaseHelper kitchenBookDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        kitchenBookDatabaseHelper = KitchenBookDatabaseHelper.getInstance(getContext());
        System.out.println("NUMBER OF USERS IN TABLE : "+kitchenBookDatabaseHelper.getUser().size());

        return view;
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println("xxxxxxxxxxx signInResult:failed code=" + e.getStatusCode()+" : "+e.getMessage());

            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account){

        if(account != null){
            System.out.println("TTTTTTTTTTTTTTTTTTT ACOUNT NAME     : "+account.getDisplayName());
            System.out.println("TTTTTTTTTTTTTTTTTTT ACOUNT EMAIL    : "+account.getEmail());
            System.out.println("TTTTTTTTTTTTTTTTTTT ACOUNT IMAGEURL : "+account.getPhotoUrl());
            System.out.println("TTTTTTTTTTTTTTTTTTT ACOUNT TOKEN    : "+account.getIdToken());

            User user = new User(account.getDisplayName(), account.getEmail(), account.getPhotoUrl().toString());

            kitchenBookDatabaseHelper.addUser(user);

            //if user logged in
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();

        }
        else{
            System.out.println("RRRRRRRRRRRRRRRRRR ACOUNT EMAIL  NOT FOUND");
        }

    }

    @Override
    public void onDestroy() {
        kitchenBookDatabaseHelper.close();
        super.onDestroy();
    }

}





