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
    public void addUser(User user);

    @Update
    public void updateUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("SELECT * FROM users")
    public LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    public LiveData<User> getUserById(int id);

}

