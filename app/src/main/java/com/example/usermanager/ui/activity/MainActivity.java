package com.example.usermanager.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.ui.fragment.AddUserFragment;
import com.example.usermanager.ui.fragment.UserListFragment;
import com.example.usermanager.ui.fragment.UserPageFragment;
import com.example.usermanager.viewmodel.UserViewModel;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements UserListFragment.OnUserSelectedListener,
        AddUserFragment.OnUserAddListener,
        UserPageFragment.OnUserEditListener {

    private ImageButton addUserButton;
    private UserListFragment userListFragment;
    private UserPageFragment userPageFragment;
    private AddUserFragment addUserFragment;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
        initializeViewModel();
        initializeFragments();
        setFragmentListeners();
        loadInitialFragments();
    }

    private void initializeUI() {
        addUserButton = findViewById(R.id.add_user_button);
        addUserButton.setOnClickListener(view -> {
            setAddUserButtonVisibility(false);
            addUserFragment.setUser(null);
            loadFragment(addUserFragment, R.id.fragment_user);
        });
    }

    private void initializeViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void initializeFragments() {
        userListFragment = new UserListFragment();
        userPageFragment = new UserPageFragment();
        addUserFragment = new AddUserFragment();
    }

    private void setFragmentListeners() {
        userListFragment.setOnUserSelectedListener(this);
        addUserFragment.setOnUserAddListener(this);
        userPageFragment.setOnUserEditListener(this);
    }

    private void loadInitialFragments() {
        loadFragment(userListFragment, R.id.fragment_users_list);
        loadFragment(userPageFragment, R.id.fragment_user);
    }

    private void loadFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    private void setAddUserButtonVisibility(boolean isVisible) {
        addUserButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onUserSelected(User user) {
        if (user != null) {
            Log.d("MainActivity", "User selected: " + user.getFirst_name() + " " + user.getLast_name());
            loadFragment(userPageFragment, R.id.fragment_user);
            userPageFragment.setUser(user);
            setAddUserButtonVisibility(true);
        }
    }

    @Override
    public void onUserEdit(User user) {
        if (user != null) {
            Log.d("MainActivity", "User edit: " + user.getFirst_name() + " " + user.getLast_name());
            setAddUserButtonVisibility(false);
            loadFragment(addUserFragment, R.id.fragment_user);
            addUserFragment.setUser(user);
        }
    }

    @Override
    public void onUserAdd(User user) {
        Log.d("MainActivity", "User added: " + user.getFirst_name() + " " + user.getLast_name());
        onUserSelected(user);
    }
}
