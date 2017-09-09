package education.cs.scu.service;

import education.cs.scu.entity.UserBean;
import education.cs.scu.entity.UserVisitBean;

import java.util.List;

/**
 * Created by Wang Han on 2017/6/18 15:18.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public interface UserVisitService {
    void addUserVisit(UserVisitBean userFlow) throws Exception;
    List<UserVisitBean>  queryUserVisit(List<Integer> shopIdlist) throws Exception;
    List<UserBean> queryUserShop(List<Integer> shopIdlist) throws Exception;

    List<Integer> queryShopList(String userName) throws Exception;
}
