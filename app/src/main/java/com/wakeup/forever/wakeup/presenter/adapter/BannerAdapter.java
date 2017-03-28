package com.wakeup.forever.wakeup.presenter.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.model.bean.Banner;

import java.util.ArrayList;

/**
 * Created by forever on 2016/11/21.
 */

public class BannerAdapter extends PagerAdapter {
    private ArrayList<Banner> bannerList;
    private ArrayList<ImageView> imageList;
    private Context context;

    public BannerAdapter(ArrayList<Banner> bannerList,Context context) {
        this.bannerList = bannerList;
        if(bannerList!=null&&bannerList.size()!=0){
            imageList=new ArrayList<>();
        }
        else{
            bannerList=new ArrayList<>();
            imageList=new ArrayList<>();
        }
        for(int i=0;i<bannerList.size();i++){
            ImageView imageView=new ImageView(context);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context)
                    .load(bannerList.get(i).getImageUrl())
                    .placeholder(R.drawable.guide1)
                    .error(R.drawable.guide1)
                    .crossFade()
                    .into(imageView);
            imageList.add(imageView);
        }
        this.context=context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageList.get(position%imageList.size()));
        return imageList.get(position%imageList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageList.get(position%imageList.size()));
    }
}
