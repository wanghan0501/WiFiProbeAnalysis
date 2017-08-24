package edu.cs.scu.conf;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Reader;

/**
 * 获取mybatis-config.xml中相关配置
 * <p>
 * Created by Wang Han on 2017/4/6 13:37.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */

public class MybatisSqlSession {

    // 得到log记录器
    private static final Logger logger = Logger.getLogger(MybatisSqlSession.class);
    private static SqlSession sqlSession = null;
    private static SqlSessionFactory sqlSessionFactory = null;

    static {

        String resource = "mybatis-configuration.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    public static synchronized SqlSession getSqlSession() {
        sqlSession = sqlSessionFactory.openSession();

        return sqlSession;
    }
}
