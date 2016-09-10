package com.example.tangyangkai.testdemo;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tangyangkai on 16/5/10.
 */
public class SecondFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    public static String SECOND_FRAGMENT = "second_fragment";
    private String msg;
    private TextView secondTxt;
    private ImageView backIMg;
    private LoginActivity.OnDisableTouchEventListener onDisableTouchEventListener;

    public static SecondFragment newInstance(String msg) {
        SecondFragment fragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SECOND_FRAGMENT, msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getArguments()) {
            msg = (String) getArguments().getSerializable(SECOND_FRAGMENT);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        backIMg = (ImageView) view.findViewById(R.id.second_back);
        backIMg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        secondTxt= (TextView) view.findViewById(R.id.second_txt);
        secondTxt.setText(msg);
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
                ((LoginActivity)getActivity()).setIsNeedDisableTouchEvent(false);
            }

            public void onAnimationStart(Animation animation) {
//                if (onDisableTouchEventListener != null) {
//                    onDisableTouchEventListener.onDisable(true);
//                }
                ((LoginActivity)getActivity()).setIsNeedDisableTouchEvent(true);
            }
        });

        return animation;
    }

    public LoginActivity.OnDisableTouchEventListener getOnDisableTouchEventListener() {
        return onDisableTouchEventListener;
    }

    public void setOnDisableTouchEventListener(LoginActivity.OnDisableTouchEventListener onDisableTouchEventListener) {
        this.onDisableTouchEventListener = onDisableTouchEventListener;
    }
}
