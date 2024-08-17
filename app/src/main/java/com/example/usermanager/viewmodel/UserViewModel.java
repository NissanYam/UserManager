package com.example.usermanager.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermanager.model.db.entitys.UserEntity;
import com.example.usermanager.model.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private LiveData<List<UserEntity>> userListLiveData;

    public UserViewModel(UserRepository repository) {
        this.userRepository = repository;
        this.userListLiveData = userRepository.getUserListLiveData();
    }
    // Fetch users from API
    public void fetchUsersFromApi() {
        userRepository.fetchUsersFromApi(1); // Start fetching from page 1
    }// Get the LiveData for observing the user list
    public LiveData<List<UserEntity>> getUserListLiveData() {
        return userListLiveData;
    }

    // CRUD Operations
    public void addUser(UserEntity userEntity) {
        userRepository.addUser(userEntity);
    }

    public void updateUser(UserEntity userEntity) {
        userRepository.updateUser(userEntity);
    }

    public void deleteUser(UserEntity userEntity) {
        userRepository.deleteUser(userEntity);
    }
}