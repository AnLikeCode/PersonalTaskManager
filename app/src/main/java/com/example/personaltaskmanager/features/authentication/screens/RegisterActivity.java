package com.example.personaltaskmanager.features.authentication.screens;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.personaltaskmanager.R;

/**
 * RegisterActivity
 * -------------------
 * Màn hình đăng ký tài khoản (local only)
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText etUser, etPass, etConfirm;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_auth_register);

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
        etUser = findViewById(R.id.et_register_username);
        etPass = findViewById(R.id.et_register_password);
        etConfirm = findViewById(R.id.et_register_confirm);

        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_to_login);
    }

    private void setupActions() {

        btnRegister.setOnClickListener(v -> {

            String u = etUser.getText().toString().trim();
            String p = etPass.getText().toString().trim();
            String c = etConfirm.getText().toString().trim();

            if (u.isEmpty()) {
                etUser.setError("Không được để trống");
                return;
            }
            if (p.isEmpty()) {
                etPass.setError("Không được để trống");
                return;
            }
            if (!p.equals(c)) {
                etConfirm.setError("Mật khẩu xác nhận không trùng!");
                return;
            }

            // TODO: Lưu DB nếu cần

            finish(); // Trở về Login
        });

        tvLogin.setOnClickListener(v -> finish());
    }
}
