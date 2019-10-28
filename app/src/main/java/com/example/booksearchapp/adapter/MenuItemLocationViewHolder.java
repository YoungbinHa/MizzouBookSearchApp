package com.example.booksearchapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.booksearchapp.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;


public class MenuItemLocationViewHolder extends ChildViewHolder {
    private SubsamplingScaleImageView mImageView;

    public MenuItemLocationViewHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.menuinfo_location_image);
    }

    public void setmImageView(){
//        int resId = getResources().getIdentifier(libData.getMap(), "drawable", getApplicationContext().getPackageName());
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
//        pathImage.setImageBitmap(bm);

        mImageView.setImage(ImageSource.resource(R.drawable.ellismap));
    }
}
