package edu.cs.scu.javautils;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

/**
 * json对象解析类
 * <p>
 * Created by Wang Han on 2017/4/6 22:30.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class JsonUtil {

    // 得到log记录器
    private static final Logger logger = Logger.getLogger(JsonUtil.class);

    /**
     * 构造含有object属性的json字符串
     *
     * @param object
     * @return
     */
    public static synchronized String stringify(Object object) {

        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    /**
     * 解析json字符串，返回T类型实例
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static synchronized <T> T parse(String json, Class<T> clazz) {

        if (json == null || json.length() == 0) {
            return null;
        }

        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return null;
    }

}
