package com.wakeup.forever.wakeup.view.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.CommonShareComment;
import com.wakeup.forever.wakeup.model.bean.CommonShareLike;
import com.wakeup.forever.wakeup.widget.CircleImageView;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by forever on 2016/8/29.
 */
public class CommonShareHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.civ_authorImage)
    CircleImageView civAuthorImage;
    @Bind(R.id.tv_authorName)
    TextView tvAuthorName;
    @Bind(R.id.rl_commonShareHeader)
    RelativeLayout rlCommonShareHeader;
    @Bind(R.id.iv_shareViewed)
    ImageView ivShareViewed;
    @Bind(R.id.tv_shareViewedCount)
    TextView tvShareViewedCount;
    @Bind(R.id.iv_shareLiked)
    ImageView ivShareLiked;
    @Bind(R.id.fl_shareComment)
    RelativeLayout flShareComment;
    @Bind(R.id.tv_likeUser)
    TextView tvLikeUser;
    @Bind(R.id.rl_tv_likeUser)
    RelativeLayout rlTvLikeUser;
    @Bind(R.id.rl_baseShare)
    LinearLayout rlBaseShare;
    @Bind(R.id.tv_publishTime)
    TextView tvPublishTime;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.iv_imageDesc)
    ImageView ivImageDesc;
    @Bind(R.id.iv_comment)
    ImageView ivComment;
    @Bind(R.id.ll_comment)
    public LinearLayout llComment;

    public EditText etComment;
    public Button btnSure;
    public Button btnCancel;
    public View view;


    public CommonShareHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //R.layout.layout_common_share_item;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_comment, null, false);
        etComment = (EditText) view.findViewById(R.id.input_comment);
        btnSure = (Button) view.findViewById(R.id.btn_sure);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
    }

    public void setData(CommonShare commonShare, Context context) {

        Glide.with(context)
                .load(commonShare.getUser().getHeadURL())
                .error(R.drawable.head)
                .crossFade()
                .into(civAuthorImage);
        tvAuthorName.setText(commonShare.getUser().getName());
        tvPublishTime.setText(new Date(commonShare.getPublishTime()).toLocaleString());
        tvContent.setText(commonShare.getContent());
        if(commonShare.getImageDesc()!=null){
            Glide.with(context)
                    .load(commonShare.getImageDesc())
                    .placeholder(R.drawable.timg)
                    .error(R.drawable.timg)
                    .crossFade()
                    .into(ivImageDesc);
        }
        else{
            llContent.removeView(ivImageDesc);
        }

        tvShareViewedCount.setText(commonShare.getViewCount() + "");
        StringBuffer likeUser = new StringBuffer();
        for (CommonShareLike commonShareLike : commonShare.getLikedList()) {
            likeUser.append(commonShareLike.getUserName() + "，");
        }
        if (likeUser.length() != 0) {
            likeUser.deleteCharAt(likeUser.length() - 1);
        }
        likeUser.append("等人覺得很贊");
        tvLikeUser.setText(likeUser);
        if (commonShare.getFavourite()) {
            ivShareLiked.setImageResource(R.drawable.favourite_full);
        } else {
            ivShareLiked.setImageResource(R.drawable.favourite);
        }
        llComment.removeAllViews();
        for (CommonShareComment commonShareComment : commonShare.getCommentList()) {
            TextView textView = new TextView(context);
            String name = commonShareComment.getUserName();
            String comment = commonShareComment.getComment();
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.mainColor));
            SpannableString spannableString = new SpannableString(name + ":　" + comment);
            if (name != null) {
                spannableString.setSpan(colorSpan, 0, name.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            textView.setText(spannableString);
            llComment.addView(textView);
        }
    }

    public CircleImageView getCivAuthorImage() {
        return civAuthorImage;
    }

    public void setCivAuthorImage(CircleImageView civAuthorImage) {
        this.civAuthorImage = civAuthorImage;
    }

    public TextView getTvAuthorName() {
        return tvAuthorName;
    }

    public void setTvAuthorName(TextView tvAuthorName) {
        this.tvAuthorName = tvAuthorName;
    }

    public RelativeLayout getRlCommonShareHeader() {
        return rlCommonShareHeader;
    }

    public void setRlCommonShareHeader(RelativeLayout rlCommonShareHeader) {
        this.rlCommonShareHeader = rlCommonShareHeader;
    }

    public ImageView getIvShareViewed() {
        return ivShareViewed;
    }

    public void setIvShareViewed(ImageView ivShareViewed) {
        this.ivShareViewed = ivShareViewed;
    }

    public TextView getTvShareViewedCount() {
        return tvShareViewedCount;
    }

    public void setTvShareViewedCount(TextView tvShareViewedCount) {
        this.tvShareViewedCount = tvShareViewedCount;
    }

    public ImageView getIvShareLiked() {
        return ivShareLiked;
    }

    public void setIvShareLiked(ImageView ivShareLiked) {
        this.ivShareLiked = ivShareLiked;
    }

    public RelativeLayout getFlShareComment() {
        return flShareComment;
    }

    public void setFlShareComment(RelativeLayout flShareComment) {
        this.flShareComment = flShareComment;
    }

    public TextView getTvLikeUser() {
        return tvLikeUser;
    }

    public void setTvLikeUser(TextView tvLikeUser) {
        this.tvLikeUser = tvLikeUser;
    }

    public RelativeLayout getRlTvLikeUser() {
        return rlTvLikeUser;
    }

    public void setRlTvLikeUser(RelativeLayout rlTvLikeUser) {
        this.rlTvLikeUser = rlTvLikeUser;
    }

    public LinearLayout getRlBaseShare() {
        return rlBaseShare;
    }

    public void setRlBaseShare(LinearLayout rlBaseShare) {
        this.rlBaseShare = rlBaseShare;
    }

    public TextView getTvPublishTime() {
        return tvPublishTime;
    }

    public void setTvPublishTime(TextView tvPublishTime) {
        this.tvPublishTime = tvPublishTime;
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public void setTvContent(TextView tvContent) {
        this.tvContent = tvContent;
    }

    public ImageView getIvImageDesc() {
        return ivImageDesc;
    }

    public void setIvImageDesc(ImageView ivImageDesc) {
        this.ivImageDesc = ivImageDesc;
    }

    public ImageView getIvComment() {
        return ivComment;
    }

    public void setIvComment(ImageView ivComment) {
        this.ivComment = ivComment;
    }
}
