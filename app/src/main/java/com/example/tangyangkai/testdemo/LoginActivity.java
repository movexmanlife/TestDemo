package com.example.tangyangkai.testdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by tangyangkai on 16/5/10.
 */
public class LoginActivity extends AppActivity {

    private String username;
    private boolean mIsNeedDisableTouchEvent = false;
    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            username = bundle.getString("username");
        }
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return FirstFragment.newInstance(username);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIsNeedDisableTouchEvent) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    public boolean isNeedDisableTouchEvent() {
        return this.mIsNeedDisableTouchEvent;
    }

    public void setIsNeedDisableTouchEvent(boolean isNeedDisableTouchEvent) {
        this.mIsNeedDisableTouchEvent = isNeedDisableTouchEvent;
    }

    public interface OnDisableTouchEventListener {
        void onDisable(boolean isDisable);
    }
}

