package com.paperplay.myformbuilder.view;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.paperplay.myformbuilder.modules.LayoutBuilder;

import java.util.ArrayList;

/**
 * Created by Ahmed Yusuf on 2019-11-04.
 */
public class HorizontalLayout {

    public static class Builder implements LayoutBuilder<Builder>, Cloneable{
        int horizontal_margin = 0, vertical_margin = 0;
        ArrayList<View> viewList;
        Context context;
        LinearLayout formLayout;

        public Builder(Context context) {
            this.context = context;
        }

        @Override
        public Builder setVerticalMargin(int margin) {
            this.vertical_margin = margin;
            return this;
        }

        @Override
        public Builder setHorizontalMargin(int margin) {
            this.horizontal_margin = margin;
            return this;
        }

        @Override
        public Builder setViewList(ArrayList<View> viewList) {
            this.viewList = viewList;
            return this;
        }

        @Override
        public Builder setFormLayout(LinearLayout formLayout) {
            this.formLayout = formLayout;
            return this;
        }

        public HorizontalLayout create(){
            return new HorizontalLayout(this);
        }

        public Object clone() throws CloneNotSupportedException{
            return super.clone();
        }
    }

    private HorizontalLayout(Builder builder){
        LinearLayout horizontalLayout = new LinearLayout(builder.context);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        for(int n=0;n<builder.viewList.size();n++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            if(n==0 && builder.viewList.size()>1){
                layoutParams.setMargins(0, builder.vertical_margin,builder.horizontal_margin, builder.vertical_margin);
            }else if(builder.viewList.size()>1){
                layoutParams.setMargins(builder.horizontal_margin, builder.vertical_margin, 0, builder.vertical_margin);
            }
            builder.viewList.get(n).setLayoutParams(layoutParams);
            horizontalLayout.addView(builder.viewList.get(n));
        }
        if(builder.formLayout!=null){
            LinearLayout formLayout = builder.formLayout;
            formLayout.addView(horizontalLayout);
        }
    }
}
