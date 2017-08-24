package edu.cs.scu.dao;

import edu.cs.scu.bean.UserVisitTimeBean;

import java.util.List;

/**
 * Created by Wang Han on 2017/6/20 16:40.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public interface UserVisitTimeDao {
    void addUserVisitTime(UserVisitTimeBean userVisitTimeBean);

    void addUserVisitTimeByBatch(List<UserVisitTimeBean> userVisitTimeBeanList);

    String getFirstVisitTIme(int shopId,String mac);
}
