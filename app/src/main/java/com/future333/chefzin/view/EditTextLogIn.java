package com.future333.chefzin.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

/**
 * Created by manuel on 9/4/16.
 */
public class EditTextLogIn extends EditText {

    public EditTextLogIn(Context context) {
        super(context);
    }

    public EditTextLogIn(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInicializate();

    }

    public EditTextLogIn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setInicializate(){
        setGravity(Gravity.CENTER);
        setTextSize(12);
    }


}
