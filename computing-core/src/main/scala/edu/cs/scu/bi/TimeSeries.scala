package edu.cs.scu.bi

import java.sql.Timestamp
import java.time.{ZoneId, ZonedDateTime}

import com.cloudera.sparkts._
import edu.cs.scu.scalautils.InitUtil
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row}

/**
  * Created by Wang Han on 2017/9/1 21:51.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. All Rights Reserved.
  *
  * @author Wang Han
  */
object TimeSeries {
  def main(args: Array[String]): Unit = {
    val data = getData()
    val zone = ZoneId.systemDefault()
    var dtIndex: UniformDateTimeIndex = DateTimeIndex.uniformFromInterval(
      // start time
      ZonedDateTime.of(2013, 1, 1, 0, 0, 0, 0, zone),
      // end time
      ZonedDateTime.of(2013, 1, 13, 0, 0, 0, 0, zone),
      // frequency
      new DayFrequency(1))


    val trainTsrdd = TimeSeriesRDD.timeSeriesRDDFromObservations(dtIndex, data,
      "time", "key", "value")

    val model = new TimeSeriesModel(5)
    //    val predit = model.holtWintersModelTrain(trainTsrdd, 6, "Multiplicative")
    val predit = model.arimaModelTrain(trainTsrdd)

  }

  def getData(): DataFrame = {
    val spark = InitUtil.initSparkSession()
    val data1 = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "false")
      .load("/Users/mac/Desktop/test.csv").rdd
    val data = data1.map(t => {
      val time = t.getString(0)
      val dt = ZonedDateTime.of(time.substring(0, 4).toInt, time.substring(4, 6).toInt, time.substring(6, 8).toInt, 0, 0, 0, 0, ZoneId.systemDefault())
      Row(Timestamp.from(dt.toInstant), t.getString(1), t.getString(2).toDouble)
    })
    val schema = StructType(Seq(
      StructField("time", TimestampType, true),
      StructField("key", StringType, true),
      StructField("value", DoubleType, true)
    ))
    val df = spark.sqlContext.createDataFrame(data, schema)
    df.show()
    df
  }
}
