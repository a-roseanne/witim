package com.witim;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegOrLoginActivity extends AppCompatActivity {

    Button login, register;

    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_or_login);

        bar = getSupportActionBar();
        bar.hide();

        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.btn_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerI = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerI);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginI = new Intent(getApplicationContext(),activity_login.class);
                startActivity(loginI);
            }
        });

    }
}
