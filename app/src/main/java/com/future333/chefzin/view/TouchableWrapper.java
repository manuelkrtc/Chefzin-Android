package com.future333.chefzin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.future333.chefzin.R;

/**
 * Created by manuel on 11/23/16.
 */

public class TouchableWrapper extends FrameLayout {

    boolean mMapIsTouched;

    public TouchableWrapper(Context context) {
        super(context);
    }

    public TouchableWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchableWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMapIsTouched = true;
                break;
            case MotionEvent.ACTION_UP:
                mMapIsTouched = false;
                break;
        }

        return super.dispatchTouchEvent(ev);

    }

    public boolean ismMapIsTouched() {
        return mMapIsTouched;
    }
}



