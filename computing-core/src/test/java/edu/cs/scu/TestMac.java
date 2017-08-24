package edu.cs.scu;

import edu.cs.scu.javautils.MacAdressUtil;

/**
 * Created by Wang Han on 2017/6/19 17:48.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class TestMac {
    public static void main(String args[]){
        String name = MacAdressUtil.getVendorByMac("00:00:00:28:7f:aa");
        System.out.println(name);
        String brand = MacAdressUtil.getBrandByMac("2b:0e:fc:65:8b:c0");
        System.out.println(brand);
    }
}
