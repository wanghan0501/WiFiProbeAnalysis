package edu.cs.scu.dao;

import java.util.List;

/**
 * Created by Wang Han on 2017/8/26 11:02.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. All Rights Reserved.
 *
 * @author Wang Han
 */
public abstract class BaseDao {

    public abstract void add(List<Object> objectList);

    public abstract Object get(String key);

    public abstract List<Object> get(List<String> keys);
}
