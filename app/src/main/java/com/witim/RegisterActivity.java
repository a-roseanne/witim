package com.witim;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    ActionBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bar = getSupportActionBar();
        bar.setTitle("Register");
        bar.setDisplayHomeAsUpEnabled(true);
    }
}
