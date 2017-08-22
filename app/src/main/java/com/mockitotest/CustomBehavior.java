package com.mockitotest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by bykj003 on 2017/8/17.
 */

public class CustomBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    public static final String TAG = "CustomBehavior";
    private ImageView mDependencyView;
    private int mDependencyHeight;
    private volatile int mStartY, mMoveY, mDiff, mPrefMoveY;
    private boolean mIsScaleImage = false;
    private int mImageInitTop, mChildInitTop;

    public CustomBehavior() {
    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        if (dependency instanceof ImageView) {
            mDependencyView = (ImageView) dependency;
            mDependencyHeight = mDependencyView.getHeight();
            mImageInitTop = 0;
            mChildInitTop = child.getTop();
            return true;
        }
        return false;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, RecyclerView child, MotionEvent ev) {
        parent.requestDisallowInterceptTouchEvent(true);
        return true;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, RecyclerView child, MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleDownEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                handleMoveEvent(child, ev);
                break;
            case MotionEvent.ACTION_UP:
                handleUpOrCancelEvent(child);
                break;
            case MotionEvent.ACTION_CANCEL:
                handleUpOrCancelEvent(child);
                break;
        }
        return true;
    }

    private boolean isCanScroll(MotionEvent ev) {
        return ((mStartY - ev.getY()) >= 0 && mMoveY <= mDependencyHeight) || ((mStartY - ev.getY()) <= 0 && mMoveY >= (-mDependencyHeight / 2));
    }

    private boolean isCanScaleImage(int diff) {
        boolean isCanScale = false;
        int top = mDependencyView.getTop();
        if (diff < 0 || mIsScaleImage) {
            if (top == 0) {
                isCanScale = true;
            } else {
                if ((top < 0 && top >= -12) || top > 0) {
                    isCanScale = true;
                    ViewCompat.offsetTopAndBottom(mDependencyView, -top);
                }
            }
        }
        return isCanScale;
    }

    private void handleMoveEvent(RecyclerView child, MotionEvent ev) {
        if (!isCanScroll(ev)) {
            return;
        }

        mPrefMoveY = (int) ev.getY();
        mDiff = (mStartY - mPrefMoveY);
        mMoveY += mDiff;
        mStartY = mPrefMoveY;

        if (isCanScaleImage(mDiff)) {
            if (mMoveY > 0) {
                mIsScaleImage = false;
                return;
            }
            mIsScaleImage = true;
            ViewCompat.setScaleX(mDependencyView, 1 - (float) mMoveY / (float) (mDependencyHeight / 2));
            ViewCompat.setScaleY(mDependencyView, 1 - (float) mMoveY / (float) (mDependencyHeight / 2));
        } else {
            mIsScaleImage = false;
            ViewCompat.offsetTopAndBottom(mDependencyView, -mDiff);
        }
        ViewCompat.offsetTopAndBottom(child, -mDiff);
    }

    private void handleUpOrCancelEvent(RecyclerView child) {
        if (mIsScaleImage) {
            startImageAnimation();
            startChildAnimation(child);
            mMoveY = 0;
            mStartY = 0;
            mIsScaleImage = false;
        }
    }

    private void handleDownEvent(MotionEvent ev) {
        mStartY = (int) ev.getY();
    }

    private void startImageAnimation() {
        mDependencyView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start();
    }

    private void startChildAnimation(final RecyclerView child) {
        int top = child.getTop();
        ValueAnimator animator = ValueAnimator.ofInt(top - mMoveY, mDependencyHeight).setDuration(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                child.setTop(value);
            }
        });
        animator.start();
    }
}
