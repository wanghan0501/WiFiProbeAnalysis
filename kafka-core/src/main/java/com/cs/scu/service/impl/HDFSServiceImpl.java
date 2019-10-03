package com.cs.scu.service.impl;

import com.cs.scu.service.HDFSService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * author: maicius
 * date: 2019/2/2
 * description:
 */
@Service
public class HDFSServiceImpl implements HDFSService{
    private FileSystem fs;
    private String ip;

    public void InitHDFSWriter(String ip) {
        if(this.fs == null){
            this.ip = ip;
            System.out.println("start initial filesystem.ip is "+ip);
            init(ip);
        }
    }

    private void init(String ip) {
        // 读取classpath下的xxx-site.xml 配置文件，并解析其内容，封装到conf对象中
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", ip);

        // 根据配置信息，去获取一个具体文件系统的客户端操作实例对象
        try {
            fs = FileSystem.get(new URI(ip), conf, "hadoop");
        } catch (Exception e) {
            System.out.println("hdfs init fail!"+e);
        }
    }

    public void write(String msg) {
        long now = System.currentTimeMillis();
        try {
            String filename = ip+"/source/"+now+".txt";
            Path dst = new Path(filename);
            FSDataOutputStream os = fs.create(dst);
            os.write(msg.getBytes(), 0, msg.getBytes().length);
            os.flush();
            os.close();
            System.out.println("写入hdfs成功:" + msg);

        } catch (Exception e) {
            System.out.println("write hdfs file failure:"+e);
        }

    }
}
