package com.paperplay.myformbuilder;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.paperplay.myformbuilder.modules.GeneralBuilder;

import java.util.HashMap;

/**
 * Created by Ahmed Yusuf on 18/11/18.
 */
public class MyRadioButton extends LinearLayout{
    Activity activity;
    Context context;
    TextView txtTitle;
    RadioGroup radioGroup;
    String value = null;
    View view;
    HashMap<String, RadioButton> radioButtonList = new HashMap();

    OnSelectedListener onSelectedListener;
    boolean nullable = false;

    String title;
    String[] optionListString;

    public static class Builder implements GeneralBuilder<Builder>, Cloneable{
        //required
        Activity activity;
        Context context;

        //optional
        String title;
        String titleFont;
        int titleColorResource = R.color.dark_grey;
        int orientation = LinearLayout.VERTICAL;
        String[] optionList;
        int[] tintColorList = null;
        int optionColorResource = R.color.dark_grey;
        int defStyleAttr = R.style.AppTheme;
        boolean nullable = true;
        String selected;

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
        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        @Override
        public Builder setTitleColorResource(int titleColorResource){
            this.titleColorResource = titleColorResource;
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
        public Builder setNullable(boolean nullable){
            this.nullable = nullable;
            return this;
        }

        @Override
        public Builder setDefStyleAttr(int defStyleAttr) {
            this.defStyleAttr = defStyleAttr;
            return this;
        }

        public Builder setOptionList(String[] optionList){
            this.optionList = optionList;
            return this;
        }

        public Builder setTintColorList(int[] tintColorList){
            this.tintColorList = tintColorList;
            return this;
        }

        public Builder setOptionColorResource(int optionColorResource){
            this.optionColorResource = optionColorResource;
            return this;
        }

        public Builder setSelected(String selected) {
            this.selected = selected;
            return this;
        }

        public MyRadioButton create(){
            return new MyRadioButton(this);
        }

        @Override
        public Object clone() throws CloneNotSupportedException{
            return (MyRadioButton.Builder)super.clone();
        }
    }

    public MyRadioButton(Builder builder){
        super(builder.context, null, builder.defStyleAttr);
        this.context = builder.context;
        this.activity = builder.activity;
        this.title = builder.title;
        this.optionListString = builder.optionList;
        this.view  = LayoutInflater.from(builder.context).inflate(R.layout.form_radiobutton, null);
        txtTitle = (TextView)view.findViewById(R.id.item_textview_title);
        txtTitle.setText(title);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        LinearLayout layout = (LinearLayout) this.view.findViewById(R.id.mainLayout);
        layout.setOrientation(builder.orientation);
        for (String option : optionListString){
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(option);
            if(builder.tintColorList == null) {
                builder.tintColorList = new int[]{ContextCompat.getColor(context, R.color.dark_grey),
                        ContextCompat.getColor(context, R.color.dark_grey)};
            }
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled}, //disabled
                            new int[]{android.R.attr.state_enabled} //enabled
                    },
                    builder.tintColorList
            );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                radioButton.setButtonTintList(colorStateList);//set the color tint list
            }
            radioButton.setTextColor(ContextCompat.getColor(context, builder.optionColorResource));
            radioButton.invalidate(); //could not be necessary
            radioGroup.addView(radioButton);
            radioButtonList.put(option, radioButton);
        }
        if(builder.selected!=null && radioButtonList.get(builder.selected)!=null){
            radioButtonList.get(builder.selected).setChecked(true);
            this.value = builder.selected;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                value = radioButton.getText().toString();
                if(onSelectedListener != null)
                    onSelectedListener.selected(value);
            }
        });
    }

    public void setSelected(String selected){
        if(selected!=null && radioButtonList.containsKey(selected)){
            ((RadioButton)radioButtonList.get(selected)).setChecked(true);
            this.value = selected;
        }
    }

    public View getView() {
        return view;
    }

    public String getValue(){
        if(getView().getVisibility() == View.GONE)
            return null;
        return String.valueOf(value);
    }

    public void setValue(String value){
        if(value!=null && radioButtonList.containsKey(value)){
            ((RadioButton)radioButtonList.get(value)).setChecked(true);
        }
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public interface OnSelectedListener {
        void selected(String value);
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isFilled() {
        if(view.getVisibility() == View.VISIBLE && nullable){
            if(value == null || value.isEmpty())
                return false;
        }
        return true;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }


}
