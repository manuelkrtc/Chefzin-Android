package com.future333.chefzin.tools;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.squareup.picasso.Transformation;

public class CropCircleTransformation implements Transformation {
	  @Override
	  public Bitmap transform(Bitmap source) {
		  	Bitmap result = Bitmap.createBitmap(source.getWidth(),source.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(result);
			
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
			
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			canvas.drawCircle(source.getWidth() / 2, source.getHeight() / 2, source.getWidth() / 2, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(source, rect, rect, paint);
			if (result != source) {
			  source.recycle();
			}
	    
			return result;
			
	  }

	  @Override
	  public String key() {
		  return "circle()"; 
	  }
	  
}