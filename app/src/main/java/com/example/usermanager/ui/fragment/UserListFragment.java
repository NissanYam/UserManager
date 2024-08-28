package com.example.usermanager.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        return inflater.inflate(R.layout.fragment_user_list, container, false);
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
        adapter = new UserCardAdapter(this, this, null);
        recyclerView.setAdapter(adapter);

        // Initial data load
        loadInitialData();

        // Create and attach ItemTouchHelper for swipe-to-delete functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Setup scroll listener for pagination
        setupScrollListener();
    }

    private void loadInitialData() {
        loadUsers(currentPageNumber, true, success -> {
            if (success) {
                showToast("Initial users loaded successfully");
            } else {
                showToast("No users found");
            }
        });
    }

    private void setupScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isLoading = false;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isLoading) return;

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    if (dy < 0 && firstVisibleItemPosition <= 1) {
                        // Scrolling up, load previous data
                        loadPreviousData();
                    } else if (dy > 0 && lastVisibleItemPosition >= adapter.getItemCount() - 2) {
                        // Scrolling down, load more data
                        loadMoreData();
                    }
                }
            }

            private void loadPreviousData() {
                if (currentPageNumber > 0) {
                    isLoading = true;
                    loadUsers(currentPageNumber - 1, false, success -> {
                        if (success) {
                            currentPageNumber--;
                        } else {
                            showToast("No more previous data");
                        }
                        isLoading = false;
                    });
                } else {
                    showToast("No more previous data");
                }
            }

            private void loadMoreData() {
                isLoading = true;
                loadUsers(currentPageNumber + 1, false, success -> {
                    if (success) {
                        currentPageNumber++;
                    } else {
                        showToast("No more data available");
                    }
                    isLoading = false;
                });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public interface LoadUsersCallback {
        void onResult(boolean success);
    }

    private void loadUsers(int page, boolean firstLoad, LoadUsersCallback loadUsersCallback) {
        userViewModel.getUsersByPagination(page).observe(getViewLifecycleOwner(), users -> {
            if (users != null && !users.isEmpty()) {
                Log.d("UserFragment", "Loaded users: " + users.size());
                adapter.addUsers(users);

                // Trigger the callback with 'true' since users were found
                loadUsersCallback.onResult(true);

                if (listener != null && firstLoad) {
                    int rand = (int) (Math.random() * users.size());
                    listener.onUserSelected(users.get(rand));
                }
            } else {
                Log.e("UserFragment", "Error loading users");

                // Trigger the callback with 'false' since no users were found
                loadUsersCallback.onResult(false);
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
            throw new ClassCastException(context + " must implement OnUserSelectedListener");
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


