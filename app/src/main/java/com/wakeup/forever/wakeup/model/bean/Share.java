package com.wakeup.forever.wakeup.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_share")
public class Share {


	public Share() {
		// TODO Auto-generated constructor stub
	}
	@DatabaseField(columnName = "id" ,unique = true,id = true)
	private int id;
	@DatabaseField(columnName = "author_phone")
	private String authorPhone;
	@DatabaseField(columnName = "view_count")
	private Integer viewCount;
	@DatabaseField(columnName = "likeCount")
	private Integer likeCount;
	@DatabaseField(columnName = "image_link")
	private String imageLink;
	@DatabaseField(columnName = "author_name")
	private String authorName;
	@DatabaseField(columnName = "title")
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthorPhone() {
		return authorPhone;
	}

	public void setAuthorPhone(String authorPhone) {
		this.authorPhone = authorPhone;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
}
