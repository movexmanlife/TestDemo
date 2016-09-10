package com.example.tangyangkai.testdemo;

/**
 * Created by Administrator on 2016/9/10.
 */
public class ToastUtils {
    private static ToastUtils instance = null;

    private ToastUtils() {
    }

    public static ToastUtils getInstance() {
        if (instance == null) {
            synchronized (ToastUtils.class) {
                if (instance == null) {
                    instance = new ToastUtils();
                }
            }
        }

        return instance;
    }
}
