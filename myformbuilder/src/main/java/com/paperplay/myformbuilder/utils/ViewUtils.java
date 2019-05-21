package com.paperplay.myformbuilder.utils;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Ahmed Yusuf on 20/05/19.
 */
public class ViewUtils {
    public static int getBackgroundColor(TextView view) {
        ColorDrawable drawable = (ColorDrawable) view.getBackground();
        if (Build.VERSION.SDK_INT >= 11) {
            return drawable.getColor();
        }
        try {
            Field field = drawable.getClass().getDeclaredField("mState");
            field.setAccessible(true);
            Object object = field.get(drawable);
            field = object.getClass().getDeclaredField("mUseColor");
            field.setAccessible(true);
            return field.getInt(object);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }
}
