package com.cs.scu.kafka.consumer;

import com.cs.scu.hive.HiveService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by lch on 2017/6/24.
 */
public class KafkaConsumerForHive {
    Properties properties;
    Connection con;
    Statement stmt;
    FileWriter fileWriter;
    PrintWriter printWriter;

    public KafkaConsumerForHive(Properties properties) {
        super();
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String receive() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(properties.getProperty("topic")));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
                System.err.println(buffer.size() + "----->" + record);

            }
            if (buffer.size() >= minBatchSize) {
                writeFileToHadoop(buffer);//先把buffer写入文件中
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

    private void insertIntoHive() {
        String tableName = "test";
        try {
            con = HiveService.getConn();
            stmt = HiveService.getStmt(con);

            /*for (int i = 0; i < buffer.size(); i++) {
                String data = "\"" + StringEscapeUtils.escapeJava(buffer.get(i).value()) + "\"";
                stmt.execute("insert into test(id, data) values(" + buffer.get(i).offset()+ "," + data + ")");
            }*/
            stmt.execute("load data inpath '/user/hive/output/data.dat' into table test");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public synchronized void writeFileToHadoop(List<ConsumerRecord<String, String>> buffer) {


        Configuration configuration = new Configuration();
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        try {

            FileSystem fileSystem = FileSystem.get(configuration);
            Path path = new Path("/user/hive/output/data.dat");
            FSDataOutputStream fsDataOutputStream = fileSystem.create(path);

            //fileWriter = new FileWriter(file,false);
            //printWriter = new PrintWriter(fileWriter);
            for (int i = 0; i < buffer.size(); i++) {
                str = buffer.get(i).value() + "\t" + buffer.get(i).value() + "\n";
                stringBuffer.append(str);
                //printWriter.println(buffer.get(i).value()   + "\t" + buffer.get(i).value());
            }
            fsDataOutputStream.write(stringBuffer.toString().getBytes(),0,stringBuffer.toString().getBytes().length);
            fsDataOutputStream.flush();
            fsDataOutputStream.close();
            stringBuffer.delete(0,stringBuffer.length());
            insertIntoHive();//存入hive中
            //printWriter.flush();

        } catch (IOException e) {

        }

    }

}
