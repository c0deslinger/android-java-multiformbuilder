package com.paperplay.myformbuilder.function;

import android.content.Context;
import android.widget.Toast;

import com.paperplay.myformbuilder.MyEdittext;
import com.paperplay.myformbuilder.MyEdittextMultiple;

/**
 * Created by Ahmed Yusuf on 24/11/18.
 */
public class ViewChecker {
    static String errorMessagePrefix = "Please fill";
    static String errorMessagePostfix = "data.";

    public static void setErrorMessagePrefix(String errorMessage) {
        errorMessagePrefix = errorMessage;
    }

    public static void setErrorMessagePostfix(String errorMessage) {
        errorMessagePostfix = errorMessage;
    }

    public static boolean isFilled(MyEdittext view, Context context){
        if(!view.isFilled() && !view.isNullable()){
            view.getView().requestFocus();
            Toast.makeText(context, errorMessagePrefix +" "+view.getTitle() +" "+errorMessagePostfix,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isFilled(MyEdittextMultiple myEdittextMultiple, Context context){
        for (String key : myEdittextMultiple.getEdtList().keySet()){
            if(!myEdittextMultiple.getEdtList().get(key).isFilled()
                    && !myEdittextMultiple.getEdtList().get(key).isNullable()) {
                myEdittextMultiple.getEdtList().get(key).getView().requestFocus();
                Toast.makeText(context, errorMessagePrefix
                                +" "+myEdittextMultiple.getEdtList().get(key).getTitle()
                                +" "+errorMessagePostfix,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

}
