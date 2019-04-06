package edu.cs.scu.bean;

import java.io.Serializable;

/**
 * 店铺探针配置项实体
 * <p>
 * Created by Wang Han on 2017/6/18 18:22.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class PropertyBean implements Serializable {
    private static final long serialVersinUID = 351877796426921776L;

    private int propertyId;
    private int shopId;
    private String mmac;
    private String visitCycle;
    private double visitRange;
    private int visitRSSI;
    private String activityDegree;
    private String visitTimeSplit;
    private boolean propertyType;

    public PropertyBean() {

    }

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
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

    public String getVisitCycle() {
        return visitCycle;
    }

    public void setVisitCycle(String visitCycle) {
        this.visitCycle = visitCycle;
    }

    public double getVisitRange() {
        return visitRange;
    }

    public void setVisitRange(double visitRange) {
        this.visitRange = visitRange;
    }

    public int getVisitRSSI() {
        return visitRSSI;
    }

    public void setVisitRSSI(int visitRSSI) {
        this.visitRSSI = visitRSSI;
    }

    public String getActivityDegree() {
        return activityDegree;
    }

    public void setActivityDegree(String activityDegree) {
        this.activityDegree = activityDegree;
    }

    //记得改
    public String getVisitTimeSplit() {
        return "10000";
    }

    public void setVisitTimeSplit(String visitTimeSplit) {
        this.visitTimeSplit = visitTimeSplit;
    }

    public boolean getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(boolean propertyType) {
        this.propertyType = propertyType;
    }
}
