package com.wakeup.forever.wakeup.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

@DatabaseTable(tableName = "tb_common_share")
public class CommonShare{
	@DatabaseField(columnName = "id" ,unique = true,id = true)
	private int id;
	@DatabaseField(columnName = "user_phone")
	private String userPhone;
	@DatabaseField(columnName = "content")
	private String content;

	private User user;
	@DatabaseField(columnName = "image_desc")
	private String imageDesc;
	@DatabaseField(columnName = "view_count")
	private Integer viewCount;
	@DatabaseField(columnName = "publish_time")
	private Long publishTime;
	@DatabaseField(columnName = "is_favourite")
	private Boolean isFavourite=false;

	private ArrayList<CommonShareLike> likedList;

	private ArrayList<CommonShareComment> commentList;
	
	
	
	public CommonShare() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getFavourite() {
		return isFavourite;
	}

	public void setFavourite(Boolean favourite) {
		isFavourite = favourite;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getUserPhone() {
		return userPhone;
	}



	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getImageDesc() {
		return imageDesc;
	}



	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}



	public Integer getViewCount() {
		return viewCount;
	}



	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}




	public ArrayList<CommonShareLike> getLikedList() {
		return likedList;
	}



	public void setLikedList(ArrayList<CommonShareLike> likedList) {
		this.likedList = likedList;
	}



	public ArrayList<CommonShareComment> getCommentList() {
		return commentList;
	}



	public void setCommentList(ArrayList<CommonShareComment> commentList) {
		this.commentList = commentList;
	}


}
