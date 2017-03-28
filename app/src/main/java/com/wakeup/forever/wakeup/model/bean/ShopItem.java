package com.wakeup.forever.wakeup.model.bean;

import java.io.Serializable;

/**
 * Created by forever on 2016/11/21.
 */

public class ShopItem implements Serializable{
    private Integer id;
    private String imageUrl;
    private String shopName;
    private Integer point;
    private String description;
    private Integer exchangeCount;
    private String shopUrl;
    private String imageDisplay;
    private String shopPhone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getImageDisplay() {
        return imageDisplay;
    }

    public void setImageDisplay(String imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }
}
