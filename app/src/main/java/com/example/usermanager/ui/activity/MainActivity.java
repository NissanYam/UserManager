package com.example.usermanager.ui.activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.ui.fragment.AddUserFragment;
import com.example.usermanager.ui.fragment.UserListFragment;
import com.example.usermanager.ui.fragment.UserPageFragment;
import com.example.usermanager.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity implements UserListFragment.OnUserSelectedListener, AddUserFragment.OnUserAddListener {
    FloatingActionButton addUserButton;
    UserListFragment userListFragment;
    UserPageFragment userPageFragment;
    AddUserFragment addUserFragment;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize UI elements
        addUserButton = findViewById(R.id.add_user_button);
        // Initialize view model
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // Initialize fragments
        userListFragment = new UserListFragment();
        userPageFragment = new UserPageFragment();
        addUserFragment = new AddUserFragment();
        // Set listeners
        userListFragment.setOnUserSelectedListener(this);
        addUserFragment.setOnUserAddListener(this);
        // Load the initial fragment
        loadFragment(userListFragment, R.id.fragment_users_list);
        loadFragment(userPageFragment, R.id.fragment_user);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserButton.setVisibility(View.GONE);
                loadFragment(addUserFragment, R.id.fragment_user);
            }
        });
    }

    private void loadFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    @Override
    public void onUserSelected(User user) {
        if (user != null) {
            Log.d("MainActivity", "User selected: " + user.getFirst_name() + " " + user.getLast_name());
            loadFragment(this.userPageFragment, R.id.fragment_user);
            userPageFragment.setUser(user);
        }
    }

    @Override
    public void OnUserAddListener(User user) {
        Log.d("MainActivity", "User added: " + user.getFirst_name() + " " + user.getLast_name());
        addUserButton.setVisibility(View.VISIBLE);
        onUserSelected(user);
    }
}
