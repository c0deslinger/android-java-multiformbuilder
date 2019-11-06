package com.paperplay.myformbuilder.view;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Ahmed Yusuf on 2019-11-04.
 */
public class HorizontalLayout {

    private LinearLayout horizontalLayout;
    private ArrayList<View> viewList = new ArrayList<>();
    private int horizontal_margin, vertical_margin;

    private boolean wrapContentHeightExist = false;

    /**
     * Used for mode general
     */
    public HorizontalLayout(Context context, int verticalMargin, int horizontalMargin){
        horizontalLayout = new LinearLayout(context);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.vertical_margin = verticalMargin;
        this.horizontal_margin = horizontalMargin;
    }

    public void addView(View view, boolean setHeightWrapContent){
        viewList.add(view);
        if(setHeightWrapContent)
            wrapContentHeightExist = true;
        if(viewList.size() > 1){
            horizontalLayout.removeAllViews();
            for (int n=0;n< viewList.size(); n++){
                View viewItem = viewList.get(n);
                if(n == 0){
                    //set margin of first item
                    LinearLayout.LayoutParams layoutParamsFirstItem = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1.0f);
                    layoutParamsFirstItem.setMargins(0, vertical_margin, horizontal_margin, vertical_margin);
                    viewItem.setLayoutParams(layoutParamsFirstItem);
                }else{
                    //set margin on new item
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            (wrapContentHeightExist)? LinearLayout.LayoutParams.WRAP_CONTENT : LinearLayout.LayoutParams.MATCH_PARENT,
                            1.0f);
                    layoutParams.setMargins(horizontal_margin, vertical_margin, 0, vertical_margin);
                    if(wrapContentHeightExist){
                        layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    }
                    viewItem.setLayoutParams(layoutParams);
                }
                horizontalLayout.addView(viewItem);
            }
        }else{
            horizontalLayout.addView(view);
        }
    }

    public View getView(){
        return horizontalLayout;
    }

}
