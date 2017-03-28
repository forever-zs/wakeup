package com.wakeup.forever.wakeup.view.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.ExchangeRecordForUser;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by forever on 2016/12/1.
 */

public class ExchangeRecordHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_createTime)
    TextView tvCreateTime;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    @Bind(R.id.iv_imageDesc)
    ImageView ivImageDesc;
    @Bind(R.id.rl_baseShare)
    RelativeLayout rlBaseShare;
    private SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日");

    private Context context;

    public ExchangeRecordHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context=context;
    }

    public void setData(ExchangeRecordForUser exchangeRecordForUser){
        Date date=new Date(exchangeRecordForUser.getCreateTime());
        tvCreateTime.setText(myFmt.format(date)+"");
        tvPoint.setText(exchangeRecordForUser.getPoint()+"");
        tvShopName.setText(exchangeRecordForUser.getShopName());
        Glide.with(context)
                .load(exchangeRecordForUser.getImageUrl())
                .placeholder(R.drawable.head)
                .error(R.drawable.head)
                .crossFade()
                .into(ivImageDesc);
    }
}
