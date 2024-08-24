package com.example.usermanager.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.viewmodel.UserViewModel;

public class AddUserFragment extends Fragment {
    // Member variables for views and user data
    private ImageView userImage;
    private EditText firstNameInput, lastNameInput, emailInput, idInput, avatarUrlInput;
    private Button saveButton;
    private UserViewModel userViewModel;
    private OnUserAddListener listener;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        // Initialize views here
        initializeViews(view);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSaveButtonClickListener();
        setupAvatarUrlInputWatcher();
    }
    @Override
    public void onStart() {
        super.onStart();
        updateUserFields();  // Update user fields when the fragment is started
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUserFields();  // Update user fields when the fragment is resumed
    }
    @Override
    public void onPause() {
        super.onPause();
        clearInputFields();  // Clear fields when the fragment is paused
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearInputFields();  // Clear fields when view is destroyed
    }

    private void initializeViews(View view) {
        userImage = view.findViewById(R.id.user_avatar);
        firstNameInput = view.findViewById(R.id.first_name_input);
        lastNameInput = view.findViewById(R.id.last_name_input);
        emailInput = view.findViewById(R.id.email_input);
        idInput = view.findViewById(R.id.id_number_input);
        avatarUrlInput = view.findViewById(R.id.avatar_url_input);
        saveButton = view.findViewById(R.id.save_button);
    }

    private void setupSaveButtonClickListener() {
        saveButton.setOnClickListener(view -> handleSaveButtonClick(view));
    }

    private void setupAvatarUrlInputWatcher() {
        avatarUrlInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleAvatarUrlChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void handleSaveButtonClick(View view) {
        String avatarUrl = avatarUrlInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String firstName = firstNameInput.getText().toString().trim();
        String idString = idInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();

        if (validateInputs(avatarUrl, email, firstName, idString, lastName)) {
            int id = Integer.parseInt(idString);
            User newUser = new User(avatarUrl, email, firstName, id, lastName);
            clearInputFields();
            userViewModel.addUser(newUser);
            if (listener != null) {
                listener.onUserAdd(newUser);
            }
            this.user = null;
        }
    }

    private boolean validateInputs(String avatarUrl, String email, String firstName, String idString, String lastName) {
        if (avatarUrl.isEmpty() || email.isEmpty() || firstName.isEmpty() || idString.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid ID format.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void clearInputFields() {
        avatarUrlInput.setText("");
        emailInput.setText("");
        firstNameInput.setText("");
        idInput.setText("");
        lastNameInput.setText("");
    }

    private void handleAvatarUrlChange(String avatarUrl) {
        if (!avatarUrl.isEmpty()) {
            Glide.with(this)
                    .load(avatarUrl)
                    .placeholder(R.drawable.ic_user_placeholder)
                    .error(R.drawable.ic_user_placeholder)
                    .into(userImage);
        } else {
            userImage.setImageResource(R.drawable.ic_user_placeholder);
        }
    }

    public void setUser(User user) {
        this.user = user;
        if (getView() != null) {
            updateUserFields();  // Ensure views are updated if they are available
        }
    }

    private void updateUserFields() {
        if (user != null) {
            firstNameInput.setText(user.getFirst_name());
            lastNameInput.setText(user.getLast_name());
            emailInput.setText(user.getEmail());
            idInput.setText(String.valueOf(user.getId()));
            avatarUrlInput.setText(user.getAvatar());
            Glide.with(this)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_user_placeholder)
                    .error(R.drawable.ic_user_placeholder)
                    .into(userImage);
        } else {
            clearInputFields();
        }
    }

    public void setOnUserAddListener(OnUserAddListener listener) {
        this.listener = listener;
    }

    public interface OnUserAddListener {
        void onUserAdd(User user);
    }
}

