package com.paperplay.myformbuilder.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paperplay.myformbuilder.R;
import com.paperplay.myformbuilder.adapter.AutocompleteAdapter;
import com.paperplay.myformbuilder.model.AutocompleteData;
import com.paperplay.myformbuilder.modules.GeneralBuilder;

import java.util.ArrayList;

/**
 * Created by Ahmed Yusuf on 18/11/18.
 */
public class MyAutocomplete extends LinearLayout {
    String title;
    Context context;
    LinearLayout formLayout;
    TextView txtTitle;
    AppCompatAutoCompleteTextView autoCompleteTextView;
    View view;
    ArrayList<AutocompleteData> item = new ArrayList<>(); //full item
    ArrayList<AutocompleteData> itemDropDown = new ArrayList<>();
    boolean nullable = false;
    OnSelectedListener onSelectedListener;
    AutocompleteAdapter autocompleteAdapter;

    AutocompleteData autocompleteDataSelected;
    Activity activity;
    String defaultSelectedValue = null;
    int defaultSelectedId = -1;
    boolean validValue = false;
    
    public static class Builder implements GeneralBuilder<Builder>, Cloneable {
        //required
        Activity activity;
        Context context;

        //optional
        ArrayList<AutocompleteData> item;
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

        public Builder setItem(ArrayList<AutocompleteData> item) {
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

        public MyAutocomplete create(){
            return new MyAutocomplete(this);
        }

        @Override
        public Builder clone() throws CloneNotSupportedException{
            return (Builder)super.clone();
        }

    }

    public MyAutocomplete(Builder builder){
        super(builder.context, null, builder.defStyleAttr);
        this.title = builder.title;
        this.context = builder.context;
        this.activity = builder.activity;
        if(builder.formViewResource != -1){
            this.view  = LayoutInflater.from(context).inflate(builder.formViewResource, null);
        }else {
            this.view = LayoutInflater.from(context).inflate(R.layout.form_autocomplete, null);
        }
        txtTitle = view.findViewById(R.id.item_atc_title);
        txtTitle.setText(title);
        autoCompleteTextView = view.findViewById(R.id.item_atc_list);
        if(this.item != null) {
            this.item.addAll(builder.item); //set as backup data
        }
        for (AutocompleteData items : builder.item){ //show all but hidden item
            if(!items.isHidden()) this.itemDropDown.add(items);
        }

        if(builder.formLayout!=null){
            this.formLayout = builder.formLayout;
            this.formLayout.addView(this.view);
        }

        this.defaultSelectedValue = builder.defaultSelectedValue;
        this.defaultSelectedId = builder.defaultSelectedId;

        setAdapter();

    }

    private void setAdapter(){
        autocompleteAdapter = new AutocompleteAdapter(this.activity,
                R.layout.row_autocomplete, this.itemDropDown);
        autoCompleteTextView.setAdapter(autocompleteAdapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            autocompleteDataSelected = (AutocompleteData) parent.getItemAtPosition(position);
        });
        if(defaultSelectedValue != null){
            setValue(defaultSelectedValue);
        }
        if(defaultSelectedId != -1){
            setId(defaultSelectedId);
        }
        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                validValue = false;
                if(autocompleteDataSelected == null) {
                    autoCompleteTextView.setText("");
                    return;
                }
                if(!autoCompleteTextView.getText().toString().isEmpty()) {
                    for (AutocompleteData item : item) {
                        if (item.getValue().equals(autoCompleteTextView.getText().toString())) {
                            autocompleteDataSelected = item;
                            validValue = true;
                            break;
                        }
                    }
                }
                if(!validValue) {
                    autocompleteDataSelected = null;
                    autoCompleteTextView.setText("");
                }
            }
        });
        autoCompleteTextView.setOnTouchListener((v, event) -> {
            autoCompleteTextView.showDropDown();
            return false;
        });
    }

    public interface OnSelectedListener {
        void onSelectedData(AutocompleteData autocompleteData);
    }

    public void setSpinnerOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public int indexOfItem(AutocompleteData value){
        return itemDropDown.indexOf(value);
    }

    public View getView() {
        return view;
    }

    public void setValue(String value){
        if(value != null) {
//            AutocompleteData autocompleteData = null;
            for (AutocompleteData data : itemDropDown){
                if(data.getValue().equals(value)) {
                    autoCompleteTextView.setText(value);
                    autocompleteDataSelected = data;
                    validValue = true;
                    break;
                }
            }
        }
    }

    public void setId(int id){
        if(id != -1) {
            AutocompleteData autocompleteData = null;
            for (AutocompleteData data : itemDropDown){
                if(data.getId() == id) {
                    autocompleteData = data;
                    validValue = true;
                    break;
                }
            }
            autoCompleteTextView.setText(autocompleteData.getValue());
            autocompleteDataSelected = autocompleteData;
        }
    }

    public AutocompleteData getSelectedData(){
        if(this.view.getVisibility() == View.VISIBLE && autocompleteDataSelected!= null) {
            return autocompleteDataSelected;
        } else return null;
    }

    public String getSelectedValue(){
        if(this.view.getVisibility() == View.VISIBLE && autocompleteDataSelected!= null) {
            return autocompleteDataSelected.getValue();
        } else return null;
    }

    public int getSelectedId(){
        if(this.view.getVisibility() == View.VISIBLE && autocompleteDataSelected != null) {
            return autocompleteDataSelected.getId();
        } else return 0;
    }

    public void setItemDropDown(ArrayList<AutocompleteData> itemDropDown) {
        this.itemDropDown = itemDropDown;
        if(autocompleteAdapter!= null) autocompleteAdapter.notifyDataSetChanged();
    }

    public String getSelectedSecondaryId(){
        if(this.view.getVisibility() == View.VISIBLE) {
            return autocompleteDataSelected.getSecondaryId();
        }else return "0";
    }

    public void attachView(){
        formLayout.addView(this.view);
    }

    public void detachView(){
        formLayout.removeView(this.view);
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
        for (AutocompleteData items : item){
            if(items.getId() == id){
                items.setHidden(true);
                break;
            }
        }
        reloadListDropdown();
    }

    public void hideItemByValue(String value){
        for (AutocompleteData items : item){
            if(items.getValue().equals(value)){
                items.setHidden(true);
                break;
            }
        }
        reloadListDropdown();
    }

    public void showItemById(int id){
        for (AutocompleteData items : item){
            if(items.getId() == id){
                items.setHidden(false);
                break;
            }
        }
        reloadListDropdown();
    }


    public void showItemByValue(String value){
        for (AutocompleteData items : item){
            if(items.getValue().equals(value)){
                items.setHidden(false);
                break;
            }
        }
        reloadListDropdown();
    }

    private void reloadListDropdown(){
        itemDropDown.clear();
        for (AutocompleteData items : item){
            if(!items.isHidden()) itemDropDown.add(items);
        }
        autocompleteAdapter.notifyDataSetChanged();
    }

    /**
     * Update list dropdown with new list
     * @param data
     */
    public void updateListDropdown(ArrayList<AutocompleteData> data){
        if(data!=null && autocompleteAdapter!=null) {
            this.item.clear();
            this.itemDropDown.clear();
            this.item.addAll(data);
            for (AutocompleteData items : data) { //show all but hidden item
                if (!items.isHidden()) this.itemDropDown.add(items);
            }
            setAdapter();
        }
    }
}
