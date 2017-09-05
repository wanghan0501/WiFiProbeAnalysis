package edu.cs.scu.offLineAnalysis

import edu.cs.scu.dao.impl.UserDaoImpl

/**
  * 离线数据分析
  *
  * Created by Wang Han on 2017/6/20 20:11.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
  *
  * @author Wang Han
  */
class GetStayTime extends Runnable {
  override def run(): Unit = {
    while(true){
      val userDao: UserDaoImpl = new UserDaoImpl()
      userDao.updateStayTime()
      Thread.sleep(60000)
    }
  }
}
