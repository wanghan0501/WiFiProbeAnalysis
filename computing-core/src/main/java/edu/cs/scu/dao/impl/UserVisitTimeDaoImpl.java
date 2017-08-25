package edu.cs.scu.dao.impl;

import edu.cs.scu.bean.UserVisitTimeBean;
import edu.cs.scu.conf.MybatisSqlSession;
import edu.cs.scu.dao.UserVisitTimeDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Wang Han on 2017/6/20 16:40.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class UserVisitTimeDaoImpl implements UserVisitTimeDao {

    // 得到log记录器
    private static final Logger logger = Logger.getLogger(UserVisitTimeDaoImpl.class);

    @Override
    public void addUserVisitTime(UserVisitTimeBean userVisitTimeBean) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            UserVisitTimeDao userVisitTimeDao = sqlSession.getMapper(UserVisitTimeDao.class);
            userVisitTimeDao.addUserVisitTime(userVisitTimeBean);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void addUserVisitTimeByBatch(List<UserVisitTimeBean> userVisitTimeBeanList) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            UserVisitTimeDao userVisitTimeDao = sqlSession.getMapper(UserVisitTimeDao.class);
            userVisitTimeDao.addUserVisitTimeByBatch(userVisitTimeBeanList);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public long getFirstVisitTIme(int shopId, String mac) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();
        long firstTime;
        try {
            UserVisitTimeDao userVisitTimeDao = sqlSession.getMapper(UserVisitTimeDao.class);
            firstTime = userVisitTimeDao.getFirstVisitTIme(shopId, mac);
        } catch (Exception e) {
            firstTime = 0L;
            logger.info("First Visit");
        } finally {
            sqlSession.close();
        }

        return firstTime;
    }
}
