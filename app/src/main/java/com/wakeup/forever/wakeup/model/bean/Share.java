package com.wakeup.forever.wakeup.model.bean;

public class Share {

	public Share() {
		// TODO Auto-generated constructor stub
	}
	
	private Integer id;
	
	private String authorPhone;
	
	private Integer viewCount;
	
	private Integer likeCount;
	
	private String imageLink;

	private String authorName;
	
	private String title;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
