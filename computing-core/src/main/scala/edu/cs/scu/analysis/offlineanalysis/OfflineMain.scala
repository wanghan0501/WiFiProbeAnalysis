package edu.cs.scu.analysis.offlineanalysis

import edu.cs.scu.common.constants.{AnalysisConstants, SparkConstants}
import edu.cs.scu.dao.impl.UserDaoImpl
import edu.cs.scu.scalautils.{GetDate, InitUtil}
import org.apache.hadoop.hbase.filter.{CompareFilter, RegexStringComparator, RowFilter}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.filter.{CompareFilter, RegexStringComparator}
import org.apache.hadoop.hbase.mapreduce.{TableInputFormat, TableMapReduceUtil}
class OfflineMain extends Runnable{
  override def run(): Unit = {
    while(true){
      OfflineMainFunc()
      Thread.sleep(60000 * 60)
    }
  }
  def OfflineMainFunc(): Unit = {
    println("enter offline")
    val spark = InitUtil.initSparkSession()
    val sc = spark.sparkContext
    val scan: Scan = new Scan()
    val conf = HBaseConfiguration.create()
    conf.set(SparkConstants.SPARK_ZOOKEEPER, SparkConstants.SPARK_ZOOKEEPER_PORT)
    conf.set(SparkConstants.SPARK_ZOOKEEPER_QUORUM, SparkConstants.SPARK_ZOOKEEPER_QUORUM_IP)
    conf.addResource(SparkConstants.SPARK_HBASE_CONF)
    conf.set(TableInputFormat.INPUT_TABLE, SparkConstants.GROUP_DATA_TABLE)
    val yesterday = GetDate.getYesterday;
    val rowRegexp = yesterday+"[0-9]{10}\\-+[0-9]{3}"
    val filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(rowRegexp))
    scan.setFilter(filter)
    val scan_str= TableMapReduceUtil.convertScanToString(scan)
    conf.set(TableInputFormat.SCAN, scan_str)
    AnalysisEachDay.analysisStayTime(sc, conf)

  }

}
