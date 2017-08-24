package edu.cs.scu.dao;

import edu.cs.scu.bean.VendorMacBean;

import java.util.List;

/**
 * Created by Wang Han on 2017/6/19 16:43.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public interface VendorMacDao {
    // 添加信息
    void addVendorMac(VendorMacBean vendorMacBean);

    void addVendorMacBatch(List<VendorMacBean> vendorMacBeanList);

    // 根据Mac前缀获取制造商
    VendorMacBean getVendorByMac(String mac);
}
