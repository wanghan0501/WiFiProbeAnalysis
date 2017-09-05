package edu.cs.scu.dao.impl;

import edu.cs.scu.bean.PropertyBean;
import edu.cs.scu.conf.MybatisSqlSession;
import edu.cs.scu.dao.PropertyDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * Created by Wang Han on 2017/6/18 19:20.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class PropertyDaoImpl implements PropertyDao {
    // 得到log记录器
    private static final Logger logger = Logger.getLogger(PropertyDaoImpl.class);

    @Override
    public PropertyBean getPropertyById(int id) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();
        PropertyBean propertyBean = new PropertyBean();

        try {
            PropertyDao propertyDao = sqlSession.getMapper(PropertyDao.class);
            propertyBean = propertyDao.getPropertyById(id);
        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }

        return propertyBean;
    }

    @Override
    public PropertyBean getNewProperty() {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();
        PropertyBean propertyBean = new PropertyBean();
        try {
            PropertyDao propertyDao = sqlSession.getMapper(PropertyDao.class);
            propertyBean = propertyDao.getNewProperty();
        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }

        return propertyBean;
    }

    @Override
    public void setyPropertyNotUse(PropertyBean propertyBean) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();
        try {
            PropertyDao propertyDao = sqlSession.getMapper(PropertyDao.class);
            propertyDao.setyPropertyNotUse(propertyBean);
            sqlSession.commit();
        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void setPropertyUse(PropertyBean propertyBean) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();
        try {
            PropertyDao propertyDao = sqlSession.getMapper(PropertyDao.class);
            propertyDao.setPropertyUse(propertyBean);
            sqlSession.commit();
        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public boolean isUse(int shopId, String mmac) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();
        boolean isUse = false;
        try {
            PropertyDao propertyDao = sqlSession.getMapper(PropertyDao.class);
            isUse = propertyDao.isUse(shopId, mmac);
        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
        return isUse;
    }
}
