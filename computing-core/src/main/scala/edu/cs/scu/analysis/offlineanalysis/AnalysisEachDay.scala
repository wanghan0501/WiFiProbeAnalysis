package edu.cs.scu.analysis.offlineanalysis

import edu.cs.scu.common.constants.AnalysisConstants
import edu.cs.scu.javautils.ParseJson
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.SparkContext

object AnalysisEachDay {
  def analysisStayTime(sc: SparkContext, conf: Configuration): Unit = {
    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    val count = hBaseRDD.count()
    hBaseRDD.foreach { case (_, result) => {
      //println(result)
      val dataList = Bytes.toString(result.getValue(AnalysisConstants.DATA_CF, AnalysisConstants.dataList))
      val macList = ParseJson.jsonToList(dataList)
      println(macList)
      val probeId = Bytes.toString(result.getValue(AnalysisConstants.PROBEINFO_CF, AnalysisConstants.probe_id))
      val mmac = Bytes.toString(result.getValue(AnalysisConstants.PROBEINFO_CF, AnalysisConstants.mmac))
      val rate = Bytes.toString(result.getValue(AnalysisConstants.PROBEINFO_CF, AnalysisConstants.rate))
      val record_time = Bytes.toString(result.getValue(AnalysisConstants.PROBEINFO_CF, AnalysisConstants.record_time))
      val wmac = Bytes.toString(result.getValue(AnalysisConstants.PROBEINFO_CF, AnalysisConstants.wssid))
      val addr = Bytes.toString(result.getValue(AnalysisConstants.ADDRESS_CF, AnalysisConstants.addr))
      val dataIterator = macList.iterator()
      while(dataIterator.hasNext){
        print(dataIterator.next().getMac)
      }
      println("RDDCOUNT:" + count)
    }
    }

  }
}
