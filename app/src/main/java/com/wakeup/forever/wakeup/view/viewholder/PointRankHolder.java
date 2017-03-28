package com.wakeup.forever.wakeup.view.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.UserPoint;
import com.wakeup.forever.wakeup.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by forever on 2016/9/10.
 */
public class PointRankHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_rank)
    TextView tvRank;
    @Bind(R.id.civ_head)
    CircleImageView civHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.iv_sign)
    ImageView ivSign;
    @Bind(R.id.tv_signCount)
    TextView tvSignCount;
    @Bind(R.id.rl_baseShare)
    RelativeLayout rlBaseShare;
    @Bind(R.id.tv_signPoint)
    TextView tvSignPoint;

    public PointRankHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(UserPoint userPoint,int position,Context context) {
        tvRank.setText((position+1)+"");
        Glide.with(context)
                .load(userPoint.getHeadURL())
                .placeholder(R.drawable.head)
                .error(R.drawable.head)
                .crossFade()
                .into(civHead);
        tvName.setText(userPoint.getName());
        if(userPoint.getSignature()==null){
            tvSignature.setText("不辜负每一个清晨");
        }
        else{
            tvSignature.setText(userPoint.getSignature());
        }
        tvSignCount.setText(userPoint.getCount()+"");

        tvSignPoint.setText(userPoint.getAccumulatePoint()+"分");

    }
}
