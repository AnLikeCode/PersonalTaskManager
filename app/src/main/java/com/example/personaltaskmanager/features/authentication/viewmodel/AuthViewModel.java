package com.example.personaltaskmanager.features.authentication.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.personaltaskmanager.features.authentication.data.model.User;
import com.example.personaltaskmanager.features.authentication.data.repository.AuthRepository;
import com.example.personaltaskmanager.features.authentication.domain.usecase.*;

/**
 * AuthViewModel
 * ----------------
 * Chịu trách nhiệm xử lý logic Authentication bằng cách gọi các UseCase:
 *  - Login
 *  - Register
 *  - Forgot password
 *  - Logout
 *  - Lấy current user (auto-login)
 *
 * Lưu ý:
 *  - Không chứa logic UI
 *  - Không thao tác Room trực tiếp
 */
public class AuthViewModel extends ViewModel {

    // Các UseCase tuân theo Clean Architecture
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final LogoutUseCase logoutUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final ForgotPasswordUseCase forgotUseCase;

    /**
     * Khởi tạo ViewModel
     * repo cần context → được truyền từ Activity
     */
    public AuthViewModel(Context context) {

        // Repository trung tâm
        AuthRepository repo = new AuthRepository(context);

        // Khởi tạo use cases
        loginUseCase = new LoginUseCase(repo);
        registerUseCase = new RegisterUseCase(repo);
        logoutUseCase = new LogoutUseCase(repo);
        getCurrentUserUseCase = new GetCurrentUserUseCase(repo);

        // Forgot password hiện tại không kết nối DB → giữ nguyên
        forgotUseCase = new ForgotPasswordUseCase();
    }

    /**
     * Xử lý Login
     */
    public boolean login(String username, String password) {
        return loginUseCase.execute(username, password);
    }

    /**
     * Xử lý Register
     */
    public boolean register(User user) {
        return registerUseCase.execute(user);
    }

    /**
     * Logout → xoá user trong Room + xoá SharedPreferences
     */
    public void logout() {
        logoutUseCase.execute();
    }

    /**
     * Lấy thông tin user đang đăng nhập (auto-login)
     * Nếu không có → trả về null
     */
    public User getCurrent() {
        return getCurrentUserUseCase.execute();
    }

    /**
     * Xử lý Forgot Password
     * Hiện tại chỉ kiểm email hợp lệ (mock)
     */
    public boolean forgotPassword(String email) {
        return forgotUseCase.execute(email);
    }
}
