package com.chenxinjian.smartstock.server.db.bean;

import java.util.Date;

/**
 * Created by apple on 2016/10/23.
 * 用户行为记录表
 */
public class UserActionRecordBean {
    private int id;
    //用户id
    private int userId;
    //行为id
    private int actionId;
    //添加时间
    private Date addTime;
    //过期时间
    private Date outOfDate;

    public int getId() {
        return id;
    }

    public UserActionRecordBean setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserActionRecordBean setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Date getAddTime() {
        return addTime;
    }

    public UserActionRecordBean setAddTime(Date addTime) {
        this.addTime = addTime;
        return this;
    }

    public int getActionId() {
        return actionId;
    }

    public UserActionRecordBean setActionId(int actionId) {
        this.actionId = actionId;
        return this;
    }

    public Date getOutOfDate() {
        return outOfDate;
    }

    public UserActionRecordBean setOutOfDate(Date outOfDate) {
        this.outOfDate = outOfDate;
        return this;
    }
}
