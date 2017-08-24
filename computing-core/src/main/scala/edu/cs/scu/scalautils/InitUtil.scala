package edu.cs.scu.scalautils

import edu.cs.scu.bean.PropertyBean
import edu.cs.scu.conf.ConfigurationManager
import edu.cs.scu.constants.SparkConstants
import edu.cs.scu.dao.impl.PropertyDaoImpl
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.Logger
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 初始化spark环境工具
  *
  * Created by Wang Han on 2017/3/29 14:43.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © Wang Han SCU. All Rights Reserved.
  *
  * @author Wang Han
  */

object InitUtil {
  // 得到log记录器
  private val logger = Logger.getLogger(classOf[InitUtil])


  /**
    * 初始化Spark Session
    *
    * @return
    */
  def initSparkSession(): SparkSession = {
    val local = ConfigurationManager.getBoolean(SparkConstants.SPARK_LOCAL)
    if (local) {
      SparkSession.builder
        .master(ConfigurationManager.getString(SparkConstants.SPARK_MASTER))
        .appName(SparkConstants.SPARK_APP_NAME)
        .getOrCreate
    }
    else {
      SparkSession.builder
        .appName(SparkConstants.SPARK_APP_NAME)
        .getOrCreate
    }
  }


  /**
    * 加载streaming环境
    *
    * @param sparkContext
    * @return
    */
  def getStreamingContext(sparkContext: SparkContext): StreamingContext = {
    val local = ConfigurationManager.getBoolean(SparkConstants.SPARK_LOCAL)
    if (local) {

    }
    new StreamingContext(sparkContext, Seconds(ConfigurationManager.getLong(SparkConstants.SPARK_STREAMING_COLLECT_TIME)))
  }


  /**
    * 加载检查点及源数据目录
    *
    * @param streamingContext
    * @return
    */
  def getDStream(streamingContext: StreamingContext): DStream[String] = {
    try {
      if (ConfigurationManager.getBoolean(SparkConstants.SPARK_LOCAL)) {
        streamingContext.checkpoint(ConfigurationManager.getString(SparkConstants.SPARK_LOCAL_CHECK_POINT_DIR))
        streamingContext.textFileStream(ConfigurationManager.getString(SparkConstants.SPARK_LOCAL_DATA_SOURCE))
      } else {
        streamingContext.checkpoint(ConfigurationManager.getString(SparkConstants.SPARK_CHECK_POINT_DIR))
        streamingContext.textFileStream(ConfigurationManager.getString(SparkConstants.SPARK_DATA_SOURCE))
      }
    } catch {
      case e: Exception => {
        e.printStackTrace()
        logger.error(e.getStackTrace)
        null
      }
    }
  }

  /**
    * 从kafka中获取数据
    *
    * @param streamingContext
    * @return
    */
  def getDStreamFromKafka(streamingContext: StreamingContext): DStream[(String, String)] = {
    val zkQuorum = ConfigurationManager.getString(SparkConstants.KAFKA_ZOOKEEPER_QUORUM)
    val groupId = ConfigurationManager.getString(SparkConstants.KAFKA_GROUP_ID)
    val topics = ConfigurationManager.getString(SparkConstants.KAFKA_TOPICS).split(",")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> zkQuorum,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> groupId,
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
    stream.map(record => (record.key, record.value))
  }

  /**
    * 从数据中加载配置项
    *
    * @return
    */
  def getPropertyFromDatabase(): PropertyBean = {
    val propertyDao = new PropertyDaoImpl
    val propertyBean = propertyDao.getNewProperty
    propertyBean
  }
}

class InitUtil {

}
