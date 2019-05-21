package com.paperplay.myformbuilder.customview.interfaces;

import android.app.Activity;

import java.util.List;

/**
 * Created by Ahmed Yusuf on 20/05/19.
 */
public interface GeneralBuilderInterface<T> {
    T setActivity(Activity activity);
    T setTitle(String title);
    T setTitleFont(String title);
    T setTitleColorResource(int titleColorResource);
    T setOrientation(int orientation);
    T setNullable(boolean nullable);
    T setDefStyleAttr(int defStyleAttr);
}
