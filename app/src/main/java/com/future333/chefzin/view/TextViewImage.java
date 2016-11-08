package com.future333.chefzin.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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

    String      _text;
    Drawable    _image;

    public TextViewImage(Context context, AttributeSet attrs) {
        super(context, attrs);

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewImage, 0, 0);

        _text   = typedArray.getString(R.styleable.TextViewImage_text);
        _image  = typedArray.getDrawable(R.styleable.TextViewImage_image);

//        _image   = typedArray.getInt(R.styleable.TextViewImage_image, R.mipmap.ic_faceboook_dark);

        typedArray.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_text_data, this, true);

        inicializate(v);
    }

    private void inicializate(View v){
        textView    = (TextView)v.findViewById(R.id.textVIew);
        imageView   = (ImageView) v.findViewById(R.id.imageView);

        textView.setText(_text);
        setImage(_image);
    }

    private void setImage(Drawable drawable){
        imageView.setImageDrawable(drawable);
    }

    public void setText(String text){
        textView.setText(text);
//        updateView();
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
