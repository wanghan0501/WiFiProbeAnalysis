package edu.cs.scu;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Wang Han on 2017/8/24 11:30.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. All Rights Reserved.
 *
 * @author Wang Han
 */
public class TestHadoop {
    public static void main(String args[]) throws IOException {
        String parent = "hdfs://192.168.42.1:9000";
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(parent), conf);
        FileStatus[] fs = fileSystem.listStatus(new Path(parent));
        for (FileStatus fileStatus : fs) {
            System.out.println(fileStatus.getPath());
        }
    }
}
