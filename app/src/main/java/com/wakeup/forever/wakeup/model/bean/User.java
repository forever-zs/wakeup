package com.wakeup.forever.wakeup.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName ="tb_user")
public class User {
    @DatabaseField(columnName = "id" ,unique = true,id = true)
    private int id;
    @DatabaseField(columnName = "password")
    private String password;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "phone")
    private String phone;
    @DatabaseField(columnName = "nick_name")
    private String nickName;
    @DatabaseField(columnName = "head_url")
    private String headURL;
    @DatabaseField(columnName = "token")
    private String token;
    @DatabaseField(columnName = "sex")
    private String sex;
    @DatabaseField(columnName = "signature")
    private String signature;
    @DatabaseField(columnName = "accumulate_point")
    private Integer accumulatePoint;
    @DatabaseField(columnName = "birth")
    private Long birth;
    @DatabaseField(columnName = "campus")
    private String campus;


    public User(String password, String phone) {
        // TODO Auto-generated constructor stub
        this.password = password;
        this.phone = phone;
    }

    public User() {
        // TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headURL='" + headURL + '\'' +
                ", token='" + token + '\'' +
                ", sex='" + sex + '\'' +
                ", signature='" + signature + '\'' +
                ", accumulatePoint=" + accumulatePoint +
                ", birth=" + birth +
                ", campus='" + campus + '\'' +
                '}';
    }
}
