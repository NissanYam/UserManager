package com.example.usermanager.model.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.usermanager.model.apiUser.models.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> getUserById(int id);

    @Query("SELECT * FROM users LIMIT :limit OFFSET :offset")
    LiveData<List<User>> getUsersByPagination(int limit, int offset);

}

