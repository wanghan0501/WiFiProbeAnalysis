package com.cs.scu.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by lch on 2017/6/18.
 */
@Component
public class KafkaConsumers {

    private Properties properties;


    public KafkaConsumers(Properties properties) {
        super();
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public List<String> receive() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(properties.getProperty("topic")));
        List<String> buffer = new ArrayList<String>();
        String msg = "";
        while (true) {
            System.err.println("consumer receive------------------");
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record.value());
            }
            consumer.close();
            return buffer;
        }


    }
}
