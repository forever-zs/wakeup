package com.wakeup.forever.wakeup.presenter.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.DataManager.ShareCacheManager;
import com.wakeup.forever.wakeup.model.DataManager.UserCacheManager;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.CommonShareComment;
import com.wakeup.forever.wakeup.model.bean.CommonShareLike;
import com.wakeup.forever.wakeup.view.activity.ImageDetailActivity;
import com.wakeup.forever.wakeup.view.fragment.CommonShareFragment;
import com.wakeup.forever.wakeup.view.viewholder.CommonShareHolder;
import com.wingsofts.dragphotoview.DragPhotoView;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by forever on 2016/8/29.
 */
public class CommonShareAdapter extends RecyclerView.Adapter<CommonShareHolder> {

    private ArrayList<CommonShare> commonShareList;
    private CommonShareFragment context;
    private CommonShareHolder commonShareHolder;
    //final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context.getContext());
    private BottomSheetDialog bottomSheetDialog;


    public EditText etComment;
    public Button btnSure;
    public Button btnCancel;
    public View view;

    public CommonShareAdapter(CommonShareFragment context,ArrayList<CommonShare> commonShareList){
        this.context=context;
        this.commonShareList=commonShareList;
        bottomSheetDialog=new BottomSheetDialog(context.getContext());
    }
    @Override
    public CommonShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseCommonShareItem= LayoutInflater.from(context.getContext()).inflate(R.layout.layout_common_share_item,null,false);
        commonShareHolder=new CommonShareHolder(baseCommonShareItem,context.getContext());


        return commonShareHolder;
    }

    @Override
    public void onBindViewHolder(final CommonShareHolder holder, final int position) {
        final CommonShare commonShare=commonShareList.get(position);
        holder.setData(commonShare,context.getContext());
        holder.getIvShareLiked().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(commonShareList.get(position).getFavourite()){
                    return ;
                }
                context.getPresenter().favourite(commonShareList.get(position).getId());
                holder.getIvShareLiked().setImageResource(R.drawable.favourite_full);   //变换图标

                commonShareList.get(position).setFavourite(true);    //设置数据集isFavourite为true

                String name=UserCacheManager.getInstance(context.getContext()).getUser().getName(); //获取缓存中的用户姓名

                CommonShareLike commonShareLike=new CommonShareLike();

                commonShareLike.setUserName(name);
                commonShareList.get(position).getLikedList().add(commonShareLike);   //加入点赞人

                ShareCacheManager.getInstance(context.getContext()).saveCommonShareList(commonShareList);//更新缓存数据

                notifyDataSetChanged();    //通知数据集变化
            }
        });

        holder.getIvComment().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = LayoutInflater.from(context.getContext()).inflate(R.layout.dialog_comment, null, false);
                etComment = (EditText) view.findViewById(R.id.input_comment);
                btnSure = (Button) view.findViewById(R.id.btn_sure);
                btnCancel = (Button) view.findViewById(R.id.btn_cancel);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                btnSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String comment=etComment.getText().toString();
                        context.getPresenter().sendComment(comment,commonShare.getId());
                        CommonShareComment commonShareComment=new CommonShareComment();
                        commonShareComment.setComment(comment);
                        String name=UserCacheManager.getInstance(context.getContext()).getUser().getName(); //获取缓存中的用户姓名
                        commonShareComment.setUserName(name);
                        commonShareList.get(position).getCommentList().add(commonShareComment);
                        notifyDataSetChanged();
                        ShareCacheManager.getInstance(context.getContext()).saveCommonShareList(commonShareList);//更新缓存数据
                        bottomSheetDialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

            }
        });

        holder.getIvImageDesc().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context.getContext(), ImageDetailActivity.class);
                ArrayList<String> imageList=new ArrayList<String>();
                imageList.add(commonShare.getImageDesc());
                i.putStringArrayListExtra("imageList",imageList);
                int location[] = new int[2];
                holder.getIvImageDesc().getLocationOnScreen(location);
                i.putExtra("left", location[0]);
                i.putExtra("top", location[1]);
                i.putExtra("height", holder.getIvImageDesc().getHeight());
                i.putExtra("width", holder.getIvImageDesc().getWidth());
                context.getContext().startActivity(i);
                context.getActivity().overridePendingTransition(0,0);
//                Dialog dialog=new Dialog(context.getContext(),R.style.Dialog_Fullscreen);
//                PhotoView photoView=new PhotoView(context.getContext());
//                dialog.setContentView(photoView);
//                dialog.show();
//                Glide.with(context)
//                        .load(commonShare.getImageDesc())
//                        .placeholder(R.drawable.guide1)
//                        .error(R.drawable.guide1)
//                        .crossFade()
//                        .into(photoView);

            }
        });
    }

    @Override
    public int getItemCount() {
        return commonShareList.size();
    }
}
