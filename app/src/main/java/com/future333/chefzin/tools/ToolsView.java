package com.future333.chefzin.tools;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by manuel on 9/4/16.
 */
public class ToolsView {

    /** this method setFocusable a EditText and activate keyboard. */
    public static void focusableEditText(Activity activity, EditText editText){
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**Este metodo toma una lista de editText asociada a un formulario y setFocusable al que se encuentre vacio.*/
    public static void focusableForm(Activity activity, ArrayList<EditText> editTextArray){
        for(EditText editText: editTextArray){
            if(editText.getText().toString().equals("")){
                focusableEditText(activity, editText);
                return;
            }
        }
    }

    public static void msj(Context ctx, String msj){
        Toast.makeText(ctx,msj,Toast.LENGTH_SHORT).show();
    }

    /**Este metodo toma un view y lo esconde o lo muestra dependiendo del estado actual.*/
    public static void switchVisibisilite(View view){
        if(view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.GONE);
            return;
        }
        if(view.getVisibility() == View.GONE){
            view.setVisibility(View.VISIBLE);
            return;
        }
    }

    public static int dpToPx(Context ctx, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }

    /**Este devuelve el fragment manager dependiendo la version de android.*/
    public static FragmentManager getMapFragment(Fragment fragment) {
        FragmentManager fm = fragment.getFragmentManager();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
            fm = fragment.getChildFragmentManager();
        return fm;
    }
}
