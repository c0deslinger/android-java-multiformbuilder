package com.paperplay.myformbuilder.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.paperplay.myformbuilder.R;
import com.paperplay.myformbuilder.adapter.SpinnerAdapter;
import com.paperplay.myformbuilder.modules.GeneralBuilder;
import com.paperplay.myformbuilder.model.SpinnerData;

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
    ArrayList<SpinnerData> item = new ArrayList<>(); //full item
    ArrayList<SpinnerData> itemDropDown = new ArrayList<>();
    boolean nullable = false;
    OnSelectedListener onSelectedListener;
    SpinnerAdapter spinnerAdapter;
    
    public static class Builder implements GeneralBuilder<Builder>, Cloneable {
        //required
        Activity activity;
        Context context;

        //optional
        ArrayList<SpinnerData> item;
        String title;
        String titleFont;
        int titleColorResource = -1;
        boolean nullable;
        Drawable backgroundDrawable;
        int orientation = LinearLayout.VERTICAL;
        int defStyleAttr = R.style.AppTheme;
        String defaultSelectedValue = null;
        int defaultSelectedId = -1;
        LinearLayout formLayout;
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


        public Builder setFormViewResource(int resource){
            this.formViewResource = resource;
            return this;
        }

        public Builder setItem(ArrayList<SpinnerData> item) {
            this.item = item;
            return this;
        }

        public Builder setDefaultSelectedValue(String defaultSelectedValue) {
            this.defaultSelectedValue = defaultSelectedValue;
            return this;
        }

        public Builder setDefaultSelectedId(int defaultSelectedId) {
            this.defaultSelectedId = defaultSelectedId;
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
        if(builder.formViewResource != -1){
            this.view  = LayoutInflater.from(context).inflate(builder.formViewResource, null);
        }else {
            this.view = LayoutInflater.from(context).inflate(R.layout.form_dropdown, null);
        }
        LinearLayout layout = this.view.findViewById(R.id.mainLayout);
        layout.setOrientation(builder.orientation);
        txtTitle = view.findViewById(R.id.item_spinner_title);
        txtTitle.setText(title);
        spinnerAnswer = view.findViewById(R.id.item_spinner_list);
        this.item.addAll(builder.item); //set as backup data
        for (SpinnerData items : builder.item){ //show all but hidden item
            if(!items.isHidden()) this.itemDropDown.add(items);
        }
        spinnerAdapter = new SpinnerAdapter(context,
                R.layout.row_spinner, this.itemDropDown);
        spinnerAnswer.setAdapter(spinnerAdapter);
        spinnerAnswer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(onSelectedListener !=null) {
                    onSelectedListener.onSelectedData(item.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(builder.defaultSelectedValue != null){
            setValue(builder.defaultSelectedValue);
        }
        if(builder.defaultSelectedId != -1){
            setId(builder.defaultSelectedId);
        }
        if(builder.formLayout!=null){
            this.formLayout = builder.formLayout;
            this.formLayout.addView(this.view);
        }
    }

    public interface OnSelectedListener {
        void onSelectedData(SpinnerData spinnerData);
    }

    public void setSpinnerOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public int indexOfItem(SpinnerData value){
        return itemDropDown.indexOf(value);
    }

    public View getView() {
        return view;
    }

    public void setValue(String value){
        if(value != null) {
            System.out.println("set value: "+value);
            SpinnerData spinnerData = null;
            for (SpinnerData data : itemDropDown){
                if(data.getValue().equals(value)) {
                    spinnerData = data;
                    break;
                }
            }
            spinnerAnswer.setSelection(indexOfItem(spinnerData));
        }
    }

    public void setId(int id){
        if(id != -1) {
            SpinnerData spinnerData = null;
            for (SpinnerData data : itemDropDown){
                if(data.getId() == id) {
                    spinnerData = data;
                    break;
                }
            }
            spinnerAnswer.setSelection(indexOfItem(spinnerData));
        }
    }

    public String getSelectedValue(){
        if(this.view.getVisibility() == View.VISIBLE) {
            SpinnerData spinnerDataSelected = (SpinnerData) spinnerAnswer.getSelectedItem();
            return String.valueOf(spinnerDataSelected.getValue());
        } else return null;
    }

    public int getSelectedId(){
        if(this.view.getVisibility() == View.VISIBLE) {
            SpinnerData spinnerDataSelected = (SpinnerData) spinnerAnswer.getSelectedItem();
            return spinnerDataSelected.getId();
        } else return 0;
    }

    public String getSelectedSecondaryId(){
        if(this.view.getVisibility() == View.VISIBLE) {
            SpinnerData spinnerDataSelected = (SpinnerData) spinnerAnswer.getSelectedItem();
            return spinnerDataSelected.getSecondaryId();
        }else return "0";
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

    public void hideItemById(int id){
        for (SpinnerData items : item){
            if(items.getId() == id){
                items.setHidden(true);
                System.out.println("hide: "+items.getId());
                break;
            }
        }
        reloadListDropdown();
    }


    public void hideItemByValue(String value){
        for (SpinnerData items : item){
            if(items.getValue().equals(value)){
                items.setHidden(true);
                break;
            }
        }
        reloadListDropdown();
    }

    public void showItemById(int id){
        for (SpinnerData items : item){
            if(items.getId() == id){
                items.setHidden(false);
                break;
            }
        }
        reloadListDropdown();
    }


    public void showItemByValue(String value){
        for (SpinnerData items : item){
            if(items.getValue().equals(value)){
                items.setHidden(false);
                break;
            }
        }
        reloadListDropdown();
    }

    private void reloadListDropdown(){
        itemDropDown.clear();
        for (SpinnerData items : item){
            if(!items.isHidden()) itemDropDown.add(items);
        }
        spinnerAdapter.notifyDataSetChanged();
    }
}
