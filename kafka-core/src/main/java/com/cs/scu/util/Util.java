package com.cs.scu.util;

import com.cs.scu.kafka.consumer.KafkaConsumerForHive;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lch on 2017/6/24.
 */
@Component
public class Util {

    @Resource(name = "kafkaConsumerForHive")
    KafkaConsumerForHive consumerForHive;


    /**
     * 计划任务，定期将kafka的数据离线到hive中
     *cron 表达式分别表示 秒 分 时 天 周 月 年
     * */

    @Scheduled(cron = "*/30 * * * * * ")
    public void hiveTask(){
        System.err.println("😁😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😁😁😁😁定期任务！");
        consumerForHive.receive();
    }
}
