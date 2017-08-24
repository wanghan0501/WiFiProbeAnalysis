package edu.cs.scu.dao;

import edu.cs.scu.bean.UserVisitBean;

import java.util.List;

/**
 * Created by Wang Han on 2017/6/18 15:18.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public interface UserVisitDao {
    void addUserVisit(UserVisitBean userVisitBean);

    void addUserVisitByBatch(List<UserVisitBean> userVisitBeanList);
}
