package com.example.usermanager.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private OnUserEditListener onUserEditListener;

    public interface OnUserEditListener {
        void onUserEdit(User user);
    }
    public void setOnUserEditListener(OnUserEditListener listener) {
        onUserEditListener = listener;
    }

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
        ImageButton editButton = view.findViewById(R.id.btn_edit);
        editButton.setOnClickListener(v -> {
            // Handle edit button click
            if (onUserEditListener != null) {
                onUserEditListener.onUserEdit(currentUser);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Load user data if it's already available
        if (currentUser != null) {
            loadUserData(currentUser);
        }
    }

    public void setUser(User user) {
        currentUser = user;
        // Check if the view is already created, if yes, load data immediately
        if (getView() != null) {
            loadUserData(user);
        }
    }

    private void loadUserData(User user) {
        userFullNameTV.setText(String.format("%s %s", user.getFirst_name(), user.getLast_name()));
        userEmailTV.setText(user.getEmail());
        userIdTV.setText(String.valueOf(user.getId()));

        if (isAdded()) {
            Glide.with(this)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_user_placeholder)
                    .into(userImageView);
        }
    }
}
