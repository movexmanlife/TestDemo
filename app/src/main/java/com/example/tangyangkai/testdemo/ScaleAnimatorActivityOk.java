package com.example.tangyangkai.testdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 用来测试登录的界面中输入号码的动画，提示逐渐放大的效果
 */
public class ScaleAnimatorActivityOk extends Activity implements View.OnClickListener{
    private static final String TAG  = ScaleAnimatorActivityOk.class.getSimpleName();
    Button button;
    Button loginButton;
    EditText editText;
    TextView login_toast_phone;
    LinearLayout phoneToastLayout;

    public int loginBtnWidth;
    public int loginBtnHeight;
    public boolean mIsDuringAnimator = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_animator_new);

        button = (Button) findViewById(R.id.btn);
        loginButton = (Button) findViewById(R.id.loginBtn);
        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content  = s.toString();
                toggle(content);
            }
        });
        login_toast_phone = (TextView) findViewById(R.id.login_toast_phone);
        phoneToastLayout = (LinearLayout) findViewById(R.id.phoneToastLayout);

        button.setOnClickListener(this);
        loginButton.post(new Runnable() {
            @Override
            public void run() {
                loginBtnWidth = loginButton.getWidth();
                loginBtnHeight = loginButton.getHeight();
            }
        });
    }

    private void toggle(String content) {
        if (TextUtils.isEmpty(content)) {
            phoneToastCollapse();
        } else {
            if (login_toast_phone.getVisibility() == View.GONE) {
                phoneToastExpand(content, true);
            } else {
                phoneToastExpand(content, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            if (login_toast_phone.getVisibility() == View.VISIBLE) {
                phoneToastCollapse();
            }

            if (!TextUtils.isEmpty(editText.getText().toString())) {
                editText.setText(null);
            }
        }
    }

    /**
     * 手机号码栏提示收起
     */
    private void phoneToastExpand(String content, boolean isNeedExpand) {
        if (!isNeedExpand) {
            login_toast_phone.setText(content);
            return;
        }

        login_toast_phone.setText(content);
        createAnimator(login_toast_phone, new Range(0f, 1f), new Range(0f, 1f), new Range(0.2f, 1f),
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mIsDuringAnimator = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        login_toast_phone.setVisibility(View.VISIBLE);
                        mIsDuringAnimator = false;
                    }
                });
    }

    /**
     * 手机号码栏提示收起
     */
    private void phoneToastCollapse() {
        createAnimator(login_toast_phone, new Range(1.0f, 0f), new Range(1.0f, 0f), new Range(1.0f, 0f),
                new AnimatorListenerAdapter(){
            @Override
            public void onAnimationStart(Animator animation) {
                mIsDuringAnimator = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsDuringAnimator = false;
                login_toast_phone.setVisibility(View.GONE);
                login_toast_phone.setText(null);
            }
        });
    }

    /**
     * 创建属性动画
     * @param view 动画的View
     * @param scaleXRange 缩放X的范围值
     * @param scaleYRange 缩放Y的范围值
     * @param alphaRange 透明度的范围值
     * @param listener 动画监听器
     */
    private void createAnimator(View view, Range scaleXRange, Range scaleYRange, Range alphaRange, AnimatorListenerAdapter listener) {
        if (mIsDuringAnimator) {
            return;
        }

        int pivotX = editText.getMeasuredWidth() / 2;
        int pivotY = 0;
        view.setPivotX(pivotX);
        view.setPivotY(pivotY);

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(AnimatorConstDef.SCALE_X, scaleXRange.startValue, scaleXRange.toValue);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(AnimatorConstDef.SCALE_Y, scaleYRange.startValue, scaleYRange.toValue);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat(AnimatorConstDef.ALPHA, alphaRange.startValue, alphaRange.toValue);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY, alpha);
        objectAnimator.setDuration(getResources().getInteger(R.integer.phone_toast_anim_time));
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(listener);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateLayoutParamsByAnimatedValue(animation, AnimatorConstDef.SCALE_Y);
            }
        });
        objectAnimator.start();
    }

    /**
     * 根据当前动画值更新布局参数
     * @param animation 值动画
     * @param propertyName 属性名
     */
    private void updateLayoutParamsByAnimatedValue(ValueAnimator animation, String propertyName) {
        float scaleYFactor = (float)animation.getAnimatedValue(propertyName);
        ViewGroup.LayoutParams layoutParams = phoneToastLayout.getLayoutParams();
        layoutParams.height = (int)(scaleYFactor * getResources().getDimensionPixelSize(R.dimen.phone_toast_height));
        phoneToastLayout.setLayoutParams(layoutParams);
    }

    public static class Range {
        public float startValue; // 起始值
        public float toValue; // 结束值
        public Range(float startValue, float toValue) {
            this.startValue = startValue;
            this.toValue = toValue;
        }
    }
}