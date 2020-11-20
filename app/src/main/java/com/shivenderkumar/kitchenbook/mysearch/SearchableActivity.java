package com.shivenderkumar.kitchenbook.mysearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.shivenderkumar.kitchenbook.R;

public class SearchableActivity extends AppCompatActivity{

    ListView listView;

    ImageButton imageButton_back;
    SearchView searchView;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        listView = findViewById(R.id.list);

        imageButton_back = findViewById(R.id.imageview_toolbar_back);
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableActivity.super.onBackPressed();
            }
        });

        searchView = findViewById(R.id.toolbar_searchview);
        searchView.clearFocus();

         //   SearchAdapter searchAdapter = doMySearch(query);
         //   setListAdapter(searchAdapter);


    }

    private void doMySearch(String query){
        // get data in aarayList from offLine SQLite
        // get data in aarayList from onLine

        // Merge ArrayList
        // pass ArrayList to Search Adapter

        // Return Adapter to Searchable Activivty
        // Retrun type of this function is adpater

    }

}