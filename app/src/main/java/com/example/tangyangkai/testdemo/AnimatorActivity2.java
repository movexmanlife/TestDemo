package com.example.tangyangkai.testdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/9/10.
 */
public class AnimatorActivity2 extends Activity {
    /**
     * 需要被隐藏的View
     */
    private LinearLayout mHiddenLayout;
    /**
     * 需要被隐藏的View的高度，在XML中的高度为120dip
     */
    private int mHiddenViewMeasuredHeight;

    /**
     * 箭头控件
     */
    private ImageView mArrowIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator1);

        mHiddenLayout = (LinearLayout) findViewById(R.id.linear_hidden);
        mArrowIv = (ImageView) findViewById(R.id.my_iv);

        mHiddenViewMeasuredHeight = PixelUtils.dip2px(getApplicationContext(), 120);
    }

    public void onClick(View v) {
        /**
         * 在已经隐藏的情况下，进行显示
         */
        if (mHiddenLayout.getVisibility() == View.GONE) {
            animateOpen(mHiddenLayout);
        } else {
            animateClose(mHiddenLayout);
        }
    }

    private void animateOpen(View hiddenLayout) {
        hiddenLayout.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(hiddenLayout, 0,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    private void animateClose(final View hiddenLayout) {
        int origHeight = hiddenLayout.getHeight();
        ValueAnimator animator = createDropAnimator(hiddenLayout, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hiddenLayout.setVisibility(View.GONE);
            }

        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View hiddenLayout, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = hiddenLayout.getLayoutParams();
                layoutParams.height = value;
                hiddenLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

}
