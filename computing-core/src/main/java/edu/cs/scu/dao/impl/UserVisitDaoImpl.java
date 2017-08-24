package edu.cs.scu.dao.impl;

import edu.cs.scu.bean.UserVisitBean;
import edu.cs.scu.conf.MybatisSqlSession;
import edu.cs.scu.dao.UserVisitDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Wang Han on 2017/6/18 15:19.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class UserVisitDaoImpl implements UserVisitDao {
    // 得到log记录器
    private static final Logger logger = Logger.getLogger(UserVisitDaoImpl.class);

    @Override
    public void addUserVisit(UserVisitBean userVisitBean) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            UserVisitDao userVisitDao = sqlSession.getMapper(UserVisitDao.class);
            userVisitDao.addUserVisit(userVisitBean);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void addUserVisitByBatch(List<UserVisitBean> userVisitBeanList) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            UserVisitDao userVisitDao = sqlSession.getMapper(UserVisitDao.class);
            userVisitDao.addUserVisitByBatch(userVisitBeanList);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }
}
