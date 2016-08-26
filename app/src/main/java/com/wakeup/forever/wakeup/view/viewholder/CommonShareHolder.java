package com.wakeup.forever.wakeup.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wakeup.forever.wakeup.R;

/**
 * Created by forever on 2016/8/26.
 */
public class CommonShareHolder extends RecyclerView.ViewHolder {

    ImageView ivImageDesc;
    TextView tvTitle;
    TextView tvViewCount;
    TextView tvFavouriteCount;
    TextView tvAuthor;
    RelativeLayout layoutBaseShare;

    public CommonShareHolder(View itemView) {
        super(itemView);
        ivImageDesc= (ImageView) itemView.findViewById(R.id.iv_imageDesc);
        tvTitle= (TextView) itemView.findViewById(R.id.tv_title);
        tvViewCount= (TextView) itemView.findViewById(R.id.tv_viewCount);
        tvFavouriteCount= (TextView) itemView.findViewById(R.id.tv_favouriteCount);
        tvAuthor= (TextView) itemView.findViewById(R.id.tv_author);
    }

    public ImageView getIvImageDesc() {
        return ivImageDesc;
    }

    public void setIvImageDesc(ImageView ivImageDesc) {
        this.ivImageDesc = ivImageDesc;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvViewCount() {
        return tvViewCount;
    }

    public void setTvViewCount(TextView tvViewCount) {
        this.tvViewCount = tvViewCount;
    }

    public TextView getTvFavouriteCount() {
        return tvFavouriteCount;
    }

    public void setTvFavouriteCount(TextView tvFavouriteCount) {
        this.tvFavouriteCount = tvFavouriteCount;
    }

    public TextView getTvAuthor() {
        return tvAuthor;
    }

    public void setTvAuthor(TextView tvAuthor) {
        this.tvAuthor = tvAuthor;
    }

    public RelativeLayout getLayoutBaseShare() {
        return layoutBaseShare;
    }

    public void setLayoutBaseShare(RelativeLayout layoutBaseShare) {
        this.layoutBaseShare = layoutBaseShare;
    }
}
