package edu.cs.scu.bi

import com.cloudera.sparkts.TimeSeriesRDD
import com.cloudera.sparkts.models.{ARIMA, HoltWinters}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Wang Han on 2017/9/2 18:02.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. All Rights Reserved.
  *
  * @author Wang Han
  */
class TimeSeriesModel {
  //预测后面N个值
  private var predictedN = 1


  def this(predictedN: Int) {
    this()
    this.predictedN = predictedN
  }

  /**
    * Arima模型：
    * 输出其p，d，q参数
    * 输出其预测的predictedN个值
    *
    * @param trainTsrdd
    */
  def arimaModelTrain(trainTsrdd: TimeSeriesRDD[String]): RDD[String] = {
    // 参数设置
    val predictedN = this.predictedN

    // 创建和训练arima模型.其RDD格式为(ArimaModel,Vector)
    val arimaAndVectorRdd = trainTsrdd.map {
      case (key, denseVector) =>
        (ARIMA.autoFit(denseVector), denseVector)
    }

    // 参数输出:p,d,q的实际值和其系数值
    val coefficients = arimaAndVectorRdd.map {
      case (arimaModel, denseVector) => {
        (arimaModel.coefficients.mkString(","),
          (arimaModel.p,
            arimaModel.d,
            arimaModel.q))
      }
    }
    coefficients.collect().foreach {
      case (coefficients, (p, d, q)) =>
        println("coefficients:" + coefficients + "=>" + "(p=" + p + ",d=" + d + ",q=" + q + ")")
    }

    // 预测出后N个的值
    val forecast = arimaAndVectorRdd.map {
      case (arimaModel, denseVector) => {
        arimaModel.forecast(denseVector, predictedN)
      }
    }
    val forecastValue = forecast.map(_.toArray.mkString(","))

    // 取出预测值
    val preditcedValueRdd = forecastValue.map { parts =>
      val partArray = parts.split(",")
      for (i <- partArray.length - predictedN until partArray.length) yield partArray(i)
    }.map(_.toArray.mkString(","))
    preditcedValueRdd.collect().foreach { row =>
      println("forecast of next " + predictedN + " observations: " + row)
    }
    preditcedValueRdd
  }

  /**
    * 实现HoltWinters模型
    *
    * @param trainTsrdd
    */
  def holtWintersModelTrain(trainTsrdd: TimeSeriesRDD[String], period: Int, holtWintersModelType: String): RDD[String] = {
    // 参数设置
    // 往后预测多少个值
    val predictedN = this.predictedN

    // 创建和训练HoltWinters模型.其RDD格式为(HoltWintersModel,Vector)
    val holtWintersAndVectorRdd = trainTsrdd.map {
      case (key, denseVector) =>
        (HoltWinters.fitModel(denseVector, period, holtWintersModelType), denseVector)
    }

    // 构成N个预测值向量，之后导入到holtWinters的forcast方法中
    val predictedArrayBuffer = new ArrayBuffer[Double]()
    for (i <- 0 until predictedN) {
      predictedArrayBuffer += i
    }
    val predictedVectors = Vectors.dense(predictedArrayBuffer.toArray)

    // 预测
    val forecast = holtWintersAndVectorRdd.map {
      case (holtWintersModel, denseVector) => {
        holtWintersModel.forecast(denseVector, predictedVectors)
      }
    }
    val forecastValue = forecast.map(_.toArray.mkString(","))
    forecastValue.collect().foreach { row =>
      println("HoltWinters forecast of next " + predictedN + " observations: " + row)
    }
    forecastValue
  }
}
