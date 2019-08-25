package com.paperplay.myformbuilder.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paperplay.myformbuilder.R;
import com.paperplay.myformbuilder.modules.GeneralBuilder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ahmed Yusuf on 18/11/18.
 */
public class MyCheckbox extends LinearLayout{
    String title;
    Activity activity;
    Context context;
    View view;
    boolean nullable;
    ArrayList<String> checkBoxItem;
    HashMap<String, CheckBox> checkBoxItemSelected = new HashMap<String, CheckBox>();
    TextView txtTitle;
    LinearLayout formLayout;
    
    public static class Builder implements GeneralBuilder<Builder>, Cloneable{
        //required
        Activity activity;
        Context context;
        LinearLayout formLayout;

        //optional
        String title;
        Drawable backgroundDrawable;
        String titleFont;
        boolean nullable;
        int orientation = LinearLayout.VERTICAL;
        int titleColorResource = -1;
        ArrayList<String> checkBoxItem = new ArrayList<>();
        int defStyleAttr = R.style.AppTheme;
        OnCheckedListener onCheckedListener;
        int formViewResource = -1;

        public Builder() {
        }

        public Builder(Activity activity) {
            this.activity = activity;
            this.context = activity.getBaseContext();
        }

        @Override
        public Builder setActivity(Activity activity) {
            this.activity = activity;
            this.context = activity.getBaseContext();
            return this;
        }

        @Override
        public Builder setFormLayout(LinearLayout formLayout){
            this.formLayout = formLayout;
            return this;
        }

        @Override
        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        @Override
        public Builder setTitleFont(String titleFont){
            this.titleFont = titleFont;
            return this;
        }

        @Override
        public Builder setOrientation(int orientation){
            this.orientation = orientation;
            return this;
        }

        @Override
        public Builder setTitleColorResource(int titleColorResource){
            this.titleColorResource = titleColorResource;
            return this;
        }

        @Override
        public Builder setNullable(boolean nullable){
            this.nullable = nullable;
            return this;
        }

        @Override
        public Builder setDefStyleAttr(int defStyleAttr) {
            this.defStyleAttr = defStyleAttr;
            return this;
        }

        public Builder setCheckBoxItem(ArrayList<String> checkBoxItem){
            this.checkBoxItem = checkBoxItem;
            return this;
        }

        public Builder setOnCheckedListener(OnCheckedListener onCheckedListener) {
            this.onCheckedListener = onCheckedListener;
            return this;
        }


        public Builder setFormViewResource(int resource){
            this.formViewResource = resource;
            return this;
        }

        public MyCheckbox create(){
            return new MyCheckbox(this);
        }

        public Object clone() throws CloneNotSupportedException{
            return super.clone();
        }
    }

    public MyCheckbox(Builder builder){
        super(builder.context, null, builder.defStyleAttr);
        this.context = builder.context;
        this.activity = builder.activity;
        this.nullable = builder.nullable;
        this.title = builder.title;
        this.checkBoxItem = builder.checkBoxItem;
        this.view  = LayoutInflater.from(context).inflate(R.layout.form_base_layout, null);
        LinearLayout baseLayout = view.findViewById(R.id.baseLayout);
        baseLayout.setOrientation(builder.orientation);
        txtTitle = view.findViewById(R.id.item_title);
        txtTitle.setText(builder.title);
        for (String title : checkBoxItem){
            View itemView = null;
            if(builder.formViewResource != -1){
                itemView = LayoutInflater.from(context).inflate(builder.formViewResource, null);
            }else {
                itemView = LayoutInflater.from(context).inflate(R.layout.form_checkbox, null);
            }
            TextView textViewQuestion = itemView.findViewById(R.id.item_checkbox_title);
            textViewQuestion.setText(title);
            CheckBox checkBoxAnswer = itemView.findViewById(R.id.item_checkbox_value);
            checkBoxItemSelected.put(title, checkBoxAnswer);
            checkBoxAnswer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean selected) {
                    if(selected)
                        builder.onCheckedListener.onchecked(title);
                }
            });
            baseLayout.addView(itemView);
        }
        if(builder.formLayout!=null){
            this.formLayout = builder.formLayout;
            this.formLayout.addView(this.view);
        }
    }

    public void setValue(String key, boolean value){
        if(checkBoxItemSelected.get(key) != null)
            checkBoxItemSelected.get(key).setChecked(value);
    }

    public View getView() {
        return view;
    }

    public boolean isChecked(String key){
        if(checkBoxItemSelected.get(key) == null)
            return false;
        return checkBoxItemSelected.get(key).isChecked();
    }

    public ArrayList<String> getAllChecked() {
        ArrayList<String> checkedList = new ArrayList<>();
        for(String key : checkBoxItemSelected.keySet()){
            if(checkBoxItemSelected.get(key).isChecked()){
                checkedList.add(key);
            }
        }
        return checkedList;
    }

    public boolean isAllChecked(){
        if(getView().getVisibility() == View.GONE)
            return false;
        else {
            boolean allChecked = true;
            for (String key : checkBoxItemSelected.keySet()){
                if(!checkBoxItemSelected.get(key).isChecked()){
                    allChecked = false;
                }
                break;
            }
            return allChecked;
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public interface checkListener{
        boolean isChecked();
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public interface OnCheckedListener{
        void onchecked(String checkboxName);
    }

    public void attachView(){
        formLayout.addView(this.view);
    }

    public void detachView(){
        formLayout.removeView(this.view);
    }

}
