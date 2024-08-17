package com.example.usermanager.model.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.model.apiUser.models.UserListResponse;
import com.example.usermanager.model.apiUser.service.UserApiService;
import com.example.usermanager.model.db.dao.UserCRUD;
import com.example.usermanager.model.db.database.AppDatabase;
import com.example.usermanager.model.db.entitys.UserEntity;
import com.example.usermanager.utils.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserApiService userApiService;
    private UserCRUD userCRUD;
    private MutableLiveData<List<UserEntity>> userListLiveData = new MutableLiveData<>();

    public UserRepository(Context context) {
        userApiService = RetrofitClient.getApiService();
        userCRUD = AppDatabase.getDatabase(context).userCRUD();
    }
    public void fetchUsersFromApi(int page) {
        userApiService.getUsers(page).enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body().getUsers();
                    if (users != null && !users.isEmpty()) {
                        // Insert users into the local database
                        insertUsers(users);

                        // Fetch the next page recursively
                        fetchUsersFromApi(page + 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                // Handle failure, possibly show a message to the user
            }
        });
    }

    // Retrieve LiveData object for observing the user list
    public LiveData<List<UserEntity>> getUserListLiveData() {
        return userListLiveData;
    }
    // Convert API User to Entity User and insert into Room database
    private void insertUsers(List<User> users) {
        for (User user : users) {
            this.insertUser(user);
        }
    }
    private void insertUser(User user){
        UserEntity userEntity = new UserEntity(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAvatar());
        // Insert into the database
        userCRUD.insert(userEntity);
    }

    // CRUD Operations for adding, updating, and deleting users
    public void addUser(UserEntity userEntity) {
        new Thread(() -> {
            userCRUD.insert(userEntity);

            // Fetch updated users from the database and update LiveData
            List<UserEntity> allUsers = userCRUD.getAllUsers();
            userListLiveData.postValue(allUsers);
        }).start();
    }

    public void updateUser(UserEntity userEntity) {
        new Thread(() -> {
            userCRUD.update(userEntity);

            // Fetch updated users from the database and update LiveData
            List<UserEntity> allUsers = userCRUD.getAllUsers();
            userListLiveData.postValue(allUsers);
        }).start();
    }

    public void deleteUser(UserEntity userEntity) {
        new Thread(() -> {
            userCRUD.delete(userEntity);

            // Fetch updated users from the database and update LiveData
            List<UserEntity> allUsers = userCRUD.getAllUsers();
            userListLiveData.postValue(allUsers);
        }).start();
    }
}