package com.paperplay.myformbuilder.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.paperplay.myformbuilder.R;

/**
 * Created by Ahmed Yusuf on 2019-11-05.
 */
public class MyButton {
    private Button button;
    public static class Builder implements Cloneable{
        Context context;
        String text;
        int textColor = -1;
        int id;
        int drawableResourceId = -1;
        LinearLayout formLayout;
        LinearLayout.LayoutParams layoutParams;
        View.OnClickListener onClickListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setText(String text){
            this.text = text;
            return this;
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setTextColor(int textColor){
            this.textColor = textColor;
            return this;
        }

        public Builder setLayoutParams(LinearLayout.LayoutParams layoutParams){
            this.layoutParams = layoutParams;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener onClickListener){
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder setFormLayout(LinearLayout formLayout){
            this.formLayout = formLayout;
            return this;
        }

        public MyButton create(){
            return new MyButton(this);
        }

        public Object clone() throws CloneNotSupportedException{
            return super.clone();
        }
    }

    private MyButton(Builder builder){
        Button button = new Button(builder.context);
        if(builder.layoutParams != null){
            button.setLayoutParams(builder.layoutParams);
        }else {
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        if(builder.textColor != -1) {
            button.setTextColor(builder.textColor);
        }else{
            button.setTextColor(Color.WHITE);
        }
        button.setText(builder.text);
        button.setId(builder.id);
        if(builder.drawableResourceId != -1){
            button.setBackgroundResource(builder.drawableResourceId);
        }else{
            button.setBackgroundResource(R.drawable.button_round_survey);
        }
        if(builder.formLayout != null){
            builder.formLayout.addView(button);
        }
        if(builder.onClickListener != null){
            button.setOnClickListener(builder.onClickListener);
        }
        this.button = button;
    }

    public View getView(){
        return this.button;
    }
}
