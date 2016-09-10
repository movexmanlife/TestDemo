package com.example.tangyangkai.testdemo;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tangyangkai on 16/5/10.
 */
public class FirstFragment extends BaseFragment {
    private static final String KEY_PARAM_IS_FIRST_ADD_FRAGMENT = "isFirstAddFragment";
    private static final String FIRST_FRAGMENT = "first_fragment";

    private String msg;
    private EditText usernameEdt;
    private TextView registerTxt, promiseTxt;
    private ImageView backImg;
    private boolean mIsFirstAddFragment = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    public static FirstFragment newInstance(String msg) {
        FirstFragment fragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FIRST_FRAGMENT, msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mIsFirstAddFragment = savedInstanceState.getBoolean(KEY_PARAM_IS_FIRST_ADD_FRAGMENT);
        }

        if (getArguments() != null) {
            msg = (String) getArguments().getSerializable(FIRST_FRAGMENT);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        usernameEdt = (EditText) view.findViewById(R.id.username_edt);
        usernameEdt.setText(msg);
        registerTxt = (TextView) view.findViewById(R.id.register_txt);
        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(SecondFragment.newInstance("从登录界面跳转过来的"));
            }
        });

        backImg = (ImageView) view.findViewById(R.id.first_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });

        promiseTxt = (TextView) view.findViewById(R.id.promise_txt);
        promiseTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(ThirdFragment.newInstance());
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_PARAM_IS_FIRST_ADD_FRAGMENT, mIsFirstAddFragment);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (mIsFirstAddFragment) { // Activity第一次加载此Fragment的时候，无需动画
            mIsFirstAddFragment = false;
            return null;
        }

        int animResId;
        if (enter) {
            animResId = R.anim.fragment_back_left_in;
        } else {
            animResId = R.anim.fragment_push_left_out;
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
