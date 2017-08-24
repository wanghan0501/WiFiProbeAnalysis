package edu.cs.scu.javautils.mybatisutils.handlerutils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Wang Han on 2017/4/6 22:29.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class ArrayTypeHandlerUtil extends BaseTypeHandler<Object[]> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object[] parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Object[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new Object[0];
    }

    @Override
    public Object[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new Object[0];
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new Object[0];
    }
}
