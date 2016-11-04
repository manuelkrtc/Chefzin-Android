package com.future333.chefzin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future333.chefzin.R;

/**
 * Created by manuel on 4/11/16.
 */

public class TextViewImage extends LinearLayout {

    TextView    textView;
    ImageView   imageView;
    TypedArray  typedArray;

    public TextViewImage(Context context, AttributeSet attrs) {
        super(context, attrs);

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewImage, 0, 0);

        String text = typedArray.getString(R.styleable.TextViewImage_text);
        typedArray.recycle();

        setOrientation(LinearLayout.HORIZONTAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_text_data, this, true);

        textView = (TextView)v.findViewById(R.id.textVIew);
        textView.setText(text);

    }

    private void setView(){

    }

    public void setText(String text){
        textView.setText(text);
    }

    private void updateView(){
        invalidate();
        requestLayout();
    }



}
