package com.example.usermanager.model.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.model.apiUser.models.UserResponse;
import com.example.usermanager.model.apiUser.service.UsersApiService;
import com.example.usermanager.model.db.dao.UserDAO;
import com.example.usermanager.model.db.database.UsersDatabase;
import com.example.usermanager.utils.RetrofitClient;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private final Application application;
    private final UsersApiService usersApiService;
    private final UserDAO userDAO;
    // LiveData to observe user list changes
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        this.application = application; // Initialize application context
        // Initialize Retrofit client for API requests
        String baseUrl = "https://reqres.in/api/";
        usersApiService = RetrofitClient.getClient(baseUrl).create(UsersApiService.class);
        // Initialize Room database with a callback
        RoomDatabase.Callback myCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.d(TAG, "Database created. Fetching users...");
                // Fetch users from API and populate the database when it is created
                fetchUsers(1);
            }
            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                Log.d(TAG, "Database opened.");
                // Optionally perform actions every time the database is opened
            }
        };
        // Build the Room database instance
        UsersDatabase usersDatabase = Room.databaseBuilder(application, UsersDatabase.class, "users_database")
                .addCallback(myCallback)
                .build();
        // Get DAO instance for user operations
        userDAO = usersDatabase.getUserDAO();
        // Initialize LiveData
        allUsers = userDAO.getAllUsers(); // Assuming this returns LiveData<List<User>>

    }
    // Fetch users from the API and save them to the local database
    private void fetchUsers(int page) {
        Log.d(TAG, "Fetching users from page: " + page);
        usersApiService.getUsers(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body().getData();
                    Log.d(TAG, "Received " + users.size() + " users from API.");
                    if (!users.isEmpty()) {
                        // Insert users into the database
                        for (User user : users) {
                            addUser(user);
                        }
                        // Fetch next page of users
                        fetchUsers(page + 1);
                    }
                    return;
                }
                Log.e(TAG, "Failed to get users. Response not successful.");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Failed to fetch users from API", t);
            }
        });
    }
    // Add user in the background and show a Toast message on the main thread
    public void addUser(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            Log.d(TAG, "Adding user: " + user.toString());
            userDAO.addUser(user);
            handler.post(() -> {
                Toast.makeText(application, "User added successfully", Toast.LENGTH_SHORT).show();
            });
        });
    }

    // Retrieve all users from the local database
    public LiveData<List<User>> getUsers() {
        Log.d(TAG, "Retrieving all users.");
        return allUsers;
    }

    // Delete a user from the local database
    public void deleteUser(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Log.d(TAG, "Deleting user: " + user.toString());
            userDAO.deleteUser(user);
        });
    }

    // Update user details in the local database
    public void updateUser(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Log.d(TAG, "Updating user: " + user.toString());
            userDAO.updateUser(user);
        });
    }

    // Retrieve a user by ID from the local database
    public LiveData<User> getUserById(int id) {
        Log.d(TAG, "Retrieving user by ID: " + id);
        return userDAO.getUserById(id);
    }
}