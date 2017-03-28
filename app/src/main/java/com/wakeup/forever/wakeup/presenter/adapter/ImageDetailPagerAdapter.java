package com.wakeup.forever.wakeup.presenter.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.wingsofts.dragphotoview.DragPhotoView;

import java.util.LinkedList;

/**
 * Created by forever on 2016/12/28.
 */

public class ImageDetailPagerAdapter extends PagerAdapter {
    private LinkedList<DragPhotoView> mPhotoViews;


    public ImageDetailPagerAdapter(LinkedList<DragPhotoView> photoViews){
        this.mPhotoViews=photoViews;
    }

    @Override
    public int getCount() {
        return mPhotoViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mPhotoViews.get(position));
        return mPhotoViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mPhotoViews.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
