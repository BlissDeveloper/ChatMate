package com.bliss.chatmate.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    MyUtils.goToActivity(SplashScreenActivity.this, LoginActivity.class);
                    finish();
                } catch (Exception e) {
                    Log.e("Avery", e.getMessage());
                }
            }
        };
        thread.start();
    }
}

