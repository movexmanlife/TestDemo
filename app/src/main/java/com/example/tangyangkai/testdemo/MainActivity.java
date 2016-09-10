package com.example.tangyangkai.testdemo;

/**
 * Created by tangyangkai on 16/5/10.
 */
public class MainActivity extends AppActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return MainFragment.newInstance();
    }
}
