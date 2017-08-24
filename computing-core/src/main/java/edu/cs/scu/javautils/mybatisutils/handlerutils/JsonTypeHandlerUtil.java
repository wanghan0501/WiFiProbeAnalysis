package edu.cs.scu.javautils.mybatisutils.handlerutils;

import edu.cs.scu.javautils.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 继承自BaseTypeHandler<Object> 使用Object是为了让JsonUtil可以处理任意类型
 * <p>
 * Created by Wang Han on 2017/4/6 22:28.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */


public class JsonTypeHandlerUtil extends BaseTypeHandler<Object> {

    // 得到log记录器
    private static final Logger logger = Logger.getLogger(JsonTypeHandlerUtil.class);

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            return JsonUtil.parse(rs.getString(columnName), Object.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            return JsonUtil.parse(rs.getString(columnIndex), Object.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            return JsonUtil.parse(cs.getString(columnIndex), Object.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setObject(i, JsonUtil.stringify(parameter));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

    }
}
