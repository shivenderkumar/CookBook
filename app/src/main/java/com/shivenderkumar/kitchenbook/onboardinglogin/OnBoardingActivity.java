package com.shivenderkumar.kitchenbook.onboardinglogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.rd.PageIndicatorView;
import com.shivenderkumar.kitchenbook.R;
import com.shivenderkumar.kitchenbook.onboardinglogin.screens.EndFragment;
import com.shivenderkumar.kitchenbook.onboardinglogin.screens.FirstFragment;
import com.shivenderkumar.kitchenbook.onboardinglogin.screens.LoginFragment;
import com.shivenderkumar.kitchenbook.onboardinglogin.screens.SecondFragment;
import com.shivenderkumar.kitchenbook.onboardinglogin.screens.ThirdFragment;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    ArrayList<Fragment> fragmentArrayList;
    ViewPagerAdapter viewPagerAdapterOnboardingScreens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        ViewPager2 viewPager2 = findViewById(R.id.viewpager);
      //  TabLayout tabLayout = findViewById(R.id.tablayout);
        TextView textView_skip = findViewById(R.id.textview_skip);

        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new FirstFragment());
        fragmentArrayList.add(new SecondFragment());
        fragmentArrayList.add(new ThirdFragment());
        fragmentArrayList.add(new EndFragment());
        fragmentArrayList.add(new LoginFragment());

        viewPagerAdapterOnboardingScreens = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentArrayList);

        viewPager2.setAdapter(viewPagerAdapterOnboardingScreens);

        PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                if(position == 3 && positionOffset >0.5){
                    textView_skip.setVisibility(View.INVISIBLE);
                }
                else if(position <=3){
                    textView_skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        textView_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(4);
            }
        });

    }
}




//        new TabLayoutMediator(tabLayout, viewPager2,
//                (tab, position) -> tab.setText("")
//        ).attach();
