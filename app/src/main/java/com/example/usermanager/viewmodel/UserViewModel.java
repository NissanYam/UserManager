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
    private final UserRepository repository;
    private static final int PAGE_SIZE = 10; // Define your page size

    public UserViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "UserViewModel created");
        repository = UserRepository.getInstance(application);
    }
    // Get users from the repository by pagination
    public LiveData<List<User>> getUsersByPagination(int page) {
        Log.d(TAG, "getUsersByPagination called with page: " + page);
        int offset = page * PAGE_SIZE;
        return repository.getUsersByPagination(PAGE_SIZE, offset);
    }
    // Add user to the repository
    public void addUser(User user){
        Log.d(TAG, "addUser called with user: " + user.toString());
        repository.addUser(user);
    }
    // Delete user from the repository
    public void deleteUser(User user){
        Log.d(TAG, "deleteUser called with user: " + user.toString());
        repository.deleteUser(user);
    }
    // Update user in the repository
    public void updateUser(User user){
        Log.d(TAG, "updateUser called with user: " + user.toString());
        repository.updateUser(user);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "UserViewModel cleared");
    }
    // Get user by id from the repository
    public User getUserById(int id) {
        Log.d(TAG, "getUserById called with id: " + id);
        return repository.getUserById(id).getValue();
    }
}
