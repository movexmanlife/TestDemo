package com.example.tangyangkai.testdemo;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by tangyangkai on 16/5/10.
 */
public class ThirdFragment extends BaseFragment {
    private LoginActivity.OnDisableTouchEventListener onDisableTouchEventListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_third;
    }

    private ImageView backIMg;

    public static ThirdFragment newInstance() {

        return new ThirdFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        backIMg = (ImageView) view.findViewById(R.id.third_back);
        backIMg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        int animResId;
        if (enter) {
            animResId = R.anim.fragment_push_left_in;
        } else {
            animResId = R.anim.fragment_back_right_out;
        }

        Animation animation = AnimationUtils.loadAnimation(getContext(), animResId);

        animation.setAnimationListener(new AnimationAdapter() {
            public void onAnimationEnd(Animation animation) {
//                if (onDisableTouchEventListener != null) {
//                    onDisableTouchEventListener.onDisable(false);
//                }
                ((LoginActivity) getActivity()).setIsNeedDisableTouchEvent(false);
            }


            public void onAnimationStart(Animation animation) {
//                if (onDisableTouchEventListener != null) {
//                    onDisableTouchEventListener.onDisable(true);
//                }
                ((LoginActivity) getActivity()).setIsNeedDisableTouchEvent(true);
            }
        });
        return animation;
    }

}
