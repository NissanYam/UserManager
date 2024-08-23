package com.example.usermanager.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.model.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private static final String TAG = "UserViewModel";  // Log tag
    private UserRepository repository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "UserViewModel created");
        repository = new UserRepository(application);
        allUsers = repository.getUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        Log.d(TAG, "getAllUsers called");
        return allUsers;
    }

    public void addUser(User user){
        Log.d(TAG, "addUser called with user: " + user.toString());
        repository.addUser(user);
    }

    public void deleteUser(User user){
        Log.d(TAG, "deleteUser called with user: " + user.toString());
        repository.deleteUser(user);
    }

    public void updateUser(User user){
        Log.d(TAG, "updateUser called with user: " + user.toString());
        repository.updateUser(user);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "UserViewModel cleared");
    }

    public User getUserById(int id) {
        Log.d(TAG, "getUserById called with id: " + id);
        return repository.getUserById(id).getValue();
    }
}
