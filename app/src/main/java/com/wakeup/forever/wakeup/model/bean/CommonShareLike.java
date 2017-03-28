package com.wakeup.forever.wakeup.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_common_share_like")
public class CommonShareLike {
    @DatabaseField(columnName = "id" ,unique = true,id = true)
    private int id;
    @DatabaseField(columnName = "common_share_id")
    private Integer commonShareId;
    @DatabaseField(columnName = "user_phone")
    private String userPhone;
    @DatabaseField(columnName = "create_time")
    private Long createTime;
    @DatabaseField(columnName = "user_name")
    private String userName;

    public CommonShareLike() {
        // TODO Auto-generated constructor stub
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommonShareId() {
        return commonShareId;
    }

    public void setCommonShareId(Integer commonShareId) {
        this.commonShareId = commonShareId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
