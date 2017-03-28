package com.wakeup.forever.wakeup.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.UserPoint;
import com.wakeup.forever.wakeup.view.viewholder.PointRankHolder;

import java.util.ArrayList;

/**
 * Created by forever on 2016/9/10.
 */
public class PointRankAdapter extends RecyclerView.Adapter<PointRankHolder> {

    private ArrayList<UserPoint> shareList;
    private Context context;
    private PointRankHolder pointRankHolder;

    public PointRankAdapter(ArrayList<UserPoint> shareList, Context context) {
        this.shareList = shareList;
        this.context = context;
    }

    @Override
    public PointRankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseShareItem= LayoutInflater.from(context).inflate(R.layout.layout_point_rank_item,parent,false);
        return new PointRankHolder(baseShareItem,context);
    }

    @Override
    public void onBindViewHolder(PointRankHolder holder, int position) {
        holder.setData(shareList.get(position),position,context);
    }

    @Override
    public int getItemCount() {
        return shareList.size();
    }
}
