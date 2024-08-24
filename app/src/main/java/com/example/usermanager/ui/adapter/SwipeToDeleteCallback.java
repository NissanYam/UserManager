package com.example.usermanager.ui.adapter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanager.R;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final ItemTouchHelperAdapter mAdapter;
    private final ColorDrawable background;
    private final Paint paint;

    public SwipeToDeleteCallback(ItemTouchHelperAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        mAdapter = adapter;
        background = new ColorDrawable(Color.RED);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setAntiAlias(true);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - 72) / 2;
        int iconSize = 72; // Size of the icon
        int iconLeft = itemView.getRight() - iconMargin - iconSize; // Position of the icon

        // Draw background with rounded corners
        RectF background = new RectF(itemView.getRight() + dX - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.RED); // Set background color
        backgroundPaint.setStyle(Paint.Style.FILL);
        c.drawRoundRect(background, 32, 32, backgroundPaint); // Increased corner radius to 32dp

        // Draw elevation (shadow)
        Paint shadowPaint = new Paint();
        shadowPaint.setColor(Color.BLACK); // Shadow color
        shadowPaint.setAlpha(30); // Adjust shadow opacity
        c.drawRoundRect(background, 32, 32, shadowPaint); // Matching corner radius for shadow

        // Draw the trash icon
        Drawable trashIcon = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.ic_trash); // Replace with your drawable resource
        if (trashIcon != null) {
            trashIcon.setBounds(iconLeft, itemView.getTop() + iconMargin, iconLeft + iconSize, itemView.getTop() + iconMargin + iconSize);
            trashIcon.draw(c);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }
}
