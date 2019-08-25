package com.paperplay.androidformbuilder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.paperplay.myformbuilder.view.MyCheckbox;
import com.paperplay.myformbuilder.view.MyEdittext;
import com.paperplay.myformbuilder.view.MyEdittextMultiple;
import com.paperplay.myformbuilder.view.MyRadioButton;
import com.paperplay.myformbuilder.view.MySpinner;
import com.paperplay.myformbuilder.view.MyTextView;
import com.paperplay.myformbuilder.model.SpinnerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Ahmed Yusuf on 17/05/19.
 */
public class ExampleJavaActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout formLayout = findViewById(R.id.formLayout);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        /** Make template and set default value **/
        MyEdittext.Builder edtBuilder = new MyEdittext.Builder(ExampleJavaActivity.this).setFormLayout(formLayout);

        /** Attach View **/
        try {
            //create edittext with search
            MyEdittext edtId = edtBuilder.clone().setTitle("ID").setMode(MyEdittext.Mode.SEARCH).create();
            edtId.setOnClickListener(keyword -> toast("Search: "+keyword));

            //create general edittext
            MyEdittext edtName = edtBuilder.clone().setTitle("Name").create();

            //create radio button
            MyRadioButton rdbGender = new MyRadioButton.Builder(ExampleJavaActivity.this)
                    .setTitle("Gender").setOptionList(new String[]{"Male", "Female"})
                    .setFormLayout(formLayout).setSelected("Female").create();

            //create edittext + datepicker
            MyEdittext edtBirthdate = edtBuilder.clone().setInputType(InputType.TYPE_CLASS_DATETIME)
                    .setTitle("Birthdate").setDateformat("dd-MM-yyyy")
                    .setTitleColorResource(R.color.dark_grey)
                    .create();

            //create edittext with 3 lines
            MyEdittext edtAddress = edtBuilder.clone().setTitle("Address").setMinLines(3).create();

            //create multiple edittext in one row
            HashMap<String, MyEdittext> edtList = new LinkedHashMap<>();
            MyEdittext.Builder edtItemBuilder = new MyEdittext.Builder(ExampleJavaActivity.this);
            edtList.put("No", edtItemBuilder.clone().setTitle("No").setInputType(InputType.TYPE_CLASS_NUMBER).create());
            edtList.put("Zip", edtItemBuilder.clone().setTitle("Zip").setInputType(InputType.TYPE_CLASS_NUMBER).create());
            MyEdittextMultiple edtMultiple = new MyEdittextMultiple.Builder(ExampleJavaActivity.this, edtList)
                    .setFormLayout(formLayout).setMargin(50).create();

            //create spinner data & view
            ArrayList<SpinnerData> cityList = new ArrayList<>();
            cityList.add(new SpinnerData(1, "1", "Malang"));
            cityList.add(new SpinnerData(2, "2", "Surabaya", true));
            cityList.add(new SpinnerData(3, "3", "Jakarta"));
            MySpinner spinCity = new MySpinner.Builder(ExampleJavaActivity.this)
                    .setTitle("City").setItem(cityList).setDefaultSelectedValue("Jakarta").setFormLayout(formLayout).create();
//            spinCity.hideItemById(2);

            //create checkbox
            ArrayList<String> educationList = new ArrayList<>();
            educationList.add("Associate");
            educationList.add("Bachelor");
            educationList.add("Master");
            MyCheckbox myCheckboxView = new MyCheckbox.Builder(ExampleJavaActivity.this)
                    .setTitle("Education").setCheckBoxItem(educationList)
                    .setFormLayout(formLayout)
                    .setOnCheckedListener(selected -> toast("selected: "+selected)).create();

            //create textview
            MyTextView myTextView = new MyTextView.Builder(ExampleJavaActivity.this)
                    .setTitle("Note:").setTitleFont("fonts/Roboto-Bold.ttf")
                    .setContent("Please check again before submit")
                    .setFormLayout(formLayout).create();

            btnSubmit.setOnClickListener(view -> toast("Name: " + edtName.getValue()
                    + " Gender: " + rdbGender.getValue()
                    + " Address: " + edtAddress.getValue()
                    + " Birthdate: " + edtBirthdate.getValue()
                    + " No: " + edtMultiple.getValue("No")
                    + " Zip: " + edtMultiple.getValue("Zip")
                    + " City: " + spinCity.getSelectedValue() +" - "+spinCity.getSelectedId()+" - "+spinCity.getSelectedSecondaryId()
                    + " Education: "+myCheckboxView.getAllChecked()));

        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }

    void toast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }
}
