package com.paperplay.androidformbuilder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.widget.Toast
import com.paperplay.myformbuilder.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Ahmed Yusuf on 24/05/19.
 */
class ExampleKotlinActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** Make template and set default value **/
        val edtBuilder = MyEdittext.Builder(this@ExampleKotlinActivity).setFormLayout(formLayout)

        /** Attach View **/
        //create edittext with search
        var edtId = edtBuilder.clone().setTitle("ID").setMode(MyEdittext.Mode.SEARCH).create()
        edtId.setOnClickSearchListener { keyword ->  toast("Search: $keyword") }

        //create general edittext
        var edtName = edtBuilder.clone().setTitle("Name").create()

        //create radio button
        var rdbGender = MyRadioButton.Builder(this@ExampleKotlinActivity).setFormLayout(formLayout)
                .setTitle("Gender").setOptionList(arrayOf("Male", "Female")).setSelected("Female").create()

        //create edittext + datepicker
        var edtBirthdate = edtBuilder.clone().setInputType(InputType.TYPE_CLASS_DATETIME)
                .setTitle("Birthdate").setDateformat("dd-MM-yyyy")
                .setTitleColorResource(R.color.dark_grey)
                .create()

        //create edittext with 3 lines
        var edtAddress = edtBuilder.clone().setTitle("Address").setMinLines(3).create()

        //create multiple edittext in one row
        var edtList = LinkedHashMap<String, MyEdittext>()
        var edtItemBuilder = MyEdittext.Builder(this@ExampleKotlinActivity)
        edtList.put("No", edtItemBuilder.clone().setTitle("No").setInputType(InputType.TYPE_CLASS_NUMBER).create())
        edtList.put("Zip", edtItemBuilder.clone().setTitle("No").setInputType(InputType.TYPE_CLASS_NUMBER).create())
        var edtMultiple = MyEdittextMultiple.Builder(this@ExampleKotlinActivity, edtList)
                .setFormLayout(formLayout).setMargin(50).create()

        //create spinner view
        val cityList = ArrayList<String>()
        cityList.add("Malang")
        cityList.add("Surabaya")
        var spinCity = MySpinner.Builder(this@ExampleKotlinActivity)
                .setTitle("City").setItem(cityList).setDefaultSelected("Surabaya").setFormLayout(formLayout).create()

        //create checkbox
        val educationList = ArrayList<String>()
        educationList.add("Associate")
        educationList.add("Bachelor")
        educationList.add("Master")
        val myCheckboxView = MyCheckbox.Builder(this@ExampleKotlinActivity)
                .setTitle("Education").setCheckBoxItem(educationList)
                .setFormLayout(formLayout)
                .setOnCheckedListener{ selected -> toast("selected: $selected") }.create()

        //create textview
        var myTextView = MyTextView.Builder(this@ExampleKotlinActivity)
                .setTitle("Note:").setTitleFont("fonts/Roboto-Bold.ttf")
                .setContent("Please check again before submit")
                .setFormLayout(formLayout).create()

        btnSubmit.setOnClickListener{ view -> toast("Name: " + edtName.getValue()
                + " Gender: " + rdbGender.getValue()
                + " Address: " + edtAddress.getValue()
                + " Birthdate: " + edtBirthdate.getValue()
                + " No: " + edtMultiple.getValue("No")
                + " Zip: " + edtMultiple.getValue("Zip")
                + " City: " + spinCity.getValue()
                + " Education: "+myCheckboxView.getAllChecked())}

    }

    fun toast(message : String){
        Toast.makeText(this@ExampleKotlinActivity.baseContext, message, Toast.LENGTH_SHORT).show()
    }
}