package com.chenxinjian.smartstock.server.db.bean;

import com.sinkinchan.transport.module.TransportType;

import java.util.Date;

/**
 * Created by apple on 2016/10/23.
 */
public class UserActionBean {
    private int id;
    //名称
    private String name;
    //描述
    private String desc;
    //积分
    private int integral;
    //奖励 如果true就添加积分 反则扣除积分
    private boolean reward;
    //添加时间
    private Date addTime;
    //对应类型
    private String transportType;

    public UserActionBean(String transportType) {
        this.transportType = transportType;
    }

    public UserActionBean() {
    }

    public int getId() {
        return id;
    }

    public UserActionBean setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserActionBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public UserActionBean setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public int getIntegral() {
        return integral;
    }

    public UserActionBean setIntegral(int integral) {
        this.integral = integral;
        return this;
    }

    public boolean isReward() {
        return reward;
    }

    public UserActionBean setReward(boolean reward) {
        this.reward = reward;
        return this;
    }

    public Date getAddTime() {
        return addTime;
    }

    public UserActionBean setAddTime(Date addTime) {
        this.addTime = addTime;
        return this;
    }

    public TransportType getTransportType() {
        return TransportType.valueOf(transportType);
    }

    public UserActionBean setTransportType(String transportType) {
        this.transportType = transportType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserActionBean that = (UserActionBean) o;

        return transportType.equals(that.transportType);

    }

    @Override
    public int hashCode() {
        return transportType.hashCode();
    }

    @Override
    public String toString() {
        return "UserActionBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", integral=" + integral +
                ", reward=" + reward +
                ", addTime=" + addTime +
                ", transportType=" + transportType +
                '}';
    }
}

