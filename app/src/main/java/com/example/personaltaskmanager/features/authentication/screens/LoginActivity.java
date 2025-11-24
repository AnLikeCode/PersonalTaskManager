package com.example.personaltaskmanager.features.authentication.screens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.personaltaskmanager.R;

/**
 * LoginActivity
 * ----------------
 * Màn hình đăng nhập cục bộ (Offline only).
 * - Nhập username / password
 * - Chuyển sang RegisterActivity nếu chưa có tài khoản
 * - Chuyển sang TaskManagerMainActivity sau khi login thành công
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private Switch switchTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_auth_login);

        setupStatusBar();
        initViews();
        setupActions();
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

    private void initViews() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        switchTheme = findViewById(R.id.switch_theme);
    }

    private void setupActions() {

        // Đăng nhập (mock)
        btnLogin.setOnClickListener(v -> {

            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (user.isEmpty()) {
                etUsername.setError("Không được bỏ trống");
                return;
            }
            if (pass.isEmpty()) {
                etPassword.setError("Không được bỏ trống");
                return;
            }

            // TODO: validate thật nếu có DB
            startActivity(new Intent(this,
                    com.example.personaltaskmanager.features.task_manager.screens.TaskManagerMainActivity.class));
            finish();
        });

        // Chuyển sang đăng ký
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
