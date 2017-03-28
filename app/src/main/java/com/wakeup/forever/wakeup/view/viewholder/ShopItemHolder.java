package com.wakeup.forever.wakeup.view.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.ShopItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by forever on 2016/11/21.
 */

public class ShopItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_shopDImage)
    ImageView ivShopDImage;
    @Bind(R.id.tv_shopName)
    TextView tvShopName;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    @Bind(R.id.rl_baseShare)
    RelativeLayout rlBaseShare;

    public ShopItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(ShopItem shopItem, Context context){
        if(shopItem!=null){
            Glide.with(context)
                    .load(shopItem.getImageUrl())
                    .placeholder(R.drawable.guide1)
                    .error(R.drawable.guide1)
                    .crossFade()
                    .into(ivShopDImage);
            tvShopName.setText(shopItem.getShopName());
            tvPoint.setText(shopItem.getPoint()+"积分");
        }
    }


    public RelativeLayout getRlBaseShare() {
        return rlBaseShare;
    }

    public void setRlBaseShare(RelativeLayout rlBaseShare) {
        this.rlBaseShare = rlBaseShare;
    }
}
