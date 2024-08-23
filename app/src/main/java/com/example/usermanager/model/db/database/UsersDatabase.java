package com.example.usermanager.model.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.model.db.dao.UserDAO;

@Database(entities = {User.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {
    public abstract UserDAO getUserDAO();
}


