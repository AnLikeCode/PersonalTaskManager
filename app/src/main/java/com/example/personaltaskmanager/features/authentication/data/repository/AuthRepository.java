package com.example.personaltaskmanager.features.authentication.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.personaltaskmanager.features.authentication.data.local.AuthDatabase;
import com.example.personaltaskmanager.features.authentication.data.local.dao.UserDao;
import com.example.personaltaskmanager.features.authentication.data.local.entity.UserEntity;
import com.example.personaltaskmanager.features.authentication.data.mapper.UserMapper;
import com.example.personaltaskmanager.features.authentication.data.model.User;

/**
 * AuthRepository
 * ---------------------
 * Lớp trung gian xử lý logic Authentication:
 *   - Login (kiểm tra user/password)
 *   - Register (kiểm tra trùng username)
 *   - Lưu trạng thái đăng nhập (SharedPreferences)
 *   - Logout
 *
 * Repository kết nối:
 *   UseCase ↔ Room Database ↔ SharedPreferences
 */
public class AuthRepository {

    private final UserDao userDao;
    private final SharedPreferences prefs;

    public AuthRepository(Context context) {
        userDao = AuthDatabase.getInstance(context).userDao();
        prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
    }

    /**
     * Đăng nhập
     * Trả về true nếu username tồn tại & password khớp
     */
    public boolean login(String username, String password) {

        UserEntity u = userDao.getUserByUsername(username);

        // Không có user
        if (u == null) return false;

        // Sai password
        if (!u.password.equals(password)) return false;

        // Lưu trạng thái đăng nhập
        prefs.edit()
                .putString("current_user", username)
                .apply();

        return true;
    }

    /**
     * Đăng ký tài khoản mới
     * Kiểm tra username đã tồn tại chưa
     */
    public boolean register(User user) {

        if (userDao.countUsername(user.username) > 0) {
            return false;
        }

        userDao.insertUser(UserMapper.toEntity(user));
        return true;
    }

    /**
     * Lấy user đang đăng nhập
     * Nếu không có → trả về null
     */
    public User getCurrentUser() {
        String username = prefs.getString("current_user", null);
        if (username == null) return null;

        UserEntity e = userDao.getUserByUsername(username);
        if (e == null) return null;

        return UserMapper.toModel(e);
    }

    /**
     * Logout → xoá trạng thái đăng nhập
     */
    public void logout() {
        prefs.edit().remove("current_user").apply();
    }
}
