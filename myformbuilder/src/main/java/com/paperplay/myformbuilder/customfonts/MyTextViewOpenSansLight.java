package com.paperplay.myformbuilder.customfonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by macbookpro on 1/18/17.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextViewOpenSansLight extends TextView {
    public MyTextViewOpenSansLight(Context context) {
        super(context);
        init();
    }

    public MyTextViewOpenSansLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewOpenSansLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public MyTextViewOpenSansLight(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Light.ttf");
            setTypeface(tf);
        }
    }
}
