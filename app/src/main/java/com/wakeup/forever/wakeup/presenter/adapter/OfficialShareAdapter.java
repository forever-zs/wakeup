package com.wakeup.forever.wakeup.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.view.activity.ShareDetailActivity;
import com.wakeup.forever.wakeup.view.viewholder.OfficialShareHolder;

import java.util.ArrayList;

/**
 * Created by forever on 2016/8/26.
 */
public class OfficialShareAdapter extends RecyclerView.Adapter<OfficialShareHolder>{

    private ArrayList<Share> shareList;
    private Context context;
    private OfficialShareHolder officialShareHolder;

    public OfficialShareAdapter(ArrayList<Share> shareList, Context context){
        this.shareList=shareList;
        this.context=context;
    }

    @Override
    public OfficialShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseShareItem= LayoutInflater.from(context).inflate(R.layout.layout_official_share_item,parent,false);
        officialShareHolder=new OfficialShareHolder(baseShareItem);
        return officialShareHolder;
    }

    @Override
    public void onBindViewHolder(OfficialShareHolder holder, int position) {
        Share share=shareList.get(position);
        holder.getTvViewCount().setText(share.getViewCount()+"");   //setText一定要为String类型，否则会爆ResourceNotFound错误
        holder.getTvFavouriteCount().setText(share.getLikeCount()+"");
        holder.getTvAuthor().setText(share.getAuthorName());
        holder.getTvTitle().setText(share.getTitle());
        Glide.with(context)
                .load(share.getImageLink())
                .placeholder(R.drawable.head)
                .error(R.drawable.head)
                .crossFade()
                .into(holder.getIvImageDesc());

        holder.getLayoutBaseShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, ShareDetailActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shareList.size();
    }

}
