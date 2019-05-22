package com.paperplay.androidformbuilder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.paperplay.myformbuilder.MyCheckbox;
import com.paperplay.myformbuilder.MyEdittext;
import com.paperplay.myformbuilder.MyEdittextMultiple;
import com.paperplay.myformbuilder.MyRadioButton;
import com.paperplay.myformbuilder.MySpinner;
import com.paperplay.myformbuilder.MyTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Created by Ahmed Yusuf on 17/05/19.
 */
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout layout = (LinearLayout)findViewById(R.id.formLayout);
        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);

        /** Make template and set default value **/
        MyEdittext.Builder edtTemplate = new MyEdittext.Builder(MainActivity.this).setNullable(false);

        /** Attach View **/
        try {
            //create edittext with search
            MyEdittext edtId = new MyEdittext.Builder(MainActivity.this).setTitle("ID")
                    .setMode(MyEdittext.Mode.SEARCH).create();
            edtId.setOnClickSearchListener(keyword -> toast("Search: "+keyword));
            layout.addView(edtId.getView());

            //create general edittext
            MyEdittext edtName = new MyEdittext.Builder(MainActivity.this).setTitle("Name").create();
            layout.addView(edtName.getView());

            //create radio button
            MyRadioButton rdbGender = new MyRadioButton.Builder(MainActivity.this)
                    .setTitle("Gender").setOptionList(new String[]{"Male", "Female"}).setSelected("Female").create();
            layout.addView(rdbGender.getView());

            //create edittext + datepicker
            MyEdittext edtBirthdate = new MyEdittext.Builder(MainActivity.this).setInputType(InputType.TYPE_CLASS_DATETIME)
                    .setTitle("Birthdate").setDateformat("dd-MM-yyyy")
                    .setTitleColorResource(R.color.dark_grey)
                    .create();
            layout.addView(edtBirthdate.getView());

            //create edittext with 3 lines
            MyEdittext edtAddress = new MyEdittext.Builder(MainActivity.this)
                    .setTitle("Address").setMinLines(3).create();
            layout.addView(edtAddress.getView());

            //create multiple edittext in one row
            HashMap<String, MyEdittext> edtList = new LinkedHashMap<>();
            edtList.put("No", edtTemplate.clone().setTitle("No").setInputType(InputType.TYPE_CLASS_NUMBER).create());
            edtList.put("Zip", edtTemplate.clone().setTitle("Zip").setInputType(InputType.TYPE_CLASS_NUMBER).create());
            MyEdittextMultiple edtMultiple = new MyEdittextMultiple.Builder(MainActivity.this, edtList)
                    .setMargin(50).create();
            layout.addView(edtMultiple.getView());

            //create spinner view
            ArrayList<String> cityList = new ArrayList<>();
            cityList.add("Malang");
            cityList.add("Surabaya");
            MySpinner spinCity = new MySpinner.Builder(MainActivity.this)
                    .setTitle("City").setItem(cityList).setDefaultSelected("Surabaya").create();
            layout.addView(spinCity.getView());

            //create checkbox
            ArrayList<String> educationList = new ArrayList<>();
            educationList.add("Associate");
            educationList.add("Bachelor");
            educationList.add("Master");
            educationList.add("Doctoral");
            educationList.add("Professional");
            MyCheckbox myCheckboxView = new MyCheckbox.Builder(MainActivity.this)
                    .setTitle("Education").setCheckBoxItem(educationList)
                    .setOnCheckedListener(s -> toast("select: "+s)).create();
            layout.addView(myCheckboxView.getView());

            //create textview
            MyTextView myTextView = new MyTextView.Builder(MainActivity.this)
                    .setTitle("Note:").setTitleFont("fonts/Roboto-Bold.ttf")
                    .setContent("Please check again before submit").create();
            layout.addView(myTextView.getView());

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //check not nullable form
                    Toast.makeText(getBaseContext(), "Name: " + edtName.getValue()
                            + " Gender: " + rdbGender.getValue()
                            + " Address: " + edtAddress.getValue()
                            + " Birthdate: " + edtBirthdate.getValue()
                            + " No: " + edtMultiple.getValue("No")
                            + " Zip: " + edtMultiple.getValue("Zip")
                            + " City: " + spinCity.getValue()
                            + " Education: "+myCheckboxView.getAllChecked(), Toast.LENGTH_LONG).show();
                }
            });

        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }

    void toast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }
}
