package com.witim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    OnboardingViewPagerAdapter onboardingViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext,btnSkip, btnGetStarted;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if (restorePrefData()){
//            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(mainActivity);
//            finish();
//        }

        setContentView(R.layout.activity_intro);

        tabIndicator = findViewById(R.id.tab_indicator);
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);
        btnGetStarted = findViewById(R.id.btn_getStarted);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Welcome to witim", "Discover your ideal team members just from the touch of your fingertips", R.drawable.img1));
        mList.add(new ScreenItem("Find member base on role", "Are you looking for a hipster, hacker, or hustler?", R.drawable.img2));
        mList.add(new ScreenItem("Stay Connected", "Manage your team simultaneously", R.drawable.img3));

        screenPager = findViewById(R.id.screen_viewPager);
        onboardingViewPagerAdapter = new OnboardingViewPagerAdapter(this,mList);
        screenPager.setAdapter(onboardingViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size()-1){
                    loadLastScreen();
                }

            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1){
                    loadLastScreen();
                }else{
                    btnNext.setVisibility(View.VISIBLE);
                    btnSkip.setVisibility(View.VISIBLE);
                    btnGetStarted.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);

//                savePrefData();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);

//                savePrefData();
            }
        });


    }

    private boolean restorePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        boolean isOnboardingDone = pref.getBoolean("OnboardingDone", false);
        return  isOnboardingDone;
    }


    private void savePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("OnboardingDone", true);
        editor.commit();

    }
    private  void loadLastScreen(){
        btnNext.setVisibility(View.INVISIBLE);
        btnSkip.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

    }


}
