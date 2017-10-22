# WIFI Probe Analysis (Web Design)
基于WIFI探针的商业大数据分析技术(数据缓存后端)

简介：
本程序是WIFI探针商业大数据分析的数据缓存服务器部分，主要功能为格式化探针上传的数据，将
格式化后的数据下发至分布式订阅系统kafka用于分布式集群的在线分析，同时也将数据缓存到数据
仓库Hive中，用于离线分析，机器学习等。

项目目录结构：
```
|________________________________________________________
com.cs.scu 
|
|--controller
|
|--entity
|
|--hbase
|
|--kafka
|   |
|   |--consumer
|   |
|   |--producer
|
|--mapper
|
|--service
|   |
|   |-impl
|
|--tools
|________________________________________________________
```

核心类：

数据上传接口
com.cs.scu.controller.DataUploadController

Json数据处理实体类
com.cs.scu.entity.Data
com.cs.scu.entity.DataGroup

Hive数据仓库工具类
com.cs.scu.hive.HBaseService

Kafka分布式消息订阅系统消费者
com.cs.scu.kafka.consumer.KafkaConsumerForHive
com.cs.scu.kafka.consumer.KafkaConsumers

Kafka分布式消息订阅系统生产者
com.cs.scu.kafka.producer.KafkaProducerForHive
com.cs.scu.kafka.producer.KafkaProducers
com.cs.scu.kafka.producer.KafkaMesConstant

工具类
com.cs.scu.tools.Util



主要算法流程：

数据上传接口接受到探针上报数据后，将上传的json数据进行格式化处理（包括转码，
补缺缺失字段（探针没有探测到的数据字段会默认无，此目的是为了节省流量））。然后通过
调用Kafka消息生产者send()函数，将数据push到消息队列中。
系统会通过调用工具类Util中的hiveTask()函数执行计划任务，启动kafka消费者，将原始
数据存入hive中。此外集群可以通过调用此消费者，或者自己实现消费者消费消息队列中的数据。


>[赛题链接](http://www.cnsoftbei.com/bencandy.php?fid=148&aid=1515)

