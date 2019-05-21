package com.paperplay.myformbuilder.customview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import com.paperplay.myformbuilder.customview.interfaces.GeneralBuilderInterface;

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
    TextView textViewQuestion;
    EditText editTextAnswer;
    View view = null;

    Calendar myCalendar = Calendar.getInstance();
    DateSetListener myDateSetListener;
    boolean nullable = false;

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

    public static class Builder implements GeneralBuilderInterface<Builder>, Cloneable{
        //required
        Activity activity;
        Context context;

        //optional
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

    @SuppressLint("NewApi")
    public MyEdittext(Builder builder) {
        super(builder.context, null, builder.defStyleAttr);
        this.context = builder.context;
        this.activity = builder.activity;
        this.nullable = builder.nullable;
        this.title = builder.title;
        this.view  = LayoutInflater.from(builder.context).inflate(R.layout.form_edittext, null);
        if(builder.mode == Mode.SEARCH){
            btnSearch = (ImageButton) this.view.findViewById(R.id.btnSearch);
            btnSearch.setVisibility(View.VISIBLE);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickSearchListener!=null)
                        onClickSearchListener.onClickSearch(editTextAnswer.getText().toString());
                }
            });
        }
        LinearLayout layout = (LinearLayout) this.view.findViewById(R.id.mainLayout);
        layout.setOrientation(builder.orientation);
        textViewQuestion = (TextView)view.findViewById(R.id.item_edittext_title);
        editTextAnswer = (EditText)view.findViewById(R.id.item_edittext_value);
        editTextAnswer.setMinLines(builder.minLines);
        editTextAnswer.setTag(builder.title);
        if(builder.inputType == InputType.TYPE_CLASS_DATETIME) {
            if(dateformat!=null){
                this.dateformat = builder.dateformat;
            }
            datePickerDialog = new DatePickerDialog(activity, dateSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            editTextAnswer.setFocusableInTouchMode(false);
            editTextAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datePickerDialog.show();
                }
            });
            editTextAnswer.setKeyListener(null);
            editTextAnswer.setInputType(InputType.TYPE_NULL);
        }else{
            if(builder.inputType != -1)
                editTextAnswer.setInputType(builder.inputType);
        }

        //set optional value
        if(builder.title!=null){
            textViewQuestion.setText(builder.title);
        }
        if(builder.backgroundDrawable!=null){
            editTextAnswer.setBackground(builder.backgroundDrawable);
        }
        if(builder.titleFont!=null){
            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    builder.titleFont);
            textViewQuestion.setTypeface(face);
        }
        if(builder.titleColorResource != -1){
            textViewQuestion.setTextColor(ContextCompat.getColor(context, builder.titleColorResource));
        }
    }

//    @SuppressLint("NewApi")
//    public MyEdittextDatepickerView(String title, Builder builder){
//        this.title = title;
//        this.context = builder.context;
//        this.activity = builder.activity;
//        this.dateformat = builder.dateformat;
//        this.view  = LayoutInflater.from(context).inflate(R.layout.form_edittext, null);
//        textViewTitle = (TextView)view.findViewById(R.id.item_edittext_title);
//        textViewTitle.setText(title);
//        editTextAnswer = (EditText)view.findViewById(R.id.item_edittext_value);
//        editTextAnswer.setFocusableInTouchMode(false);
//        datePickerDialog = new DatePickerDialog(activity, dateSetListener, myCalendar
//                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                myCalendar.get(Calendar.DAY_OF_MONTH));
//        editTextAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                datePickerDialog.show();
//            }
//        });
//        editTextAnswer.setKeyListener(null);
//        editTextAnswer.setInputType(InputType.TYPE_NULL);
//        if(this.backgroundDrawable!=null){
//            editTextAnswer.setBackground(this.backgroundDrawable);
//        }
//    }
//
//    @SuppressLint("ValidFragment")
//    public MyEdittextDatepickerView(Context context, String title, final Activity activity) {
//        this.context = context;
//        this.title = title;
//        this.view  = LayoutInflater.from(context).inflate(R.layout.form_edittext, null);
//        textViewTitle = (TextView)view.findViewById(R.id.item_edittext_title);
//        textViewTitle.setText(title);
//        editTextAnswer = (EditText)view.findViewById(R.id.item_edittext_value);
//        editTextAnswer.setFocusableInTouchMode(false);
//        editTextAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(activity, dateSetListener, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        editTextAnswer.setKeyListener(null);
//        editTextAnswer.setInputType(InputType.TYPE_NULL);
//    }
//
//    @SuppressLint("ValidFragment")
//    public MyEdittextDatepickerView(Context context, String title, String dateformat, final Activity activity) {
//        this.context = context;
//        this.title = title;
//        this.view  = LayoutInflater.from(context).inflate(R.layout.form_edittext, null);
//        this.dateformat = dateformat;
//        textViewTitle = (TextView)view.findViewById(R.id.item_edittext_title);
//        textViewTitle.setText(title);
//        editTextAnswer = (EditText)view.findViewById(R.id.item_edittext_value);
//        editTextAnswer.setFocusableInTouchMode(false);
//        editTextAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(activity, dateSetListener, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        editTextAnswer.setKeyListener(null);
//        editTextAnswer.setInputType(InputType.TYPE_NULL);
//    }

    @SuppressLint("NewApi")
    public void setBackground(Drawable drawable){
        editTextAnswer.setBackground(drawable);
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
            editTextAnswer.setText(sdf.format(myCalendar.getTime()));

            if(myDateSetListener!=null)
                myDateSetListener.onDateSet(sdf.format(myCalendar.getTime()));
        }
    };

    public void setValue(String text){
        if(text != null)
            editTextAnswer.setText(text);
    }

    public TextView getTextViewQuestion() {
        return textViewQuestion;
    }

    public EditText getEditTextAnswer() {
        return editTextAnswer;
    }

    public View getView() {
        return view;
    }

    public String getValue(){
        if(getView().getVisibility() == View.GONE)
            return null;
        return String.valueOf(editTextAnswer.getText().toString());
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
        return editTextAnswer.getKeyListener();
    }

    public void setEditable(boolean editable, KeyListener keyListener){
        if(editable){
            editTextAnswer.setKeyListener(keyListener);
        }else{
            editTextAnswer.setKeyListener(null);
        }
    }

    public boolean isFilled() {
        if(!nullable){
            if(editTextAnswer.getText().toString().isEmpty())
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


}

