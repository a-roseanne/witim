package com.witim;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    ActionBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bar = getSupportActionBar();
        bar.hide();
        bar.setSubtitle("witim");
        FragmentProject fragmentProject = new FragmentProject();
        FragmentTransaction ftProject = getSupportFragmentManager().beginTransaction();
        ftProject.replace(R.id.mainFrame, fragmentProject, "Project" );
        ftProject.commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_project:
                        FragmentProject fragmentProject = new FragmentProject();
                        FragmentTransaction ftProject = getSupportFragmentManager().beginTransaction();
                        ftProject.replace(R.id.mainFrame, fragmentProject, "Project" );
                        ftProject.commit();
                        return true;
                    case R.id.navigation_find:
                        FragmentFind fragmentFind = new FragmentFind();
                        FragmentTransaction ftFind = getSupportFragmentManager().beginTransaction();
                        ftFind.replace(R.id.mainFrame, fragmentFind, "Find" );
                        ftFind.commit();
                        return true;
                    case R.id.navigation_chat:
                        FragmentChat fragmentChat = new FragmentChat();
                        FragmentTransaction ftChat = getSupportFragmentManager().beginTransaction();
                        ftChat.replace(R.id.mainFrame, fragmentChat, "Chat" );
                        ftChat.commit();
                        return true;
                    case R.id.navigation_profile:
                        FragmentProfile fragmentProfile = new FragmentProfile();
                        FragmentTransaction ftProfile = getSupportFragmentManager().beginTransaction();
                        ftProfile.replace(R.id.mainFrame, fragmentProfile, "Profile" );
                        ftProfile.commit();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
