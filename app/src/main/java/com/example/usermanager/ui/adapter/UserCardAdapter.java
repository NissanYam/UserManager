package com.example.usermanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.UserViewHolder> implements ItemTouchHelperAdapter{

    private List<User> userList;
    private final Map<Integer, User> userMap;
    private final OnUserClickListener onUserClickListener;
    private final OnUserSwipedListener onUserSwipedListener;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return (userList != null) ? userList.size() : 0;
    }

    public UserCardAdapter(OnUserClickListener onUserClickListener,
                           OnUserSwipedListener onUserSwipedListener,
                           List<User> userList) {
        this.onUserClickListener = onUserClickListener;
        this.onUserSwipedListener = onUserSwipedListener;
        this.userList = userList != null ? userList : new ArrayList<>();
        this.userMap = new HashMap<>();
        for (User user : this.userList) {
            userMap.put(user.getId(), user); // Initialize the map with existing users
        }
    }

    public void addUsers(List<User> newUserList) {
        if (newUserList == null || newUserList.isEmpty()) {
            return;
        }

        if (this.userList == null) {
            this.userList = new ArrayList<>();
        }

        for (User user : newUserList) {
            if (userMap.containsKey(user.getId())) {
                // Update the existing user
                int index = this.userList.indexOf(userMap.get(user.getId()));
                if (index != -1) {
                    this.userList.set(index, user);
                    notifyItemChanged(index); // Notify that an item has changed
                }
            } else {
                // Add new user
                this.userList.add(user);
                notifyItemInserted(this.userList.size() - 1); // Notify that a new item was added
            }
            // Update the map with the latest user data
            userMap.put(user.getId(), user);
        }
    }
    @Override
    public void onItemSwiped(int position) {
        User user = userList.get(position);
        userList.remove(position);
        notifyItemRemoved(position);
        onUserSwipedListener.onUserSwiped(user);
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }
    public interface OnUserSwipedListener {
        void onUserSwiped(User user);
    }
    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView userAvatar;
        TextView userFullName, userEmail;

        UserViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            userFullName = itemView.findViewById(R.id.user_full_name);
            userEmail = itemView.findViewById(R.id.user_email);
        }

        void bind(User user) {
            if (user != null)
            {
                userFullName.setText(String.format("%s %s", user.getFirst_name(), user.getLast_name()));
                userEmail.setText(user.getEmail());

                Glide.with(itemView.getContext())
                        .load(user.getAvatar())
                        .placeholder(R.drawable.ic_user_placeholder)
                        .into(userAvatar);

                // Handle item click
                itemView.setOnClickListener(v -> onUserClickListener.onUserClick(user));
            }
        }
    }
}
