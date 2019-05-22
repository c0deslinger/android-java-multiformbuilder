# Multi Form Builder

Android Multi Form Builder is a simple library to help you make simple form faster without create xml codes.

![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-22%20at%2015.43.56.png?raw=true)
![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-22%20at%2015.44.12.png?raw=true)
![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-22%20at%2016.16.21.png?raw=true)

# Feature
- Easily build forms with minimal effort
- Clonable builder as template. So you can make more element with a single builder object.
- More customable option such Text Font, Text Color, etc

# Installation
In order to use the library, please follow this step bellow:
- Add the following to your project level ```build.gradle```:
```gradle
allprojects {
  repositories {
    maven { url "https://jitpack.io" }
  }
}
```
- Add this to your app ```build.gradle```:
```gradle
dependencies {
  implementation 'com.github.c0deslinger:android-java-multiformbuilder:1.0.2'
}
```

# Example Usage
- Init your form layout:
```java
LinearLayout layout = (LinearLayout)findViewById(R.id.formLayout);
```

- Create EditText + Search Button
```java
MyEdittext edtId = new MyEdittext.Builder(MainActivity.this).setTitle("ID")
                    .setMode(MyEdittext.Mode.SEARCH).create();
edtId.setOnClickSearchListener(keyword -> toast("Search: "+keyword));
layout.addView(edtId.getView());
```

- Create EditText
```java
MyEdittext edtName = new MyEdittext.Builder(MainActivity.this).setTitle("Name").create();
layout.addView(edtName.getView());
```

- Create EditText with 3 lines
```java
MyEdittext edtAddress = new MyEdittext.Builder(MainActivity.this).setTitle("Address").setMinLines(3).create();
layout.addView(edtAddress.getView());
```

- Create EditText + datepicker
```java
MyEdittext edtBirthdate = new MyEdittext.Builder(MainActivity.this).setInputType(InputType.TYPE_CLASS_DATETIME)
                    .setTitle("Birthdate").setDateformat("dd-MM-yyyy")
                    .setTitleColorResource(R.color.dark_grey)
                    .create();
layout.addView(edtBirthdate.getView());
```

- Create RadioButton
```java
MyRadioButton rdbGender = new MyRadioButton.Builder(MainActivity.this).setTitle("Gender")
                    .setOptionList(new String[]{"Male", "Female"}).setSelected("Female").create();
layout.addView(rdbGender.getView());
```

- Create multiple EditText in a single row
```java
HashMap<String, MyEdittext> edtList = new LinkedHashMap<>();
            edtList.put("No", edtTemplate.clone().setTitle("No").setInputType(InputType.TYPE_CLASS_NUMBER).create());
            edtList.put("Zip", edtTemplate.clone().setTitle("Zip").setInputType(InputType.TYPE_CLASS_NUMBER).create());
MyEdittextMultiple edtMultiple = new MyEdittextMultiple.Builder(MainActivity.this, edtList)
        .setMargin(50).create();
layout.addView(edtMultiple.getView());
```

- Create spinner view
```java
ArrayList<String> cityList = new ArrayList<>();
            cityList.add("Malang");
            cityList.add("Surabaya");
MySpinner spinCity = new MySpinner.Builder(MainActivity.this).setTitle("City").setItem(cityList)
           .setDefaultSelected("Surabaya").create();
layout.addView(spinCity.getView());
```

- Create multiple checkbox
```java
ArrayList<String> educationList = new ArrayList<>();
            educationList.add("Associate");
            educationList.add("Bachelor");
            educationList.add("Master");
MyCheckbox myCheckboxView = new MyCheckbox.Builder(MainActivity.this).setTitle("Education")
            .setCheckBoxItem(educationList).create();
layout.addView(myCheckboxView.getView());
```

- Create TextView
```java
MyTextView myTextView = new MyTextView.Builder(MainActivity.this).setTitle("Note:")
                    .setTitleFont("fonts/Roboto-Bold.ttf")
                    .setContent("Please check again before submit").create();
layout.addView(myTextView.getView());
```

- Get values
```java
Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
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
```

# Contributing
Send Pull Requests and Let's rock n roll

# License
This Library is released under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
