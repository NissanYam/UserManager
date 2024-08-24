package com.example.usermanager.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.ui.adapter.SwipeToDeleteCallback;
import com.example.usermanager.ui.adapter.UserCardAdapter;
import com.example.usermanager.viewmodel.UserViewModel;
public class UserListFragment extends Fragment implements UserCardAdapter.OnUserClickListener, UserCardAdapter.OnUserSwipedListener {

    private RecyclerView recyclerView;
    private UserCardAdapter adapter;
    private UserViewModel userViewModel;
    private OnUserSelectedListener listener;

    @Override
    public void onUserClick(User user) {
        if (listener != null) {
            listener.onUserSelected(user);
        }
    }

    @Override
    public void onUserSwiped(User user) {
        this.userViewModel.deleteUser(user);
    }


    // Define an interface for the listener
    public interface OnUserSelectedListener {
        void onUserSelected(User user);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModel, shared with the activity
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter with the listener
        adapter = new UserCardAdapter(this,this);
        recyclerView.setAdapter(adapter);

        // Observe user data from ViewModel in onCreateView
        observeUserData();
        // Create and attach ItemTouchHelper for swipe-to-delete functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeUserData();
    }

    private void observeUserData() {
        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null) {
                adapter.setUserList(users);
                if (listener != null && !users.isEmpty()) {
                    listener.onUserSelected(users.get(0));
                }
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ensure that the listener is attached to the activity
        if (context instanceof OnUserSelectedListener) {
            listener = (OnUserSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnUserSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Clear the listener to avoid memory leaks
        listener = null;
    }

    public void setOnUserSelectedListener(OnUserSelectedListener listener) {
        this.listener = listener;
    }
}


