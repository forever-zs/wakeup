package com.wakeup.forever.wakeup.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.ShopItem;
import com.wakeup.forever.wakeup.model.bean.UserPoint;
import com.wakeup.forever.wakeup.view.activity.ShopActivity;
import com.wakeup.forever.wakeup.view.viewholder.PointRankHolder;
import com.wakeup.forever.wakeup.view.viewholder.ShopItemHolder;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by forever on 2016/11/21.
 */

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemHolder> {

    private LinkedList<ShopItem> shopItemList;
    private Context context;
    private ShopItemHolder shopItemHolder;

    public ShopItemAdapter(LinkedList<ShopItem> shopItemList, Context context) {
        this.shopItemList = shopItemList;
        this.context = context;
    }

    @Override
    public ShopItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_point_shop_item,parent,false);
        shopItemHolder=new ShopItemHolder(view);
        return shopItemHolder;
    }

    @Override
    public void onBindViewHolder(ShopItemHolder holder, final int position) {
        holder.setData(shopItemList.get(position),context);

        holder.getRlBaseShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, ShopActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(GlobalConstant.SHOP_ITMP,shopItemList.get(position));
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }



    @Override
    public int getItemCount() {
        return shopItemList.size();
    }
}
