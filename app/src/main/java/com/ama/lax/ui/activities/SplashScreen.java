package com.ama.lax.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.ama.lax.MainActivity;
import com.ama.lax.R;
import com.ama.lax.repositories.LoginData;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new CountDownTimer(1000, 3500){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (!LoginData.getIsSkippedLogin(SplashScreen.this)){
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();

    }
}