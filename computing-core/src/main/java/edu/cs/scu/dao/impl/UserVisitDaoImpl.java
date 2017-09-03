package edu.cs.scu.dao.impl;

import com.alibaba.fastjson.JSON;
import edu.cs.scu.bean.UserVisitBean;
import edu.cs.scu.conf.JedisPoolManager;
import edu.cs.scu.dao.BaseDao;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Created by Wang Han on 2017/6/18 15:18.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class UserVisitDaoImpl extends BaseDao {

    @Override
    public void add(List<Object> objectList) {
        ShardedJedis jedis = JedisPoolManager.getResource();
        Map<String, List<String>> map = new HashMap<>();
        for (Object o : objectList) {
            UserVisitBean userVisitBean = (UserVisitBean) o;
            String key = String.valueOf(userVisitBean.getShopId()) + "||"
                + userVisitBean.getMmac();
            if (map.containsKey(key)) {
                List<String> values = map.get(key);
                values.add(JSON.toJSONString(userVisitBean));
                map.put(key, values);
            } else {
                List<String> values = new ArrayList<>();
                values.add(JSON.toJSONString(userVisitBean));
                map.put(key, values);
            }
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            jedis.rpush(entry.getKey(), entry.getValue().toArray(new String[0]));
        }
        jedis.close();
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public List<Object> get(List<String> keys) {
        return null;
    }
}
