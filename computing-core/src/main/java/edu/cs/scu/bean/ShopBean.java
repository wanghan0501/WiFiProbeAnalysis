package edu.cs.scu.bean;

import java.io.Serializable;

/**
 * Created by Wang Han on 2017/6/19 13:56.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class ShopBean implements Serializable {

    private static final long serialVersinUID = 351877796426921776L;

    // 商点编号
    private int shopId;
    // Wi-Fi探针Mac地址
    private String mmac;
    // 纬度
    private double lat;
    // 经度
    private double lon;
    // 地址
    private String addr;

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getMmac() {
        return mmac;
    }

    public void setMmac(String mmac) {
        this.mmac = mmac;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
