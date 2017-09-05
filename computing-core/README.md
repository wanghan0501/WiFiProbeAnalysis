# Computing-Core 
基于WIFI探针的商业大数据分析技术(计算集群)

>[赛题链接](http://www.cnsoftbei.com/bencandy.php?fid=148&aid=1515)


## Dependency Version

* Hadoop: 2.7.4
* HBase: 1.3.0
* Spark: 2.2.0
* Scala: 2.11.11

## Submit Parameters

```bash
$SPARK_HOME/bin/spark-submit \
--master spark://node-01:7077 \
--class edu.cs.scu.analysis.Main \
--executor-memory 500M \
--executor-cores 1 \
./computing-core-0.0.1.jar
```
