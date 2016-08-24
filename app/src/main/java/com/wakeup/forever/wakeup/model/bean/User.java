package com.wakeup.forever.wakeup.model.bean;

public class User {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String nickName;
    private String headURL;
    private String token;
    private String sex;
    private String signature;
    private Integer accumulatePoint;
    private Long birth;
    private String campus;

    public User(String password, String phone) {
        // TODO Auto-generated constructor stub
        this.password = password;
        this.phone = phone;
    }

    public User() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadURL() {
        return headURL;
    }

    public void setHeadURL(String headURL) {
        this.headURL = headURL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getAccumulatePoint() {
        return accumulatePoint;
    }

    public void setAccumulatePoint(Integer accumulatePoint) {
        this.accumulatePoint = accumulatePoint;
    }

    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }

    public String getCampus() {
        return campus;
    }
    public void setCampus(String campus) {
        this.campus = campus;
    }

}
