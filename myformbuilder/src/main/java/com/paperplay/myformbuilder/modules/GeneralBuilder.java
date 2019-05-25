package com.paperplay.myformbuilder.modules;

import android.app.Activity;
import android.widget.LinearLayout;

/**
 * Created by Ahmed Yusuf on 20/05/19.
 */
public interface GeneralBuilder<T> {
    T setActivity(Activity activity);
    T setTitle(String title);
    T setTitleFont(String title);
    T setTitleColorResource(int titleColorResource);
    T setOrientation(int orientation);
    T setNullable(boolean nullable);
    T setDefStyleAttr(int defStyleAttr);
    T setFormLayout(LinearLayout formLayout);
}
