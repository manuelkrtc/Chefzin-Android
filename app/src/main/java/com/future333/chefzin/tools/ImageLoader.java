package com.future333.chefzin.tools;


import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.future333.chefzin.R;
import com.squareup.picasso.Picasso;


public class ImageLoader {
	
	private Picasso picasso = null;
	
	public ImageLoader(Context ctx){
		
		picasso = new Picasso.Builder(ctx).listener(new Picasso.Listener() {
	        @Override
	        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
	          exception.printStackTrace();
	        }
	    })
	    .build();
		
	}
	
	public void loadAndDisplayImage(String url, ImageView viewHolder){
		if(picasso != null && !url.equals("")){
			picasso.load(url).placeholder(R.mipmap.img_chef).into(viewHolder);
		}
	}
	
	public void loadAndDisplayCircledImage(String url, ImageView viewHolder){
		
		if(picasso != null && !url.equals("")){
			picasso.load(url).transform(new CropCircleTransformation()).into(viewHolder);
		}
		
	}

}
