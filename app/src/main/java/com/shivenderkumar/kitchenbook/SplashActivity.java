package com.shivenderkumar.kitchenbook;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;

import com.shivenderkumar.kitchenbook.db.KitchenBookDatabaseHelper;
import com.shivenderkumar.kitchenbook.model.User;
import com.shivenderkumar.kitchenbook.onboardinglogin.OnBoardingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SplashActivity extends AppCompatActivity {

    private KitchenBookDatabaseHelper kitchenBookDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                kitchenBookDatabaseHelper = KitchenBookDatabaseHelper.getInstance(SplashActivity.this);

                Intent intent;

                if(userLoggedIn() >= 1){
                    //if user logged in
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    //if user logged out
                    intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        },2000);

    }

    private int userLoggedIn(){

            List<User> users = new ArrayList<>();

            users = kitchenBookDatabaseHelper.getUser();

            for(User user : users){
                System.out.println("LOGGED IN USER DATA : ");
                System.out.println("USER NAME      : "+user.getName());
                System.out.println("USER EMAIL     : "+user.getEmail());
                System.out.println("USER PHOTO URL : "+user.getImage_url());
            }

            return users.size();

    }

    @Override
    protected void onDestroy() {
        kitchenBookDatabaseHelper.close();
        super.onDestroy();
    }

}