package com.paperplay.myformbuilder;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.paperplay.myformbuilder.modules.GeneralBuilder;

import java.util.ArrayList;

/**
 * Created by Ahmed Yusuf on 18/11/18.
 */
public class MySpinner extends LinearLayout {
    String title;
    Context context;
    LinearLayout formLayout;
    TextView txtTitle;
    Spinner spinnerAnswer;
    View view;
    ArrayList<String> item;
    boolean nullable = false;
    OnSelectedListener onSelectedListener;
    
    public static class Builder implements GeneralBuilder<Builder>, Cloneable {
        //required
        Activity activity;
        Context context;

        //optional
        ArrayList<String> item;
        String title;
        String titleFont;
        int titleColorResource = -1;
        boolean nullable;
        Drawable backgroundDrawable;
        int orientation = LinearLayout.VERTICAL;
        int defStyleAttr = R.style.AppTheme;
        String defaultSelected = null;
        LinearLayout formLayout;

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
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        @Override
        public Builder setTitleFont(String titleFont) {
            this.titleFont = titleFont;
            return this;
        }

        @Override
        public Builder setTitleColorResource(int titleColorResource){
            this.titleColorResource = titleColorResource;
            return this;
        }

        @Override
        public Builder setOrientation(int orientation){
            this.orientation = orientation;
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

        public Builder setBackgroundDrawable(Drawable backgroundDrawable){
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }


        public Builder setItem(ArrayList<String> item) {
            this.item = item;
            return this;
        }

        public Builder setDefaultSelected(String defaultSelected) {
            this.defaultSelected = defaultSelected;
            return this;
        }

        public MySpinner create(){
            return new MySpinner(this);
        }

        @Override
        public Builder clone() throws CloneNotSupportedException{
            return (Builder)super.clone();
        }

    }

    public MySpinner(Builder builder){
        super(builder.context, null, builder.defStyleAttr);
        this.title = builder.title;
        this.context = builder.context;
        this.view  = LayoutInflater.from(context).inflate(R.layout.form_dropdown, null);
        LinearLayout layout = (LinearLayout) this.view.findViewById(R.id.mainLayout);
        layout.setOrientation(builder.orientation);
        txtTitle = (TextView)view.findViewById(R.id.item_spinner_title);
        txtTitle.setText(title);
        spinnerAnswer = (Spinner)view.findViewById(R.id.item_spinner_list);
        this.item = builder.item;
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(context,
                R.layout.row_spinner, builder.item);
        spinnerAnswer.setAdapter(arrayAdapter);
        spinnerAnswer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(onSelectedListener !=null)
                    onSelectedListener.onSelected(item.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(builder.defaultSelected != null){
            setValue(builder.defaultSelected);
        }
        if(builder.formLayout!=null){
            this.formLayout = builder.formLayout;
            this.formLayout.addView(this.view);
        }
    }

    public interface OnSelectedListener {
        void onSelected(String value);
    }

    public void setSpinnerOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public int indexOfItem(String value){
        return item.indexOf(value);
    }

    public View getView() {
        return view;
    }

    public void setValue(String value){
        if(value != null) {
            spinnerAnswer.setSelection(indexOfItem(value));
        }
    }

    public String getValue(){
        if(this.view.getVisibility() == View.VISIBLE)
            return String.valueOf(spinnerAnswer.getSelectedItem());
        else return null;
    }

    public void attachView(){
        formLayout.addView(this.view);
    }

    public void detachView(){
        formLayout.removeView(this.view);
    }

    public int getIndexSelected(){
        return spinnerAnswer.getSelectedItemPosition();
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }
}
