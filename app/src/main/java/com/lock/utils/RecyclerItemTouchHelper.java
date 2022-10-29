package com.lock.utils;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.lock.adaptar.CustomNotificationAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private final RecyclerItemTouchHelperListener listener;

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int i, int i2);
    }

    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        return true;
    }

    public RecyclerItemTouchHelper(int i, int i2, RecyclerItemTouchHelperListener recyclerItemTouchHelperListener) {
        super(i, i2);
        this.listener = recyclerItemTouchHelperListener;
    }

    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (!((Boolean) ((CustomNotificationAdapter.ViewHolder) viewHolder).tv_title.getTag()).booleanValue()) {
            return 0;
        }
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        this.listener.onSwiped(viewHolder, i, viewHolder.getAbsoluteAdapterPosition());
    }

    public int convertToAbsoluteDirection(int i, int i2) {
        return super.convertToAbsoluteDirection(i, i2);
    }
}
