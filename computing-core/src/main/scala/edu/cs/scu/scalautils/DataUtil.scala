package edu.cs.scu.scalautils

import edu.cs.scu.bean.PropertyBean
import edu.cs.scu.common.constants.TableConstants
import edu.cs.scu.dao.impl.UserVisitTimeDaoImpl
import edu.cs.scu.javautils.{DateUtil, MacAdressUtil, StringUtil}
import org.apache.log4j.Logger
import org.apache.spark.HashPartitioner
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

/**
  * 获取数据的工具类
  *
  * Created by Wang Han on 2017/6/20 18:44.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
  *
  * @author Wang Han
  */
object DataUtil {
  // 得到log记录器
  private val logger = Logger.getLogger(classOf[DataUtil])
  // 配置属性
  private val property: PropertyBean = InitUtil.getPropertyFromDatabase()


  /**
    * 原始数据经过预处理得到预处理数据
    * 预处理后的数据形如(t,id,mmac,rate,time,vmac,wssid)
    *
    * @param sQLContext
    * @param originDStream
    * @return (t,id,mmac,rate,time,vmac,wssid)
    */
  //  def getPreDStream(sQLContext: SQLContext, originDStream: DStream[String]):
  //  DStream[(Row, String, String, String, String, String, String)] = {
  //    val preDStream = originDStream.transform(rdd => {
  //      val df = sQLContext.read.json(rdd)
  //      val preData = df.flatMap(t => {
  //        val id = t.getString(1)
  //        // Wi-Fi探针Mac地址
  //        val mmac = t.getString(2)
  //        val rate = t.getString(3)
  //        val time = DateUtil.parseTime(t.getString(4), TimeTypes.TIME_FORMAT)
  //        val wmac = t.getString(5)
  //        val wssid = t.getString(6)
  //        val data = t.getSeq(0).asInstanceOf[Seq[Row]].iterator
  //        val preIterator = data.map(t => (t, id, mmac, rate, time, wmac, wssid))
  //        preIterator
  //      })
  //      preData
  //    })
  //    preDStream
  //  }

  /**
    * 获得原始数据中data字段中的手机数据,并处理统计
    * 返回值形如：(mmac=00:00:00:00:00:00|time=2017-04-04 12:12:12|brand=Ximi,
    * 00:00:00:00:00:00,Ximi,range,rssi)
    *
    * @param sQLContext
    * @param preDStream
    * @return
    */
  def getPhoneDStream(sQLContext: SQLContext, preDStream: DStream[(Row, String, String, String, String, String, String)]):
  DStream[(String, String, String, Double, Int)] = {
    val phoneDStream = preDStream.map(tuple => {
      val mmac = tuple._3
      val time = tuple._5
      val mac = tuple._1.getString(0)
      val brand = MacAdressUtil.getBrandByMac(tuple._1.getString(0))
      val range = tuple._1.getString(1).toDouble
      val rssi = tuple._1.getString(2).toInt
      val key = s"${TableConstants.FILED_MMAC}=${mmac}|${TableConstants.FIELD_TIME}=${time}|" +
        s"${TableConstants.FIELD_BRAND}=${brand}"
      (key, mac, brand, range, rssi)
    })
    phoneDStream
  }


  /**
    * 获得流量统计DStream，形如（key,range,rssi）
    *
    * @param preDStream
    * @return
    */
  def getFlowDStream(preDStream: DStream[(Row, String, String, String, String, String, String)]): DStream[(String, Double, Int)] = {
    val flowDStream = preDStream.map(tuple => {
      val mmac = tuple._3
      val time = tuple._5
      val range = tuple._1.getString(1).toDouble
      val rssi = tuple._1.getString(2).toInt
      val key = s"${TableConstants.FILED_MMAC}=${mmac}|${TableConstants.FIELD_TIME}=${time}"
      (key, range, rssi)
    })
    flowDStream
  }


  /**
    * 获得总人流量，返回值形如(mmac=00:00:00:00:00:00,time=2017-04-04 12:12:12,10)
    *
    * @param flowDStream
    * @return
    */
  def getTotalFlow(flowDStream: DStream[(String, Double, Int)]): DStream[(String, Long)] = {
    val totalFlowDStream = flowDStream.map(t => t._1)
    totalFlowDStream.countByValue()
  }


  /**
    * 获得入店流量，返回值形如(mmac=00:00:00:00:00:00,time=2017-04-04 12:12:12,10)
    *
    * @param flowDStream
    * @return
    */
  def getCheckInFlow(flowDStream: DStream[(String, Double, Int)]): DStream[(String, Long)] = {
    val checkInFlowDStream = flowDStream.filter(t => {
      val range = t._2
      val rssi = t._3
      isCheckIn(range, rssi)
    }).map(t => t._1)
    checkInFlowDStream.countByValue()
  }

  /**
    * 判断用户是否入店
    *
    * @param range 距离
    * @param rssi  信号强度
    * @return
    */
  def isCheckIn(range: Double, rssi: Int): Boolean = {
    if (range < property.getVisitRange)
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
    val fisrtTIme = userVisitTimeDao.getFirstVisitTIme(shopId, mac).toLong
    DateUtil.after(fisrtTIme, time, property.getVisitTimeSplit.toLong)
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

  /**
    * 统计进店用户所用手机品牌数量
    * 统计结果形如:(Ximi,100),(Huawei,50)
    *
    * @param sQLContext
    * @param streamingContext
    * @param phoneData
    * @return
    */
  def getBrandCount(sQLContext: SQLContext, streamingContext: StreamingContext,
                    phoneData: DStream[(String, String, String, Double, Int)]): DStream[(String, Int)] = {
    /**
      * 内部更新函数
      *
      * @param iterator
      * @return
      */
    def updateFunc(iterator: Iterator[(String, Seq[Int], Option[Int])]): Iterator[(String, Int)] = {
      iterator.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(i => (x, i)) }
    }

    // 提取data中的brand字段
    val brandData = phoneData.map(t => {
      val key = t._1
      val time = StringUtil.getFieldFromConcatString(key, "\\|", TableConstants.FIELD_TIME)
      val newKey = StringUtil.setFieldInConcatString(key, "\\|", TableConstants.FIELD_TIME, time)
      (newKey, 1)
    })

    // 更新品牌统计表
    val brandCounts = brandData.updateStateByKey(updateFunc _,
      new HashPartitioner(streamingContext.sparkContext.defaultParallelism),
      rememberPartitioner = true)

    brandCounts.print()
    brandCounts
  }
}

class DataUtil {

}
