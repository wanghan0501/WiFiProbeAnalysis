package edu.cs.scu.conf;

import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Wang Han on 2017/8/26 10:20.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. All Rights Reserved.
 *
 * @author Wang Han
 */
public class JedisPoolManager {
    private static Properties properties = new Properties();
    private static final Logger logger = Logger.getLogger(ConfigurationManager.class);
    private static boolean isSetup = false;

    static {
        InputStream in = ConfigurationManager.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("Jedis Pool Setup Error");
            logger.error("Jedis Pool Setup Error");
        }
    }

    private static ShardedJedisPool pool;

    public static void setSharedJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();//Jedis池配置
        // 共享jedis池，用于集群
        List<JedisShardInfo> jedisShardInfos = new ArrayList<>();
        JedisShardInfo node01 = new JedisShardInfo("localhost", 6379);
        node01.setPassword("110110");
        jedisShardInfos.add(node01);
        pool = new ShardedJedisPool(config, jedisShardInfos);
        isSetup = true;
    }

    public static ShardedJedis getResource() {
        if (!isSetup) {
            isSetup = true;
            setSharedJedisPool();
        }
        return pool.getResource();
    }


    public static void destroyPool() {
        isSetup = false;
        pool.destroy();
    }
}
