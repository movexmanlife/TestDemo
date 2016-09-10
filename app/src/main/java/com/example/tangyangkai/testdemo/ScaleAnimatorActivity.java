package com.example.tangyangkai.testdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
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
 */
public class ScaleAnimatorActivity extends Activity implements View.OnClickListener{
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
        setContentView(R.layout.activity_scale_animator);

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
        objectAnimator.setDuration(30000);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                login_toast_phone.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)phoneToastLayout.getLayoutParams();
                lp.height = loginBtnHeight + PixelUtils.dip2px(getApplicationContext(), 50);
                phoneToastLayout.setLayoutParams(lp);
                phoneToastLayout.requestLayout();
            }
        });
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if ((float) animation.getAnimatedValue() >= 1) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)phoneToastLayout.getLayoutParams();
                    lp.height = loginBtnHeight + PixelUtils.dip2px(getApplicationContext(), 50);
                    phoneToastLayout.setLayoutParams(lp);
                } else {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)phoneToastLayout.getLayoutParams();
                    int currentToastHeight = (int)((float) animation.getAnimatedValue() * PixelUtils.dip2px(getApplicationContext(), 50));
                    login_toast_phone.setHeight(currentToastHeight);
                    lp.height = loginBtnHeight + currentToastHeight;
                    phoneToastLayout.setLayoutParams(lp);
                }
                phoneToastLayout.requestLayout();
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
        objectAnimator.setDuration(30000);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                login_toast_phone.setVisibility(View.GONE);
                login_toast_phone.setText(null);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)phoneToastLayout.getLayoutParams();
                lp.height = loginBtnHeight;
                phoneToastLayout.setLayoutParams(lp);
            }
        });
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if ((float) animation.getAnimatedValue() >= 1) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)phoneToastLayout.getLayoutParams();
                    lp.height = loginBtnHeight;
                    phoneToastLayout.setLayoutParams(lp);
                } else {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)phoneToastLayout.getLayoutParams();
                    int currentToastHeight =(int)((float) animation.getAnimatedValue() * PixelUtils.dip2px(getApplicationContext(), 50));
                    login_toast_phone.setHeight(currentToastHeight);
                    lp.height = loginBtnHeight + currentToastHeight;
                    phoneToastLayout.setLayoutParams(lp);
                }
                phoneToastLayout.requestLayout();
            }
        });
        objectAnimator.start();
    }
}
