package edu.cs.scu;

import edu.cs.scu.bean.PropertyBean;
import edu.cs.scu.dao.impl.PropertyDaoImpl;

/**
 * Created by Wang Han on 2017/6/21 13:32.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class TestProperty {
    public static void main(String args[]) {
        PropertyDaoImpl propertyDao = new PropertyDaoImpl();
        PropertyBean propertyBean = propertyDao.getPropertyById(1);
        System.out.println(propertyBean.getPropertyType());

        propertyDao.setyPropertyNotUse(propertyBean);
        PropertyBean propertyBean1 = propertyDao.getPropertyById(1);
        System.out.println(propertyBean1.getPropertyType());

        propertyDao.setPropertyUse(propertyBean);
        PropertyBean propertyBean2 = propertyDao.getPropertyById(1);
        System.out.println(propertyBean2.getPropertyType());
    }
}
