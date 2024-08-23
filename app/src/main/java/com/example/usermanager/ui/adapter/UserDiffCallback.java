package com.example.usermanager.ui.adapter;

import androidx.recyclerview.widget.DiffUtil;
import com.example.usermanager.model.apiUser.models.User;

import java.util.Collections;
import java.util.List;

public class UserDiffCallback extends DiffUtil.Callback {

    private final List<User> oldList;
    private final List<User> newList;

    public UserDiffCallback(List<User> oldList, List<User> newList) {
        this.oldList = oldList != null ? oldList : Collections.emptyList();
        this.newList = newList != null ? newList : Collections.emptyList();
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // Check if the same item by comparing unique IDs
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        // Check if the contents of the items are the same
        User oldUser = oldList.get(oldItemPosition);
        User newUser = newList.get(newItemPosition);
        return oldUser.equals(newUser);
    }
}


