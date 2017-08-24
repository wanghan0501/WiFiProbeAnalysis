package edu.cs.scu;

import edu.cs.scu.bean.PropertyBean;
import edu.cs.scu.dao.impl.PropertyDaoImpl;

/**
 * Created by Wang Han on 2017/6/18 19:34.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class TestPropertyBean {
    public static void main(String args[]) {
        PropertyDaoImpl propertyDao = new PropertyDaoImpl();
        PropertyBean propertyBean;
        propertyBean = propertyDao.getNewProperty();
        System.out.println(propertyBean.getPropertyId());
        System.out.println(propertyBean.getActivityDegree());
        System.out.println(propertyBean.getPropertyType());
        System.out.println(propertyBean.getVisitCycle());
        System.out.println(propertyBean.getVisitTimeSplit());
        System.out.println(propertyBean.getVisitRange());
    }
}
