package com.wakeup.forever.wakeup.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.view.viewholder.CommonShareHolder;

import java.util.ArrayList;

/**
 * Created by forever on 2016/8/26.
 */
public class CommonShareAdapter extends RecyclerView.Adapter<CommonShareHolder> {

    private ArrayList<Share> shareList;
    private Context context;
    private CommonShareHolder commonShareHolder;

    public CommonShareAdapter(ArrayList<Share> shareList, Context context){
        this.shareList=shareList;
        this.context=context;
    }

    @Override
    public CommonShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseShareItem= LayoutInflater.from(context).inflate(R.layout.layout_base_share,parent,false);
        commonShareHolder=new CommonShareHolder(baseShareItem);
        return commonShareHolder;
    }

    @Override
    public void onBindViewHolder(CommonShareHolder holder, int position) {
        Share share=shareList.get(position);
        holder.getTvViewCount().setText(share.getViewCount()+"");   //setText一定要为String类型，否则会爆ResourceNotFound错误
        holder.getTvFavouriteCount().setText(share.getLikeCount()+"");
        holder.getTvAuthor().setText(share.getAuthorName());
        holder.getTvTitle().setText(share.getTitle());
        Glide.with(context)
                .load(share.getImageLink())
                .error(R.drawable.head)
                .crossFade()
                .into(holder.getIvImageDesc());
    }

    @Override
    public int getItemCount() {
        return shareList.size();
    }
}
