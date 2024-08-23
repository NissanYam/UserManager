package com.example.usermanager.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.viewmodel.UserViewModel;

public class UserPageFragment extends Fragment {

    private ImageView userImageView;
    private TextView userFullNameTV, userEmailTV, userIdTV;
    private UserViewModel userViewModel;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_page, container, false);
        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userImageView = view.findViewById(R.id.user_avatar);
        userFullNameTV = view.findViewById(R.id.user_full_name);
        userEmailTV = view.findViewById(R.id.user_email);
        userIdTV = view.findViewById(R.id.user_id);
        return view;
    }

    public void setUser(User user) {
        currentUser = user;
        userFullNameTV.setText(user.getFirst_name() + " " + user.getLast_name());
        userEmailTV.setText(user.getEmail());
        userIdTV.setText(String.valueOf(user.getId()));
        Glide.with(this).
                load(user.getAvatar()).
                placeholder(R.drawable.ic_user_placeholder).
                into(userImageView);
    }
}

