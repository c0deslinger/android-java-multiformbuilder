package com.paperplay.myformbuilder.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paperplay.myformbuilder.R;
import com.paperplay.myformbuilder.function.ViewChecker;
import com.paperplay.myformbuilder.modules.GeneralBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ahmed Yusuf on 18/11/18.
 */
@SuppressLint("ValidFragment")
public class MyEdittext extends LinearLayout{
    Context context;
    Activity activity;
    LinearLayout formLayout;
    LinearLayout mainLayout;
    TextView txtTitle;
    EditText editTextContent;
    View view;

    Calendar myCalendar = Calendar.getInstance();
    DateSetListener myDateSetListener;
    boolean nullable;

    String title;
    String dateformat = "yyyy-MM-dd";
    DatePickerDialog datePickerDialog;

    //search mode
    OnClickSearchListener onClickSearchListener;
    ImageButton btnSearch;

    //edittext mode
    public enum Mode {
        GENERAL,
        SEARCH
    }

    public static class Builder implements GeneralBuilder<Builder>, Cloneable{
        //required
        Activity activity;
        Context context;

        //optional
        LinearLayout formLayout;
        String title;
        int minLines;
        String dateformat;
        Drawable backgroundDrawable;
        String titleFont;
        boolean nullable;
        Mode mode;
        int orientation = LinearLayout.VERTICAL;
        int inputType = -1;
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

        @Override
        public Builder setFormLayout(LinearLayout formLayout){
            this.formLayout = formLayout;
            return this;
        }

        public Builder setDateformat(String dateformat){
            this.dateformat = dateformat;
            return this;
        }

        public Builder setMode(Mode mode){
            this.mode = mode;
            return this;
        }

        public Builder setMinLines(int minLines){
            this.minLines = minLines;
            return this;
        }

        public Builder setInputType(int inputType){
            this.inputType = inputType;
            return this;
        }

        public Builder setFormViewResource(int resource){
            this.formViewResource = resource;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable){
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public MyEdittext create(){
            return new MyEdittext(this);
        }

        @Override
        public MyEdittext.Builder clone() throws CloneNotSupportedException{
            return (MyEdittext.Builder)super.clone();
        }
    }

    public MyEdittext(Builder builder) {
        super(builder.context, null, builder.defStyleAttr);
        this.context = builder.context;
        this.activity = builder.activity;
        this.nullable = builder.nullable;
        this.title = builder.title;
        // change form view resource
        if(builder.formViewResource != -1){
            this.view  = LayoutInflater.from(builder.context).inflate(builder.formViewResource, null);
        }else {
            this.view = LayoutInflater.from(builder.context).inflate(R.layout.form_edittext, null);
        }
        if(builder.mode == Mode.SEARCH){
            btnSearch = this.view.findViewById(R.id.btnSearch);
            btnSearch.setVisibility(View.VISIBLE);
            btnSearch.setOnClickListener(view -> {
                if(onClickSearchListener!=null)
                    onClickSearchListener.onClickSearch(editTextContent.getText().toString());
            });
        }
        mainLayout = this.view.findViewById(R.id.mainLayoutEdittext);
        mainLayout.setOrientation(builder.orientation);
        txtTitle = view.findViewById(R.id.item_edittext_title);
        editTextContent = view.findViewById(R.id.item_edittext_value);
        editTextContent.setMinLines(builder.minLines);
        editTextContent.setTag(builder.title);
        if(builder.inputType == InputType.TYPE_CLASS_DATETIME) {
            if(dateformat!=null){
                this.dateformat = builder.dateformat;
            }
            datePickerDialog = new DatePickerDialog(activity, dateSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            editTextContent.setFocusableInTouchMode(false);
            editTextContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    datePickerDialog.show();
                }
            });
            editTextContent.setKeyListener(null);
            editTextContent.setInputType(InputType.TYPE_NULL);
        }else{
            if(builder.inputType != -1)
                editTextContent.setInputType(builder.inputType);
        }

        //set optional value
        if(builder.title!=null){
            txtTitle.setText(builder.title);
        }
        if(builder.backgroundDrawable!=null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            editTextContent.setBackground(builder.backgroundDrawable);
        }
        if(builder.titleFont!=null){
            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    builder.titleFont);
            txtTitle.setTypeface(face);
        }
        if(builder.titleColorResource != -1){
            txtTitle.setTextColor(ContextCompat.getColor(context, builder.titleColorResource));
        }
        if(builder.formLayout!=null){
            this.formLayout = builder.formLayout;
            this.formLayout.addView(this.view);
        }
    }

    @SuppressLint("NewApi")
    public void setBackground(Drawable drawable){
        editTextContent.setBackground(drawable);
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if(dateformat == null){
                dateformat = "yyyy-MM-dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(dateformat, Locale.US);
            editTextContent.setText(sdf.format(myCalendar.getTime()));

            if(myDateSetListener!=null)
                myDateSetListener.onDateSet(sdf.format(myCalendar.getTime()));
        }
    };

    public void setValue(String text){
        if(text != null)
            editTextContent.setText(text);
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public EditText getEditTextContent() {
        return editTextContent;
    }

    public View getView() {
        return view;
    }

    public String getValue(){
        if(getView().getVisibility() == View.GONE)
            return null;
        return String.valueOf(editTextContent.getText().toString());
    }

    public interface DateSetListener{
        void onDateSet(String date);
    }

    public void setMyDateSetListener(DateSetListener myDateSetListener) {
        this.myDateSetListener = myDateSetListener;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public KeyListener getKeyListener(){
        return editTextContent.getKeyListener();
    }

    public void setEditable(boolean editable, KeyListener keyListener){
        if(editable){
            editTextContent.setKeyListener(keyListener);
        }else{
            editTextContent.setKeyListener(null);
        }
    }

    public boolean isFilled() {
        if(!nullable){
            if(editTextContent.getText().toString().isEmpty())
                return false;
        }
        return true;
    }

    public String getTitle() {
        return title;
    }

    public interface OnClickSearchListener{
        void onClickSearch(String keyword);
    }

    public void setOnClickSearchListener(MyEdittext.OnClickSearchListener onClickSearchListener) {
        this.onClickSearchListener = onClickSearchListener;
    }

    public void attachView(){
        formLayout.addView(this.view);
    }

    public void detachView(){
        formLayout.removeView(this.view);
    }

    /**
     * Check edittext is empty or not
     */
    public boolean checkMustFilled(){
        return ViewChecker.isFilled(this, this.context);
    }
}

