package edu.cs.scu.dao.impl;

import edu.cs.scu.bean.VendorMacBean;
import edu.cs.scu.conf.MybatisSqlSession;
import edu.cs.scu.dao.VendorMacDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Wang Han on 2017/6/19 16:44.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class VendorMacDaoImpl implements VendorMacDao {
    // 得到log记录器
    private static final Logger logger = Logger.getLogger(VendorMacDaoImpl.class);

    @Override
    public void addVendorMac(VendorMacBean vendorMacBean) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            VendorMacDao vendorMacDao = sqlSession.getMapper(VendorMacDao.class);
            vendorMacDao.addVendorMac(vendorMacBean);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void addVendorMacBatch(List<VendorMacBean> vendorMacBeanList) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            VendorMacDao vendorMacDao = sqlSession.getMapper(VendorMacDao.class);
            vendorMacDao.addVendorMacBatch(vendorMacBeanList);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public VendorMacBean getVendorByMac(String mac) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();
        VendorMacBean vendorMacBean = new VendorMacBean();

        try {
            VendorMacDao vendorMacDao = sqlSession.getMapper(VendorMacDao.class);
            vendorMacBean = vendorMacDao.getVendorByMac(mac);
        } catch (Exception e) {
            vendorMacBean = null;
        } finally {
            sqlSession.close();
        }

        return vendorMacBean;
    }
}
