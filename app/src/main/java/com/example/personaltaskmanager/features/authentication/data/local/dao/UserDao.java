package com.example.personaltaskmanager.features.authentication.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.personaltaskmanager.features.authentication.data.local.entity.UserEntity;

/**
 * DAO xử lý CRUD cho bảng Users.
 * Dùng Room để thao tác login/register với DB local.
 */
@Dao
public interface UserDao {

    /**
     * Thêm mới user.
     * Nếu username trùng → REPLACE (ghi đè user cũ).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(UserEntity user);

    /**
     * Lấy user theo username (login)
     */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    UserEntity getUserByUsername(String username);

    /**
     * Kiểm tra username đã tồn tại chưa (register)
     */
    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    int countUsername(String username);

    /**
     * Lấy user đầu tiên (dùng cho Auto-login)
     */
    @Query("SELECT * FROM users LIMIT 1")
    UserEntity getFirstUser();

    /**
     * Xoá toàn bộ user (logout hoặc reset)
     */
    @Query("DELETE FROM users")
    void deleteAll();
}
