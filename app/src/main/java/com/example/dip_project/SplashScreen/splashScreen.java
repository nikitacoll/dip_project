package com.example.dip_project.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dip_project.Activity.Authorization;
import com.example.dip_project.R;

public class splashScreen extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent intent = new Intent(this, Authorization.class);
        Runnable splashDelay = () -> startActivity(intent);
        handler.postDelayed(splashDelay, 2000);
    }
}