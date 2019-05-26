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
  implementation 'com.github.c0deslinger:android-java-multiformbuilder:1.0.3'
}
```

# Example Usage 
- Init your form layout:

Java:
```java
LinearLayout formLayout = (LinearLayout)findViewById(R.id.formLayout);
```
Kotlin:
```kotlin
//pfft
```

-  Make template view builder and set default value

Java:
```java
MyEdittext.Builder edtBuilder = new MyEdittext.Builder(MainActivity.this).setFormLayout(formLayout);
```
Kotlin:
```kotlin
val edtBuilder = MyEdittext.Builder(this@ExampleKotlinActivity).setFormLayout(formLayout)
```

- Create EditText + Search Button

![alt text](https://raw.githubusercontent.com/c0deslinger/android-java-multiformbuilder/master/screenshots/Screen%20Shot%202019-05-24%20at%2023.14.14.png)

Java:
```java
MyEdittext edtId = edtBuilder.clone().setTitle("ID").setMode(MyEdittext.Mode.SEARCH).create();
edtId.setOnClickSearchListener(keyword -> toast("Search: "+keyword));
```
Kotlin:
 ```kotlin
 var edtId = edtBuilder.clone().setTitle("ID").setMode(MyEdittext.Mode.SEARCH).create()
edtId.setOnClickSearchListener { keyword ->  toast("Search: $keyword") }
```

- Create EditText

![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-24%20at%2023.14.20.png?raw=true)

Java:
```java
MyEdittext edtName = edtBuilder.clone().setTitle("Name").create();
```
Kotlin:
```kotlin
var edtName = edtBuilder.clone().setTitle("Name").create()
```

- Create EditText with 3 lines

![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-25%20at%2011.33.28.png?raw=true)

Java:
```java
MyEdittext edtAddress = edtBuilder.clone().setTitle("Address").setMinLines(3).create();
```
Kotlin:
```kotlin
var edtAddress = edtBuilder.clone().setTitle("Address").setMinLines(3).create()
```

- Create EditText + datepicker

![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-25%20at%2011.33.23.png?raw=true)

Java:
```java
MyEdittext edtBirthdate = edtBuilder.clone().setInputType(InputType.TYPE_CLASS_DATETIME)
                .setTitle("Birthdate").setDateformat("dd-MM-yyyy")
                .setTitleColorResource(R.color.dark_grey)
                .create();
```
Kotlin:
```kotlin
var edtBirthdate = edtBuilder.clone().setInputType(InputType.TYPE_CLASS_DATETIME)
                .setTitle("Birthdate").setDateformat("dd-MM-yyyy")
                .setTitleColorResource(R.color.dark_grey)
                .create()
```

- Create RadioButton

![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-24%20at%2023.14.30.png?raw=true)

Java:
```java
MyRadioButton rdbGender = new MyRadioButton.Builder(MainActivity.this)
                .setTitle("Gender").setOptionList(new String[]{"Male", "Female"})
                .setFormLayout(formLayout).setSelected("Female").create();
```
Kotlin:
```kotlin
var rdbGender = MyRadioButton.Builder(this@ExampleKotlinActivity).setFormLayout(formLayout)
                .setTitle("Gender").setOptionList(arrayOf("Male", "Female")).setSelected("Female").create()
```
Note: If you not define template builder on the beginning, you have to setFormLayout to attach view.

- Create multiple EditText in a single row

![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-25%20at%2011.33.34.png?raw=true)

Java:
```java
HashMap<String, MyEdittext> edtList = new LinkedHashMap<>();
MyEdittext.Builder edtItemBuilder = new MyEdittext.Builder(MainActivity.this);
edtList.put("No", edtItemBuilder.clone().setTitle("No").setInputType(InputType.TYPE_CLASS_NUMBER).create());
edtList.put("Zip", edtItemBuilder.clone().setTitle("Zip").setInputType(InputType.TYPE_CLASS_NUMBER).create());
MyEdittextMultiple edtMultiple = new MyEdittextMultiple.Builder(MainActivity.this, edtList)
            .setFormLayout(formLayout).setMargin(50).create();
