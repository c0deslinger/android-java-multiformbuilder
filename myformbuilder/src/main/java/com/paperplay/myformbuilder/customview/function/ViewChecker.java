package com.paperplay.myformbuilder.customview.function;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.paperplay.myformbuilder.customview.MyEdittext;
import com.paperplay.myformbuilder.customview.MyEdittextMultiple;

/**
 * Created by Ahmed Yusuf on 24/11/18.
 */
public class ViewChecker {
    static String errorNotFiled = "Please fill ";

    public static boolean checkEdittextFilled(MyEdittext view, Context context){
        if(!view.isFilled()){
            view.getView().requestFocus();
            Toast.makeText(context, errorNotFiled+view.getTitle(),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean checkEdittextFilled(MyEdittextMultiple myEdittextMultiple, Context context){
        for (String key : myEdittextMultiple.getEdtList().keySet()){
            if(!myEdittextMultiple.getEdtList().get(key).isFilled()){
                myEdittextMultiple.getEdtList().get(key).getView().requestFocus();
                Toast.makeText(context, errorNotFiled+myEdittextMultiple.getEdtList().get(key).getTitle(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

}
