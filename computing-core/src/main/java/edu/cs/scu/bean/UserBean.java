package edu.cs.scu.bean;

import java.io.Serializable;

/**
 * Created by Wang Han on 2017/6/19 13:46.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class UserBean implements Serializable{

    private static final long serialVersinUID = 351877796426921776L;

    // 商店ID
    private int shopId;
    // Mac地址
    private String mac;
    // 手机品牌
    private String brand;
    //停留时间
    private Long stayTime;

    //来访周期
    private Long visitCycle;

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getStayTime() {
        return stayTime;
    }

    public void setStayTime(Long stayTime) {
        this.stayTime = stayTime;
    }

    public Long getVisitCycle() {
        return visitCycle;
    }

    public void setVisitCycle(Long visitCycle) {
        this.visitCycle = visitCycle;
    }
}
