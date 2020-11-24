package com.shivenderkumar.kitchenbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shivenderkumar.kitchenbook.db.KitchenBookDatabaseHelper;
import com.shivenderkumar.kitchenbook.dialogfragment.WelcomeUserFragment;
import com.shivenderkumar.kitchenbook.model.User;
import com.shivenderkumar.kitchenbook.mysearch.SearchableActivity;

public class MainActivity extends AppCompatActivity {

    private KitchenBookDatabaseHelper kitchenBookDatabaseHelper;

    User user;
    BottomNavigationView navView;
    ImageButton imageButton_search;

    @Override
    protected void onStart() {
        kitchenBookDatabaseHelper = KitchenBookDatabaseHelper.getInstance(this);
        user = kitchenBookDatabaseHelper.getUser().get(0);

        showDialogFragment(user);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        imageButton_search = findViewById(R.id.imageview_search);
        imageButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showDialogFragment(User user){
        FragmentManager fm = getSupportFragmentManager();
        WelcomeUserFragment welcomeUserFragment = new WelcomeUserFragment();
        welcomeUserFragment.welcomeuser = user;
        welcomeUserFragment.show(fm, "welcome_dialog_fragment");

    }

    @Override
    protected void onDestroy() {
        kitchenBookDatabaseHelper.close();
        super.onDestroy();
    }

}
















//    private void loadIconImage(){
////        Glide.with(this)
////                .load(user.getImage_url())
////                .onlyRetrieveFromCache(true)
////                .into();
//    }


