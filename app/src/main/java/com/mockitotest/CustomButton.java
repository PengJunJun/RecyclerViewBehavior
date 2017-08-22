package com.mockitotest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

/**
 * Created by bykj003 on 2017/8/14.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {

    private TranslateAnimation mAnimation;

    public CustomButton(Context context) {
        this(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mAnimation == null) {
            mAnimation = new TranslateAnimation(getX(), getX() + 400, getY(), getY() + 400);
        }
        mAnimation.setDuration(2000);
        this.startAnimation(mAnimation);
    }
}
