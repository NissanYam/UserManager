package com.example.usermanager.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.ui.adapter.UserCardAdapter;
import com.example.usermanager.viewmodel.UserViewModel;
public class UserListFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserCardAdapter adapter;
    private UserViewModel userViewModel;
    private OnUserSelectedListener listener;

    // Define an interface for the listener
    public interface OnUserSelectedListener {
        void onUserSelected(User user);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter with the listener
        adapter = new UserCardAdapter(user -> {
            // Handle user click by notifying the listener
            if (listener != null) {
                listener.onUserSelected(user);
            }
        });
        recyclerView.setAdapter(adapter);

        // Observe user data from ViewModel
        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), users -> {
            adapter.setUserList(users);
            if (listener != null && users != null && !users.isEmpty()) {
                listener.onUserSelected(users.get(0));
            }
        });

        return view;
    }
    public void setOnUserSelectedListener(OnUserSelectedListener listener) {
        this.listener = listener;
    }
}
