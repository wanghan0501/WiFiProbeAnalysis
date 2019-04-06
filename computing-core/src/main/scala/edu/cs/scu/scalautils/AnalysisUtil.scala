package edu.cs.scu.scalautils

import edu.cs.scu.bean.PropertyBean
import edu.cs.scu.common.constants.AnalysisConstants
import edu.cs.scu.dao.impl.UserVisitTimeDaoImpl
import edu.cs.scu.javautils.DateUtil
import org.apache.log4j.Logger

/**
  * 获取数据的工具类
  *
  * Created by Wang Han on 2017/6/20 18:44.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
  *
  * @author Wang Han
  */
object AnalysisUtil {
  // 得到log记录器
  private val logger = Logger.getLogger(classOf[AnalysisUtil])
  // 配置属性
  private val property: PropertyBean = InitUtil.getPropertyFromDatabase()

  /**
    * 判断用户是否入店
    *
    * @param range 距离
    * @param rssi  信号强度
    * @return
    */
  def isCheckIn(range: Double, rssi: Int): Boolean = {

//    if (range < property.getVisitRange)
//      true
//    else
//      false
    if(range < 10)
      true
    else
      false
  }

  /**
    * 判断用户是否是深度访问
    *
    * @param shopId 商店ID
    * @param mac    用户Mac
    * @param time   访问时间
    * @return
    */
  def isDeepVisit(shopId: Int, mac: String, time: Long): Boolean = {
    val userVisitTimeDao = new UserVisitTimeDaoImpl
    val queryResult = userVisitTimeDao.getFirstVisitTime(shopId, mac)
    val firstTIme = if (queryResult == AnalysisConstants.DEFAULT_FIRST_VISIT_TIME) time else queryResult
    DateUtil.after(firstTIme, time, property.getVisitTimeSplit.toLong)
  }


  /**
    * 计算入店率
    *
    * @param checkInFlow
    * @param totalFlow
    * @return
    */
  def getCheckInRate(checkInFlow: Int, totalFlow: Int): Double = {
    var checkInRate: Double = 0.0
    try {
      if (totalFlow != 0)
        checkInRate = checkInFlow.toDouble / totalFlow.toDouble
    } catch {
      case e: ArithmeticException => {
        checkInRate = 0.0
      }
    }
    checkInRate
  }


  /**
    * 计算深访率
    *
    * @param deepVisitFlow
    * @param checkInFlow
    * @return
    */
  def getDeepVisitRate(deepVisitFlow: Int, checkInFlow: Int): Double = {
    var deepVisitRate: Double = 0.0
    try {
      if (checkInFlow != 0)
        deepVisitRate = deepVisitFlow.toDouble / checkInFlow.toDouble
    } catch {
      case e: ArithmeticException => {
        deepVisitRate = 0.0
      }
    }
    deepVisitRate
  }

  /**
    * 计算浅访率
    *
    * @param deepVisitFlow
    * @param checkInFlow
    * @return
    */
  def getShallowVisitRate(deepVisitFlow: Int, checkInFlow: Int): Double = {
    var shallowVisitRate: Double = 0.0
    try {
      if (checkInFlow != 0)
        shallowVisitRate = 1.0 - deepVisitFlow.toDouble / checkInFlow.toDouble
    } catch {
      case e: ArithmeticException => {
        shallowVisitRate = 0.0
      }
    }
    shallowVisitRate
  }
}

class AnalysisUtil {

}
