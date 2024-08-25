package com.example.usermanager.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;
import com.example.usermanager.ui.adapter.SwipeToDeleteCallback;
import com.example.usermanager.ui.adapter.UserCardAdapter;
import com.example.usermanager.viewmodel.UserViewModel;

import java.util.List;

public class UserListFragment extends Fragment implements UserCardAdapter.OnUserClickListener, UserCardAdapter.OnUserSwipedListener {

    private RecyclerView recyclerView;
    private UserCardAdapter adapter;
    private int currentPageNumber = 0;
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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);



        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize ViewModel, shared with the activity
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter with the listener
        adapter = new UserCardAdapter(this,this, null);
        recyclerView.setAdapter(adapter);

        loadUsers(currentPageNumber);

        // Create and attach ItemTouchHelper for swipe-to-delete functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Setup scroll listener for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    // Check if scrolling up and we are at the top of the list
                    if (dy < 0 && layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                        // Load previous data if currentPageNumber is not less than 0
                        if (currentPageNumber > 0) {
                            loadUsers(currentPageNumber);
                            currentPageNumber--;
                            Log.d("UserFragment", "Loading previous data..." + currentPageNumber);
                        }
                    }

                    // Check if scrolling down and we are at the bottom of the list
                    if (dy > 0 && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
                        // Load more data
                        loadUsers(currentPageNumber);
                        currentPageNumber++;
                        Log.d("UserFragment", "Loading more data..." + currentPageNumber);
                    }
                }
            }
        });

    }
    private void loadUsers(int page) {
        userViewModel.getUsersByPagination(page).observe(getViewLifecycleOwner(), users -> {
            if (users != null && !users.isEmpty()) {
                Log.d("UserFragment", "Loaded users: " + users.size());
                adapter.addUsers(users);
                if (listener != null && !users.isEmpty()) {
                    int rand = (int) (Math.random() * users.size());
                    listener.onUserSelected(users.get(rand));
                }else {
                    Log.e("UserFragment", "Error loading users");
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


