package com.shivenderkumar.kitchenbook.ui.home;

import android.os.Bundle;
import android.print.PrintAttributes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.shivenderkumar.kitchenbook.R;

public class HomeFragment extends Fragment {

    Toolbar toolbar_main_activity;
    View fragment_main_nav_host;
    ViewGroup.MarginLayoutParams p;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar_main_activity = getActivity().findViewById(R.id.toolbar_main_activity);
        toolbar_main_activity.setVisibility(View.VISIBLE);

        fragment_main_nav_host = getActivity().findViewById(R.id.nav_host_fragment);
        p = (ViewGroup.MarginLayoutParams) fragment_main_nav_host.getLayoutParams();
        setFragmentMargin(8);

        return root;
    }

    @Override
    public void onPause() {
        toolbar_main_activity.setVisibility(View.GONE);
        setFragmentMargin(0);

        super.onPause();
    }

    void setFragmentMargin(int top){
        p.setMargins(0, top, 0, 0);
        fragment_main_nav_host.requestLayout();
    }

}