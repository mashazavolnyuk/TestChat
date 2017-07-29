package com.mashazavolnyuk.testchat.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Switch;

import com.mashazavolnyuk.testchat.R;

import static android.R.attr.bitmap;

/**
 * Created by mashka on 28.07.17.
 */

public class CustomSwitch extends Switch {

    Context context;



    private int count_right;
    private int count_left;
    private Drawable drawableThumb;

    public CustomSwitch(Context context) {
        super(context);
        this.context = context;
    }


    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    @Override
    public CharSequence getTextOn() {
        return super.getTextOn();
    }

    @Override
    public void setTextOn(CharSequence textOn) {
        super.setTextOn(textOn);
    }

    @Override
    public void setTextOff(CharSequence textOff) {
        super.setTextOff(textOff);
    }


    public int getCount_right() {
        return count_right;
    }

    public void setCount_right(int count_right) {
        this.count_right = count_right;
    }

    public int getCount_left() {
        return count_left;
    }

    public void setCount_left(int count_left) {
        this.count_left = count_left;
    }


    private void draw() {

        Bitmap square1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rec_white);
        Bitmap square2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rec_blue);
        Bitmap big = Bitmap.createBitmap(square1.getWidth() * 2, square1.getHeight() * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(big);
        canvas.drawBitmap(square1, 0, 0, null);
        BitmapDrawable drawable = new BitmapDrawable(big);
        setThumbDrawable(drawable);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.context = context;
        final TypedArray typedArray = context.
                obtainStyledAttributes(attrs, R.styleable.CustomSwitch, defStyleAttr, defStyleRes);
        setCount_left(typedArray.getResourceId(R.styleable.CustomSwitch_count_start, 0));
        setCount_right(typedArray.getResourceId(R.styleable.CustomSwitch_count_end, 0));
        draw();
        if (isInEditMode()) return;
        typedArray.recycle();
    }


    public void setDrawableStart(int drawableStartId) {
        CustomSwitch.checkResource(drawableStartId);
        drawableThumb = getResources().getDrawable(drawableStartId);
    }


    private static void checkResource(int resId) {
        if (resId == 0)
            throw new RuntimeException("Resource wasn't specified!");
    }


}
