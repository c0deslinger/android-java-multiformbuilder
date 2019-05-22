package com.paperplay.myformbuilder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.paperplay.myformbuilder.function.ViewChecker;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Ahmed Yusuf on 18/11/18.
 */
@SuppressLint("ValidFragment")
public class MyEdittextMultiple {
    Context context;
    Activity activity;
    HashMap<String, MyEdittext> edtList = new HashMap<>();
    View view;

    public static class Builder{
        Context context;
        Activity activity;
        HashMap<String, MyEdittext> edtList = new HashMap<>();
        int margin;

        public Builder() {
        }

        public Builder(Activity activity, HashMap<String, MyEdittext> edtList) {
            this.context = activity.getBaseContext();
            this.activity = activity;
            this.edtList = edtList;
        }

        public Builder setMargin(int margin){
            this.margin = margin;
            return this;
        }

        public MyEdittextMultiple create(){
            return new MyEdittextMultiple(this);
        }
    }

    public MyEdittextMultiple(Builder builder) {
        this.context = builder.context;
        this.activity = builder.activity;
        this.edtList = builder.edtList;
        LinearLayout linearLayout = new LinearLayout(builder.context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        int margin = builder.margin;

        Iterator<HashMap.Entry<String, MyEdittext>> iterator = edtList.entrySet().iterator();
        boolean isFirst = true;
        while (iterator.hasNext()){
            HashMap.Entry<String, MyEdittext> pair = (HashMap.Entry<String, MyEdittext>) iterator.next();
            View v = edtList.get(pair.getKey()).getView();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            //if it's not a first item, add left margin
            if(!isFirst) {
                layoutParams.leftMargin = margin / 2;
            }
            //if it's not a last item, add right margin
            if(iterator.hasNext()) {
                layoutParams.rightMargin = margin / 2;
            }
            v.setLayoutParams(layoutParams);
            linearLayout.addView(v);
            isFirst = false;
        }
        this.view = linearLayout;
    }

    public String getValue(String key){
        if(edtList.get(key)==null)
            return null;
        return edtList.get(key).getValue();
    }

    public View getView() {
        return view;
    }

    public HashMap<String, MyEdittext> getEdtList() {
        return edtList;
    }

    /**
     * Check all edittext is empty or not
     */
    public boolean checkMustFilled(){
        return ViewChecker.isFilled(this, this.context);
    }
}

