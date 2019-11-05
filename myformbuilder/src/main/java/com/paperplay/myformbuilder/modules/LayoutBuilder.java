package com.paperplay.myformbuilder.modules;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Ahmed Yusuf on 20/05/19.
 */
public interface LayoutBuilder<T> {
    T setVerticalMargin(int margin);
    T setHorizontalMargin(int margin);
    T setViewList(ArrayList<View> viewList);
    T setFormLayout(LinearLayout formLayout);
}
