package com.example.usermanager.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.viewmodel.UserViewModel;

public class AddUserFragment extends Fragment {
    private ImageView userImage;
    private EditText firstNameInput, lastNameInput, emailInput, idInput, avatarUrlInput;
    private Button saveButton;
    private UserViewModel userViewModel;
    private OnUserAddListener listener;



    // Define an interface for the listener
    public interface OnUserAddListener {
        void OnUserAddListener(User user);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userImage = view.findViewById(R.id.user_avatar);
        firstNameInput = view.findViewById(R.id.first_name_input);
        lastNameInput = view.findViewById(R.id.last_name_input);
        emailInput = view.findViewById(R.id.email_input);
        idInput = view.findViewById(R.id.id_number_input);
        avatarUrlInput = view.findViewById(R.id.avatar_url_input);
        saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User u = new User(
                        avatarUrlInput.getText().toString(),
                        emailInput.getText().toString(),
                        firstNameInput.getText().toString(),
                        Integer.parseInt(idInput.getText().toString()),
                        lastNameInput.getText().toString());
                avatarUrlInput.setText("");
                emailInput.setText("");
                firstNameInput.setText("");
                idInput.setText("");
                lastNameInput.setText("");
                userViewModel.addUser(u);
                // Notify the listener when a user is added
                listener.OnUserAddListener(u);
            }
        });

        avatarUrlInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Load image into userImage when the avatarUrlInput changes
                String avatarUrl = s.toString();
                if (!avatarUrl.isEmpty()) {
                    Glide.with(AddUserFragment.this)
                            .load(avatarUrl)
                            .placeholder(R.drawable.ic_user_placeholder) // Optional: placeholder while loading
                            .error(R.drawable.ic_user_placeholder) // Optional: error placeholder
                            .into(userImage);
                } else {
                    userImage.setImageResource(R.drawable.ic_user_placeholder); // Reset to placeholder if URL is empty
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }
    public void setOnUserAddListener(OnUserAddListener listener) {
        this.listener = listener;
    }
}

