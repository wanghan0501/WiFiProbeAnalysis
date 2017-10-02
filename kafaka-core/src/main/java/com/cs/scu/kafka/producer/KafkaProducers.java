package com.cs.scu.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by lch on 2017/6/18.
 */
@Component
public class KafkaProducers {
    Properties properties;

    public KafkaProducers(Properties properties) {
        super();
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    public void sendMessage(String msg) {
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String,String> record = new ProducerRecord<String, String>(properties.getProperty("topic"),msg);
        producer.send(record);
        producer.close();
    }

}
