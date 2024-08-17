package com.example.usermanager.model.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.usermanager.model.db.entitys.UserEntity;

import java.util.List;

@Dao
public interface UserCRUD {
    @Insert
    void insert(UserEntity user);

    @Update
    void update(UserEntity user);

    @Delete
    void delete(UserEntity user);

    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    UserEntity getUserById(int id);
}
