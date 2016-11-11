package com.future333.chefzin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future333.chefzin.R;

/**
 * Created by manuel on 4/11/16.
 */

public class CheckProductPrice extends LinearLayout {

    CheckBox    check;
    TextView    tvName;
    TextView    tvPrice;

    public CheckProductPrice(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_check_product_price, this, true);

        inicializate(v);
    }

    public CheckProductPrice(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_check_product_price, this, true);

        inicializate(v);
    }

    private void inicializate(View v){
        tvName      = (TextView)v.findViewById(R.id.tvName);
        tvPrice     = (TextView)v.findViewById(R.id.tvPrice);
        check       = (CheckBox) v.findViewById(R.id.checkbox);
    }

    public void setName(String text){
        tvName.setText(text);
    }

    public void setPrice(String text){
        tvPrice.setText(text);
    }

    public void setCheck(boolean isCheck){
        check.setChecked(isCheck);
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
