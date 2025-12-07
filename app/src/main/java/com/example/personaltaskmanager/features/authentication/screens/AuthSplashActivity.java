package com.example.personaltaskmanager.features.authentication.screens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.personaltaskmanager.R;

/**
 * Màn Splash nhỏ cho module Authentication.
 * Chỉ hiển thị logo sau đó chuyển sang LoginActivity.
 */
public class AuthSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_auth_splash);

        setupStatusBar();

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }, 1500);
    }

    private void setupStatusBar() {
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);

        WindowInsetsControllerCompat wic =
                WindowCompat.getInsetsController(window, window.getDecorView());

        if (wic != null) {
            wic.setAppearanceLightStatusBars(true);
            wic.setAppearanceLightNavigationBars(true);
        }
    }
}
