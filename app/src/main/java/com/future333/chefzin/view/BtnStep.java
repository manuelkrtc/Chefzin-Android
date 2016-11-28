package com.future333.chefzin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future333.chefzin.R;


/**
 * Created by manuel on 11/25/16.
 */

public class BtnStep extends LinearLayout{

    TypedArray typedArray;

    TextView    tvNumber;
    TextView    tvNameStep;
    ViewGroup   zoneStroke;

    public BtnStep(Context context) {
        super(context);
    }

    public BtnStep(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_step, this, true);

        tvNumber    = (TextView)v.findViewById(R.id.tvNumber);
        tvNameStep  = (TextView)v.findViewById(R.id.tvNameStep);
        zoneStroke  = (ViewGroup)v.findViewById(R.id.zoneStroke);

        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs){
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.BtnStep, 0, 0);

        tvNameStep.setText(typedArray.getString(R.styleable.BtnStep_text));
        tvNumber.setText(String.valueOf(typedArray.getInt(R.styleable.BtnStep_number, 0)));
        select(typedArray.getBoolean(R.styleable.BtnStep_select, true));
    }

    //----------------------------------------------------------------------------------------------
    public void select(boolean isSelect){
        if(isSelect){
            tvNameStep.setVisibility(VISIBLE);
            tvNumber.setTextColor(getResources().getColor(R.color.green));
            zoneStroke.setBackgroundResource(R.drawable.shape_rectangle_stroke_corner_green);

        }else {
            tvNameStep.setVisibility(INVISIBLE);
            tvNumber.setTextColor(getResources().getColor(R.color.white));
            zoneStroke.setBackgroundResource(R.drawable.shape_rectangle_corner_green);
        }
    }

    /*Notice that setShowText calls invalidate() and requestLayout(). These calls are crucial
    to ensure that the view behaves reliably. You have to invalidate the view after any change to its
    properties that might change its appearance, so that the system knows that it needs to be redrawn.
    Likewise, you need to request a new layout if a property changes that might affect the size or
    shape of the view. Forgetting these method calls can cause hard-to-find bugs.*/
    private void updateView(){
        invalidate();
        requestLayout();
    }

}
