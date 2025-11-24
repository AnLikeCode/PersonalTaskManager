package com.example.personaltaskmanager.features.authentication.screens;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.personaltaskmanager.R;

/**
 * ForgotPasswordActivity
 * -------------------------
 * Màn hình quên mật khẩu (mock)
 * Chỉ hiển thị thành công – vì hệ thống đang là local.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_auth_forgot);

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
        etEmail = findViewById(R.id.et_forgot_email);
        btnSend = findViewById(R.id.btn_forgot_send);
    }

    private void setupActions() {

        btnSend.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            if (email.isEmpty()) {
                etEmail.setError("Không được bỏ trống");
                return;
            }

            Toast.makeText(this, "Đã gửi yêu cầu khôi phục!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
