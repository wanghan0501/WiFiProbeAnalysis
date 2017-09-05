package edu.cs.scu.analysis.realtimeanalysis

import java.util

import edu.cs.scu.bean.{UserBean, UserVisitBean, UserVisitTimeBean}
import edu.cs.scu.common.constants.AnalysisConstants
import edu.cs.scu.common.types.TimeTypes
import edu.cs.scu.dao.impl.{UserDaoImpl, UserVisitDaoImpl, UserVisitTimeDaoImpl}
import edu.cs.scu.javautils.{DateUtil, MacAdressUtil}
import edu.cs.scu.scalautils.AnalysisUtil
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

/**
  * 实时数据分析
  *
  * Created by Wang Han on 2017/6/20 19:57.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
  *
  * @author Wang Han
  */
object RealTimeAnalysis {
  def analysis(sparkSession: SparkSession, streamingContext: StreamingContext, data: DStream[String]): Unit = {

    //    val preData = DataUtil.getPreDStream(sQLContext, data)
    //    val phoneData = DataUtil.getPhoneDStream(sQLContext, preData)
    //    UpdateDatabase.updateUser(phoneData)
    //    UpdateDatabase.updateUserVisitTime(phoneData)
    //    val brandCount = getBrandCount(sQLContext, streamingContext, phoneData)
    //    val flowData = DataUtil.getFlowDStream(preData)
    //    val totalFlowData = DataUtil.getTotalFlow(flowData)
    //    val checkInFlowData = DataUtil.getCheckInFlow(flowData)

    data.foreachRDD(foreachFunc = rdd => {

      // 如果当前窗口记录不为空
      if (rdd.count() >= 1) {
        // 读取格式化json
        val df = sparkSession.read
          .json(rdd)
        // 打印表结构
        //        df.printSchema()
        df.foreach(t => {
          // 地址信息
          val addr = t.getString(t.fieldIndex(AnalysisConstants.FIELD_ARRD))
          val datas = t.getSeq(t.fieldIndex(AnalysisConstants.FIELD_DATA)).asInstanceOf[Seq[Row]]
          // 嗅探器设备id
          val id = t.getString(t.fieldIndex(AnalysisConstants.FIELD_ID))
          // 纬度
          val lat = t.getString(t.fieldIndex(AnalysisConstants.FIELD_LAT)).toDouble
          // 经度
          val lon = t.getString(t.fieldIndex(AnalysisConstants.FIELD_LON)).toDouble
          // 嗅探器设备自身WiFi mac
          val mmac = t.getString(t.fieldIndex(AnalysisConstants.FIELD_MMAC))
          // 发送频率
          val rate = t.getString(t.fieldIndex(AnalysisConstants.FIELD_RATE))
          // 时间戳，采集到这些mac的时间
          val time = DateUtil.parseTime(t.getString(t.fieldIndex(AnalysisConstants.FIELD_TIME)), TimeTypes.ENGLISH_TIME_FORMAT)
          // 嗅探器设备连接的WIFI的mac地址
          val wmac = t.getString(t.fieldIndex(AnalysisConstants.FIELD_WMAC))
          // 嗅探器设备连接的WIFI的ssid
          val wssid = t.getString(t.fieldIndex(AnalysisConstants.FIELD_WSSID))

          // 总人数，根据mac地址判断
          var totalFlow: Int = 0
          // 入店总人数，根据rssi判断
          var checkInFlow: Int = 0
          // 深度访问人数,根据访问时间判断
          var deepVisitFlow: Int = 0
          // 用户访问时间列表
          val userVisitTimeBeanArrayList: util.List[Object] = new util.ArrayList()
          // 用户列表
          val userBeanArrayList: util.List[Object] = new util.ArrayList()
          // 用户数据迭代器
          val datasIterator = datas.iterator
          while (datasIterator.hasNext) {
            val currentData = datasIterator.next()
            // 手机是否睡眠
            val ds = currentData.getString(currentData.fieldIndex(AnalysisConstants.FIELD_DS))
            // 采集到的手机mac地址
            val mac = currentData.getString(currentData.fieldIndex(AnalysisConstants.FIELD_MAC))
            totalFlow = totalFlow + 1
            // 手机距离嗅探器的测距距离字段，单位米
            val range = currentData.getString(currentData.fieldIndex(AnalysisConstants.FIELD_RANGE)).toDouble
            // 探针是否探测到路由设备
            val router = currentData.getString(currentData.fieldIndex(AnalysisConstants.FIELD_ROUTER))
            // 手机的信号强度
            val rssi = currentData.getString(currentData.fieldIndex(AnalysisConstants.FIELD_RSSI)).toInt
            // 是否与路由器相连
            val tc = currentData.getString(currentData.fieldIndex(AnalysisConstants.FIELD_TC))
            // 目标设备的mac地址，手机连接的WIFI的mac地址
            val tmc = currentData.getString(currentData.fieldIndex(AnalysisConstants.FIELD_TMC))

            // 判断用户是否入店
            if (AnalysisUtil.isCheckIn(range, rssi)) {
              checkInFlow = checkInFlow + 1
              if (AnalysisUtil.isDeepVisit(1, mac, time)) {
                deepVisitFlow = deepVisitFlow + 1
              }
            }
            // 向用户列表中加入新数据
            val userBean = new UserBean
            userBean.setShopId(1)
            userBean.setMac(mac)
            userBean.setBrand(MacAdressUtil.getBrandByMac(mac))
            userBeanArrayList.add(userBean)

            // 向用户访问列表中加入新数据
            val userVisitTimeBean = new UserVisitTimeBean
            userVisitTimeBean.setShopId(1)
            userVisitTimeBean.setMac(mac)
            userVisitTimeBean.setVisitTime(time)
            userVisitTimeBeanArrayList.add(userVisitTimeBean)
          } //end while

          // 插入用户数据
          val userDaoImpl = new UserDaoImpl
          userDaoImpl.add(userBeanArrayList)

          // 插入用户访问时间数据
          val userVisitTimeDaoImpl = new UserVisitTimeDaoImpl
          userVisitTimeDaoImpl.add(userVisitTimeBeanArrayList)

          // 进店率
          val checkInRate = AnalysisUtil.getCheckInRate(checkInFlow, totalFlow)
          // 深度访问率
          val deepVisitRate = AnalysisUtil.getDeepVisitRate(deepVisitFlow, checkInFlow)
          // 浅访率
          val shallowVisitRate = AnalysisUtil.getShallowVisitRate(deepVisitFlow, checkInFlow)
          // 添加用户相关信息
          val userVisitDaoIml = new UserVisitDaoImpl
          val userVisitList: util.List[Object] = new util.ArrayList[Object]()
          val userVisit = new UserVisitBean
          userVisit.setShopId(1)
          userVisit.setMmac(mmac)
          userVisit.setTime(time)
          userVisit.setTotalFlow(totalFlow)
          userVisit.setCheckInFlow(checkInFlow)
          userVisit.setCheckInRate(checkInRate)
          userVisit.setDeepVisitRate(deepVisitRate)
          userVisit.setShallowVisitRate(shallowVisitRate)
          userVisitList.add(userVisit)
          userVisitDaoIml.add(userVisitList)
          println("insert finished")

        }
        ) // end foreach
      }
    }
    )
  }

}
