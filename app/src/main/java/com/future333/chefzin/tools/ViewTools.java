package com.future333.chefzin.tools;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by manuel on 9/4/16.
 */
public class ViewTools {

    /**
     * this method setFocusable a EditText and activate keyboard.
     */
    public static void focusableEditText(Activity activity, EditText editText){
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Este metodo toma una lista de editText asociada a un formulario y setFocusable al que se encuentre vacio.
     */
    public static void focusableForm(Activity activity, ArrayList<EditText> editTextArray){
        for(EditText editText: editTextArray){
            if(editText.getText().toString().equals("")){
                focusableEditText(activity, editText);
                return;
            }
        }
    }
}
