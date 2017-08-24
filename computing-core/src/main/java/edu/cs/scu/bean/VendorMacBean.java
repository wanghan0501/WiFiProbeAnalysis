package edu.cs.scu.bean;

import java.io.Serializable;

/**
 * Created by Wang Han on 2017/6/19 16:39.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class VendorMacBean implements Serializable{

    private static final long serialVersinUID = 351877796426921776L;
    //Mac地址前缀
    private String macPrefix;
    // 制造厂商
    private String vendorName;

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public String getMacPrefix() {
        return macPrefix;
    }

    public void setMacPrefix(String macPrefix) {
        this.macPrefix = macPrefix;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
