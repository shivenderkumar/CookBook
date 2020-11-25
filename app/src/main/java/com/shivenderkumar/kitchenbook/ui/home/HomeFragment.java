package com.shivenderkumar.kitchenbook.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.shivenderkumar.kitchenbook.MainActivity;
import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.mysearch.SearchableActivity;

public class HomeFragment extends Fragment {

    ImageButton imageButton_search;
    View fragment_main_nav_host;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTT ONCREATEVIEW HOMEFRAGMENT");

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        fragment_main_nav_host = getActivity().findViewById(R.id.nav_host_fragment);

        imageButton_search = root.findViewById(R.id.imageview_search);
        imageButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchableActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

}