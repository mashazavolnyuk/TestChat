package com.mashazavolnyuk.testchat.mvp.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.mashazavolnyuk.testchat.R;
import com.mashazavolnyuk.testchat.adapters.ChatAdapter;

/**
 * Created by mashka on 06.08.17.
 */

public class ChatItemTouchHelper extends ItemTouchHelper.SimpleCallback{

    private static final float MYTEXTSIZE = 18.0f;
    private int textSizePx;

    RecyclerView recyclerView;
    Context context;

    Drawable background;
    Drawable xMark;
    int xMarkMargin;
    boolean initiated;

    public ChatItemTouchHelper(RecyclerView recyclerView, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        this.recyclerView = recyclerView;
        this.context = recyclerView.getContext();
    }

    private void init() {
        background = new ColorDrawable(Color.BLUE);
        xMark = ContextCompat.getDrawable(context, R.drawable.ic_clear_24dp);
        xMark.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        xMarkMargin = (int) context.getResources().getDimension(R.dimen.ic_clear_margin);
        initiated = true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int swipedPosition = viewHolder.getAdapterPosition();
        ChatAdapter adapter = (ChatAdapter) recyclerView.getAdapter();
        adapter.remove(swipedPosition);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        Paint p = new Paint();
        p.setColor(context.getResources().getColor(R.color.colorPrimary));
        // not sure why, but this method get's called for viewholder that are already swiped away
        if (viewHolder.getAdapterPosition() == -1) {
            // not interested in those
            return;
        }

        if (!initiated) {
            init();
        }

        // draw red background
        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);
        // draw x mark
        int itemHeight = itemView.getBottom() - itemView.getTop();
        int intrinsicWidth = xMark.getIntrinsicWidth();
        int intrinsicHeight = xMark.getIntrinsicWidth();

        int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
        int xMarkRight = itemView.getRight() - xMarkMargin;
        int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
        int xMarkBottom = xMarkTop + intrinsicHeight;
        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                (float) itemView.getBottom(), p);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
// Convert the dps to pixels, based on density scale
        textSizePx = (int) (MYTEXTSIZE * scale + 0.5f);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-MediumItalic.ttf");
        if (tf != null)
            paint.setTypeface(tf);
        paint.setTextSize(textSizePx);
        paint.setTextAlign(Paint.Align.CENTER);
        String inbox = itemView.getContext().getResources().getString(R.string.swipe_text);
        c.drawText(inbox, (float) (itemView.getX() - itemView.getX() + pxToDp(100)), itemView.getY() + pxToDp(50), paint); //
        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

        xMark.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private int pxToDp(int px) {
        return (int) (px * Resources.getSystem().getDisplayMetrics().density);
    }
}
