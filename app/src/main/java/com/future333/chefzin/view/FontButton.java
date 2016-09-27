package com.future333.chefzin.view;

import clickdelivery.pidefarma.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class FontButton extends Button {

	public FontButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}
	
	public FontButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		
	}
	
	public FontButton(Context context) {
		super(context);
		init(null);
	}
	
	private void init(AttributeSet attrs) {
		if (attrs!=null) {
			 TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontTextView);
			 String fontName = a.getString(R.styleable.FontTextView_fontName);
			 if (fontName!=null && !this.isInEditMode()) {
				 Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
				 setTypeface(myTypeface);
			 }
			 a.recycle();
		}
	}
	
}
