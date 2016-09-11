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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 用来测试登录的界面中输入号码的动画，提示逐渐放大的效果
 *
 * 成功实现功能，但是这里的属性动画过多，需要进行精简一下。
 */
public class ScaleAnimatorActivityNew1 extends Activity implements View.OnClickListener{
    Button button;
    Button loginButton;
    EditText editText;
    TextView login_toast_phone;
    LinearLayout phoneToastLayout;

    public int loginBtnWidth;
    public int loginBtnHeight;
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
            createDropAnimator(phoneToastLayout, PixelUtils.dip2px(getApplicationContext(), 60), 0);
        } else {
            if (login_toast_phone.getVisibility() == View.GONE) {
                phoneToastExpand(content, true);
                createDropAnimator(phoneToastLayout, 0, PixelUtils.dip2px(getApplicationContext(), 60));
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
                createDropAnimator(phoneToastLayout, PixelUtils.dip2px(getApplicationContext(), 60), 0);
            }

            if (!TextUtils.isEmpty(editText.getText().toString())) {
                editText.setText(null);
            }
        }
    }

    private void phoneToastExpand(String content, boolean isNeedExpand) {
        if (!isNeedExpand) {
            login_toast_phone.setText(content);
            return;
        }

        int pivotX = editText.getMeasuredWidth() / 2;
        int pivotY = 0;
        login_toast_phone.setPivotX(pivotX);
        login_toast_phone.setPivotY(pivotY);
        login_toast_phone.setText(content);
        login_toast_phone.setVisibility(View.VISIBLE);

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.2f, 1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(login_toast_phone, scaleX, scaleY, alpha);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                login_toast_phone.setVisibility(View.VISIBLE);
            }
        });
        objectAnimator.start();
    }

    private void phoneToastCollapse() {
        int pivotX = editText.getMeasuredWidth() / 2;
        int pivotY = 0;
        login_toast_phone.setPivotX(pivotX);
        login_toast_phone.setPivotY(pivotY);

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0.2f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(login_toast_phone, scaleX, scaleY, alpha);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                login_toast_phone.setVisibility(View.GONE);
                login_toast_phone.setText(null);
            }
        });
        /**
         * 这段代码的含义就是，当时的思路是这样子的，login_toast_phone不断的缩放对吧，然后
         * 那么login_toast_phone的高度也必然变小了，
         *
         *
         * TODO!!!要非常注意这点
         * TODO!!!特别注意这里login_toast_phone的值其实是不会改变的，变化的其实是login_toast_phone.getScaleY()等的值。
         */
//        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int height = login_toast_phone.getHeight();
//                Log.e("xxxxxxxxxx", "------------------------------------>height:" + height);
//                phoneToastLayout.requestLayout();
//            }
//        });
        objectAnimator.start();
    }

    private void createDropAnimator(final View hiddenLayout, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = hiddenLayout.getLayoutParams();
                layoutParams.height = value;
                hiddenLayout.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }
}
