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
    private static UserRepository instance;
    private final Application application;
    private final UsersApiService usersApiService;
    private final UserDAO userDAO;
    private static final Object LOCK = new Object();

    public UserRepository(Application application) {
        this.application = application;

        // Initialize the Retrofit API service
        String baseUrl = "https://reqres.in/api/";
        usersApiService = RetrofitClient.getClient(baseUrl).create(UsersApiService.class);

        // Build the Room database and add a callback for initial setup
        UsersDatabase usersDatabase = Room.databaseBuilder(application, UsersDatabase.class, "users_database")
                .addCallback(getDatabaseCallback())
                .build();

        // Initialize the DAO
        userDAO = usersDatabase.getUserDAO();
    }
    // Callback for initial database setup
    private RoomDatabase.Callback getDatabaseCallback() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.d(TAG, "Database created. Fetching users...");
                fetchUsers(1);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                Log.d(TAG, "Database opened.");
            }
        };
    }
    // Singleton pattern to ensure only one instance of the repository is created
    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new UserRepository(application);
                }
            }
        }
        return instance;
    }
    // Fetch users from the API and add them to the database
    private void fetchUsers(int page) {
        Log.d(TAG, "Fetching users from page: " + page);
        usersApiService.getUsers(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body().getData();
                    Log.d(TAG, "Received " + users.size() + " users from API.");
                    if (!users.isEmpty()) {
                        for (User user : users) {
                            addUser(user);
                        }
                        fetchUsers(page + 1);  // Fetch the next page of users
                    }
                } else {
                    // Handle HTTP error responses (e.g., 404, 500)
                    Log.e(TAG, "Server returned an error: " + response.code() + " " + response.message());
                    showToast("Server error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                // Handle network or other call-related failures (e.g., timeout, no connectivity)
                Log.e(TAG, "Failed to fetch users from API", t);
                if (t instanceof java.net.SocketTimeoutException) {
                    showToast("Request timed out. Please try again.");
                } else if (t instanceof java.net.UnknownHostException) {
                    showToast("No internet connection. Please check your network.");
                } else {
                    showToast("Failed to load users. Please try again later.");
                }
            }
        });
    }

    // Helper method to show a toast message on the main thread
    private void showToast(String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> Toast.makeText(application, message, Toast.LENGTH_SHORT).show());
    }

    // Methods for CRUD operations on the database
    // Add a user to the database
    public void addUser(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            Log.d(TAG, "Adding user: " + user.toString());
            userDAO.addUser(user);
            handler.post(() -> {
                Toast.makeText(application, "User " + user.getFirst_name() + " "+ user.getLast_name()+ " added successfully", Toast.LENGTH_SHORT).show();
            });
        });
    }
    // Get all users from the database
    public LiveData<List<User>> getUsersByPagination(int limit, int offset) {
        Log.d(TAG, "Retrieving users by pagination. Limit: " + limit + ", Offset: " + offset);
        return userDAO.getUsersByPagination(limit, offset);
    }
    // Delete a user from the database
    public void deleteUser(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Log.d(TAG, "Deleting user: " + user.toString());
            userDAO.deleteUser(user);
        });
    }
    // Update a user in the database
    public void updateUser(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Log.d(TAG, "Updating user: " + user.toString());
            userDAO.updateUser(user);
        });
    }
    // Get a user by their ID
    public LiveData<User> getUserById(int id) {
        Log.d(TAG, "Retrieving user by ID: " + id);
        return userDAO.getUserById(id);
    }
}