```
Kotlin:
```kotlin
var edtList = LinkedHashMap<String, MyEdittext>()
var edtItemBuilder = MyEdittext.Builder(this@ExampleKotlinActivity)
edtList.put("No", edtItemBuilder.clone().setTitle("No").setInputType(InputType.TYPE_CLASS_NUMBER).create())
edtList.put("Zip", edtItemBuilder.clone().setTitle("No").setInputType(InputType.TYPE_CLASS_NUMBER).create())
var edtMultiple = MyEdittextMultiple.Builder(this@ExampleKotlinActivity, edtList)
        .setFormLayout(formLayout).setMargin(50).create()
```

- Create spinner view


![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-25%20at%2011.33.48.png?raw=true)

Java:
```java
ArrayList<String> cityList = new ArrayList<>();
            cityList.add("Malang");
            cityList.add("Surabaya");
MySpinner spinCity = new MySpinner.Builder(MainActivity.this)
        .setTitle("City").setItem(cityList).setDefaultSelected("Surabaya").setFormLayout(formLayout).create();
```
Kotlin:
```kotlin
val cityList = ArrayList<String>()
          cityList.add("Malang")
          cityList.add("Surabaya")
var spinCity = MySpinner.Builder(this@ExampleKotlinActivity)
        .setTitle("City").setItem(cityList).setDefaultSelected("Surabaya").setFormLayout(formLayout).create()
 ```

- Create multiple checkbox


![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-25%20at%2011.34.02.png?raw=true)

Java:
```java
ArrayList<String> educationList = new ArrayList<>();
educationList.add("Associate");
educationList.add("Bachelor");
educationList.add("Master");
MyCheckbox myCheckboxView = new MyCheckbox.Builder(MainActivity.this)
        .setTitle("Education").setCheckBoxItem(educationList)
        .setFormLayout(formLayout).setOnCheckedListener(selected -> toast("selected: "+selected)).create();
```
Kotlin
```kotlin
val educationList = ArrayList<String>()
educationList.add("Associate")
educationList.add("Bachelor")
educationList.add("Master")
val myCheckboxView = MyCheckbox.Builder(this@ExampleKotlinActivity)
        .setTitle("Education").setCheckBoxItem(educationList)
        .setFormLayout(formLayout).setOnCheckedListener{ selected -> toast("selected: $selected") }.create()
```

- Create TextView


![alt text](https://github.com/c0deslinger/android-java-multiformbuilder/blob/master/screenshots/Screen%20Shot%202019-05-25%20at%2011.34.09.png?raw=true)

Java:
```java
MyTextView myTextView = new MyTextView.Builder(MainActivity.this)
                    .setTitle("Note:").setTitleFont("fonts/Roboto-Bold.ttf")
                    .setContent("Please check again before submit").setFormLayout(formLayout).create();
```
Kotlin:
```kotlin
var myTextView = MyTextView.Builder(this@ExampleKotlinActivity)
                .setTitle("Note:").setTitleFont("fonts/Roboto-Bold.ttf")
                .setContent("Please check again before submit").setFormLayout(formLayout).create()
```

- Get values
Java:
```java
Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
btnSubmit.setOnClickListener(view -> toast("Name: " + edtName.getValue()
                    + " Gender: " + rdbGender.getValue()
                    + " Address: " + edtAddress.getValue()
                    + " Birthdate: " + edtBirthdate.getValue()
                    + " No: " + edtMultiple.getValue("No")
                    + " Zip: " + edtMultiple.getValue("Zip")
                    + " City: " + spinCity.getValue()
                    + " Education: "+myCheckboxView.getAllChecked()));
```
Kotlin:
```kotlin
btnSubmit.setOnClickListener{ view -> toast("Name: " + edtName.getValue()
                + " Gender: " + rdbGender.getValue()
                + " Address: " + edtAddress.getValue()
                + " Birthdate: " + edtBirthdate.getValue()
                + " No: " + edtMultiple.getValue("No")
                + " Zip: " + edtMultiple.getValue("Zip")
                + " City: " + spinCity.getValue()
                + " Education: "+myCheckboxView.getAllChecked())}
```

# Contributing
Send Pull Requests and Let's rock n roll

# License
This Library is released under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
