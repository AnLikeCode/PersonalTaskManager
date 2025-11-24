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
 * AuthSplashActivity
 * -------------------
 * Màn hình Splash đơn giản cho module Authentication.
 * Hiện logo/tên app vài giây → chuyển sang LoginActivity.
 *
 * Dùng WindowInsetsControllerCompat để set status bar trắng + icon đen.
 */
public class AuthSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_auth_splash);

        setupStatusBar();

        // Chờ 1.5 giây rồi chuyển sang LoginActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(AuthSplashActivity.this, LoginActivity.class));
            finish();
        }, 1500);
    }

    /**
     * Setup status bar trắng + icon đen theo chuẩn AndroidX.
     */
    private void setupStatusBar() {
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);

        WindowInsetsControllerCompat wic =
                WindowCompat.getInsetsController(window, window.getDecorView());

        if (wic != null) {
            wic.setAppearanceLightStatusBars(true);      // icon đen
            wic.setAppearanceLightNavigationBars(true);
        }
    }
}
