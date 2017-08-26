package edu.cs.scu.dao.factory;

import edu.cs.scu.dao.*;
import edu.cs.scu.dao.impl.*;

/**
 * Created by Wang Han on 2017/6/24 19:57.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class DaoFactory {

    public static PropertyDao getPropertyDao() {
        return new PropertyDaoImpl();
    }

    public static ShopDao getShopDao() {
        return new ShopDaoImpl();
    }

    public static TaskDao getTaskDao() {
        return new TaskDaoImpl();
    }

    public static UserDaoImpl getUserDao() {
        return new UserDaoImpl();
    }

    public static UserVisitDaoImpl getUserVisitDao() {
        return new UserVisitDaoImpl();
    }

    public static UserVisitTimeDaoImpl getUserVisitTimeDao() {
        return new UserVisitTimeDaoImpl();
    }

    public static VendorMacDao getVendorMacDao() {
        return new VendorMacDaoImpl();
    }
}
