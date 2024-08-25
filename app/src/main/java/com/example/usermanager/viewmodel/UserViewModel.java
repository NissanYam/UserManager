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
    private static final int PAGE_SIZE = 10; // Define your page size

    public UserViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "UserViewModel created");
        repository = UserRepository.getInstance(application);
    }

    public LiveData<List<User>> getUsersByPagination(int page) {
        Log.d(TAG, "getUsersByPagination called with page: " + page);
        int offset = page * PAGE_SIZE;
        return repository.getUsersByPagination(PAGE_SIZE, offset);
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
