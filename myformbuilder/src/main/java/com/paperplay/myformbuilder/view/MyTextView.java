package com.paperplay.myformbuilder.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paperplay.myformbuilder.R;
import com.paperplay.myformbuilder.modules.GeneralBuilder;

/**
 * Created by Ahmed Yusuf on 18/11/18.
 */
public class MyTextView extends LinearLayout{
    String title;
    Context context;
    LinearLayout formLayout;
    Activity activity;
    TextView textViewTitle;
    TextView textViewContent;
    View view;

    public static class Builder implements GeneralBuilder<Builder>, Cloneable{
        //required
        Activity activity;
        Context context;
        LinearLayout formLayout;

        //optional
        String title;
        String titleFont;
        String content;
        String contentFont;
        boolean nullable;
        int orientation = LinearLayout.VERTICAL;
        int titleColorResource = -1;
        int defStyleAttr = R.style.AppTheme;
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

        public Builder setContent(String content){
            this.content = content;
            return this;
        }

        public Builder setFormViewResource(int resource){
            this.formViewResource = resource;
            return this;
        }

        public Builder setContentFont(String contentFont){
            this.contentFont = contentFont;
            return this;
        }

        public MyTextView create(){
            return new MyTextView(this);
        }

        @Override
        public Builder clone() throws CloneNotSupportedException{
            return (MyTextView.Builder)super.clone();
        }
    }
    
    public MyTextView(Builder builder) {
        super(builder.context, null, builder.defStyleAttr);
        this.context = builder.context;
        this.activity = builder.activity;
        this.title = builder.title;
        this.view  = LayoutInflater.from(context).inflate(R.layout.form_textview, null);
        LinearLayout layout = (LinearLayout) this.view.findViewById(R.id.mainLayout);
        layout.setOrientation(builder.orientation);
        textViewTitle = (TextView)view.findViewById(R.id.item_textview_title);
        textViewTitle.setText(builder.title);
        textViewContent = (TextView)view.findViewById(R.id.item_textview_value);
        textViewContent.setText(builder.content);
        if(builder.titleFont!=null){
            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    builder.titleFont);
            textViewTitle.setTypeface(face);
        }
        if(builder.contentFont !=null){
            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    builder.contentFont);
            textViewContent.setTypeface(face);
        }
        if(builder.titleColorResource != -1){
            textViewTitle.setTextColor(ContextCompat.getColor(context, builder.titleColorResource));
        }
        if(builder.formLayout!=null){
            this.formLayout = builder.formLayout;
            this.formLayout.addView(this.view);
        }
    }

    public void setContent(String text){
        textViewContent.setText(text);
    }

    public View getView() {
        return view;
    }

    public String getContent(){
        if(getView().getVisibility() == View.GONE)
            return null;
        return String.valueOf(textViewContent.getText().toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TextView getTextViewTitle() {
        return textViewTitle;
    }

    public TextView getTextViewContent() {
        return textViewContent;
    }

    public void attachView(){
        formLayout.addView(this.view);
    }

    public void detachView(){
        formLayout.removeView(this.view);
    }

}
