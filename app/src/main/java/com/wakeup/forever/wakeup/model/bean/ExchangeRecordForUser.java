package com.wakeup.forever.wakeup.model.bean;

public class ExchangeRecordForUser extends ExchangeRecord{
	
	private String imageUrl;
    private String shopName;
    private Integer point;
    private String description;
    private Integer exchangeCount;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getExchangeCount() {
		return exchangeCount;
	}
	public void setExchangeCount(Integer exchangeCount) {
		this.exchangeCount = exchangeCount;
	}
    
    

}
