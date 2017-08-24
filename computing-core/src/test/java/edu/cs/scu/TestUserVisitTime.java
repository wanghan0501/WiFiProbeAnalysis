package edu.cs.scu;

import edu.cs.scu.bean.UserVisitTimeBean;
import edu.cs.scu.constants.TimeConstants;
import edu.cs.scu.dao.impl.UserVisitTimeDaoImpl;
import edu.cs.scu.javautils.DateUtil;

import java.util.ArrayList;

/**
 * Created by Wang Han on 2017/6/20 17:00.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class TestUserVisitTime {
    public static void main (String args[]){
        UserVisitTimeDaoImpl userVisitTimeDao = new UserVisitTimeDaoImpl();

        ArrayList<UserVisitTimeBean> userVisitTimeBeanArrayList=new ArrayList<>();

        UserVisitTimeBean userVisitTimeBean = new UserVisitTimeBean();
        userVisitTimeBean.setMac("00:00:00:00:00:00");
        userVisitTimeBean.setShopId(1);
        userVisitTimeBean.setVisitTime("2017-04-03 12:12:12");
        userVisitTimeBeanArrayList.add(userVisitTimeBean);

        UserVisitTimeBean userVisitTimeBean1 = new UserVisitTimeBean();
        userVisitTimeBean1.setMac("20:20:20:20:20:20");
        userVisitTimeBean1.setShopId(2);
        userVisitTimeBean1.setVisitTime("2017-04-03 12:12:12");
        userVisitTimeBeanArrayList.add(userVisitTimeBean1);

        userVisitTimeDao.addUserVisitTimeByBatch(userVisitTimeBeanArrayList);

        String firstTime = userVisitTimeDao.getFirstVisitTIme(1,"6c:70:f8:8e:7e:c1");
        System.out.println(firstTime);
        String secondeTime = "2017-06-21 21:02:02.0";
        System.out.println(DateUtil.after(firstTime,secondeTime, TimeConstants.TIME_FORMAT,59000));
    }
}
