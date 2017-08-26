package edu.cs.scu.testconf;

import edu.cs.scu.conf.JedisPoolManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by Wang Han on 2017/8/26 10:41.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. All Rights Reserved.
 *
 * @author Wang Han
 */
public class TestSharedRedis {

    @Before
    public void before() {
        JedisPoolManager.setSharedJedisPool();
    }

    @After
    public void after() {
        JedisPoolManager.destroyPool();
    }

    @Test
    public void useJedisPool() {
        ShardedJedis shardedJedis = JedisPoolManager.getResource();
        shardedJedis.set("Wang", "CAESAR");
        shardedJedis.pexpire("Wang", 1000 * 10);
    }

}
