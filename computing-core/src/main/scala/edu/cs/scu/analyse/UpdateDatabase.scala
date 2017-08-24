package edu.cs.scu.analyse

import edu.cs.scu.bean.{UserBean, UserVisitTimeBean}
import edu.cs.scu.constants.TableConstants
import edu.cs.scu.dao.impl.{UserDaoImpl, UserVisitTimeDaoImpl}
import edu.cs.scu.javautils.StringUtil
import org.apache.spark.streaming.dstream.DStream

import scala.collection.JavaConverters._


/**
  * 开启新线程更新数据库
  *
  * Created by Wang Han on 2017/6/21 14:29.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
  *
  * @author Wang Han
  */
object UpdateDatabase {


  /**
    * 更新user表数据
    *
    * @param phoneDStream
    */
  def updateUser(phoneDStream: DStream[(String, String, String, Double, Int)]): Unit = {
    phoneDStream.foreachRDD(rdd => {
      if (rdd.count() > 0) {
        val list = rdd.map(l => {
          val user = new UserBean
          user.setShopId(1)
          user.setMac(l._2)
          user.setBrand(l._3)
          user
        }).collect().toList

        val userDaoImpl = new UserDaoImpl
        userDaoImpl.addUserByBatch(list.asJava)
      }
    })
  }

  /**
    * 更新userVisitTime表
    *
    * @param phoneDStream
    */
  def updateUserVisitTime(phoneDStream: DStream[(String, String, String, Double, Int)]): Unit = {
    phoneDStream.foreachRDD(rdd => {
      if (rdd.count() > 0) {
        val userVisitTimeList = rdd.map(l => {
          val userVisitTime = new UserVisitTimeBean
          userVisitTime.setShopId(1)
          userVisitTime.setMac(l._2)
          userVisitTime.setVisitTime(StringUtil.getFieldFromConcatString(l._1, "\\|", TableConstants.FIELD_TIME))
          userVisitTime
        }).collect().toList

        val userVisitTimeDaoImpl = new UserVisitTimeDaoImpl
        userVisitTimeDaoImpl.addUserVisitTimeByBatch(userVisitTimeList.asJava)
      }
    })
  }

  def updateUserVisit(totalFlow: DStream[(String, Long)], checkInFlow: DStream[(String, Long)]) = {

  }

}
