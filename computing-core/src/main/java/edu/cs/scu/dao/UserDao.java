package edu.cs.scu.dao;

import edu.cs.scu.bean.UserBean;

import java.util.List;

/**
 * 用户信息数据库接口
 *
 * Created by Wang Han on 2017/6/19 13:58.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public interface UserDao {

    void addUser(UserBean userBean);

    void addUserByBatch(List<UserBean> userBeanList);
}
