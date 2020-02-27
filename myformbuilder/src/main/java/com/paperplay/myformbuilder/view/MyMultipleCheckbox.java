package com.paperplay.myformbuilder.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paperplay.myformbuilder.R;
import com.paperplay.myformbuilder.adapter.CheckboxAdapter;
import com.paperplay.myformbuilder.model.CheckboxData;
import com.paperplay.myformbuilder.modules.GeneralBuilder;

import java.util.ArrayList;

/**
 * Created by Ahmed Yusuf on 18/11/18.
 */
@SuppressLint("ValidFragment")
public class MyMultipleCheckbox extends LinearLayout{
    public enum SelectedBy{
        ID,
        VALUE
    }
    Context context;
    Activity activity;
    LinearLayout formLayout;
    TextView txtTitle;
    EditText editTextContent;
    View view;
    boolean nullable;
    String title;
    ArrayList<CheckboxData> list;
    AlertDialog alertDialogMultipleCheckbox;

    ArrayList<CheckboxData> listSelected = new ArrayList<>();

    RecyclerView listViewCheckbox;

    StringBuilder selectedValue, selectedId;
    CheckboxAdapter adapter = null;

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
        int orientation = LinearLayout.VERTICAL;
        int inputType = -1;
        int titleColorResource = -1;
        int defStyleAttr = R.style.AppTheme;
        int formViewResource = -1;
        ArrayList<CheckboxData> list;

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

        public Builder setItem(ArrayList<CheckboxData> list){
            this.list = list;
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

        public MyMultipleCheckbox create(){
            return new MyMultipleCheckbox(this);
        }

        @Override
        public MyMultipleCheckbox.Builder clone() throws CloneNotSupportedException{
            return (MyMultipleCheckbox.Builder)super.clone();
        }
    }

    public MyMultipleCheckbox(Builder builder) {
        super(builder.context, null, builder.defStyleAttr);
        this.context = builder.context;
        this.activity = builder.activity;
        this.nullable = builder.nullable;
        this.title = builder.title;
        this.list = builder.list;

        // change form view resource
        if(builder.formViewResource != -1){
            this.view  = LayoutInflater.from(builder.context).inflate(builder.formViewResource, null);
        }else {
            this.view = LayoutInflater.from(builder.context).inflate(R.layout.form_edittext, null);
        }
        txtTitle = view.findViewById(R.id.item_edittext_title);
        editTextContent = view.findViewById(R.id.item_edittext_value);
        editTextContent.setMinLines(builder.minLines);
        editTextContent.setTag(builder.title);

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
        createAlertDialog();
        editTextContent.setOnClickListener(v -> alertDialogMultipleCheckbox.show());
        editTextContent.setKeyListener(null);
    }

    @SuppressLint("NewApi")
    public void setBackground(Drawable drawable){
        editTextContent.setBackground(drawable);
    }

    public void setValue(String text){
        if(text != null)
            editTextContent.setText(text);
    }

    public void setSelected(ArrayList<String> dataList, SelectedBy selectedBy){
        if(dataList!=null && adapter!=null && list!=null) {
            int n = 0;
            selectedId = new StringBuilder();
            selectedValue = new StringBuilder();
            for (String data : dataList) {
                for (CheckboxData checkboxData : list) {
                    if ((selectedBy == SelectedBy.ID && String.valueOf(checkboxData.getId()).equals(data)) ||
                            (selectedBy == SelectedBy.VALUE && String.valueOf(checkboxData.getValue()).equals(data))) {
                        checkboxData.setChecked(true);
                        selectedId.append((n > 0) ? ", " : "").append(checkboxData.getId());
                        selectedValue.append((n > 0) ? ", " : "").append(checkboxData.getValue());
                        n++;
                        break;
                    }
                }
            }
            editTextContent.setText(selectedValue.toString());
            adapter.notifyDataSetChanged();
        }
    }

    public void updateListCheckbox(ArrayList<CheckboxData> list){
        this.list.clear();
        this.list.addAll(list);
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
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
        return String.valueOf(editTextContent.getText());
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }


    private void createAlertDialog(){
        if(alertDialogMultipleCheckbox == null) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater dialogInflater = activity.getLayoutInflater();
            View dialogView = dialogInflater.inflate(R.layout.dialog_multiple_checkbox, null);
            dialogBuilder.setView(dialogView);
            TextView txtTitle = dialogView.findViewById(R.id.txtFormTitle);
            listViewCheckbox = dialogView.findViewById(R.id.listCheckbox);

            adapter = new CheckboxAdapter(context, list);
            listViewCheckbox.setAdapter(adapter);
            listViewCheckbox.setLayoutManager(new LinearLayoutManager(activity.getBaseContext()));

            txtTitle.setText(title);
            dialogBuilder.setPositiveButton("Save", (dialog, which) -> {
                listSelected.clear();
                selectedValue = new StringBuilder();
                selectedId = new StringBuilder();
                int n = 0;
                for (CheckboxData checkboxData: adapter.getCheckboxListChecked()){
                    if(checkboxData.isChecked()){
                        selectedId.append((n > 0) ? ", " : "").append(checkboxData.getId());
                        selectedValue.append((n > 0) ? ", " : "").append(checkboxData.getValue());
                        n++;
                    }
                }
                listSelected.addAll(adapter.getCheckboxListChecked());
                editTextContent.setText(selectedValue.toString());
                alertDialogMultipleCheckbox.dismiss();
            });

            dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> alertDialogMultipleCheckbox.dismiss());

            alertDialogMultipleCheckbox = dialogBuilder.create();
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

    public void attachView(){
        formLayout.addView(this.view);
    }

    public void detachView(){
        formLayout.removeView(this.view);
    }

    public String getSelectedValue() {
        return selectedValue.toString();
    }

    public String getSelectedId() {
        return selectedId.toString();
    }

    public ArrayList<CheckboxData> getSelectedCheckbox(){
        return listSelected;
    }

}

