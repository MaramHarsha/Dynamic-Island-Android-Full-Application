package com.lock.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetDecoration2 extends RecyclerView.ItemDecoration {
    private int mItemOffset;

    public ItemOffsetDecoration2(int i) {
        this.mItemOffset = i;
    }

    public ItemOffsetDecoration2(Context context, int i) {
        this(context.getResources().getDimensionPixelSize(i));
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        int i = this.mItemOffset;
        rect.set(i, i, i, i);
    }
}
