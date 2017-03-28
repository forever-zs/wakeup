package com.wakeup.forever.wakeup.presenter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by forever on 2016/11/26.
 */

public class UserGuideAdapter extends PagerAdapter {
    private ArrayList<ImageView> imageList;
    private Context context;


    public UserGuideAdapter(ArrayList<Bitmap> bitmapList, Context context) {
        imageList=new ArrayList<ImageView>();
        this.context = context;
        for(int i=0;i<bitmapList.size();i++){
            ImageView imageView=new ImageView(context);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight=11.0f;
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(bitmapList.get(i));
            imageList.add(imageView);
        }
    }

    @Override

    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageList.get(position));
        return imageList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageList.get(position));
    }
}
