package com.example.usermanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usermanager.R;
import com.example.usermanager.model.apiUser.models.User;

import java.util.Collections;
import java.util.List;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.UserViewHolder> {

    private List<User> userList;
    private final OnUserClickListener onUserClickListener;

    public UserCardAdapter(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

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

    public void setUserList(List<User> newUserList) {
        if (newUserList == null) {
            newUserList = Collections.emptyList();
        }
        if (this.userList == null) {
            this.userList = Collections.emptyList();
        }

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new UserDiffCallback(this.userList, newUserList));
        this.userList = newUserList;
        diffResult.dispatchUpdatesTo(this);
    }



    public interface OnUserClickListener {
        void onUserClick(User user);
    }
    class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView userAvatar;
        TextView userFullName, userEmail, favouriteTag;

        UserViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            userFullName = itemView.findViewById(R.id.user_full_name);
            userEmail = itemView.findViewById(R.id.user_email);
        }

        void bind(User user) {
            userFullName.setText(user.getFirst_name() + " " + user.getLast_name());
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
