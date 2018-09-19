package com.cs.scu.util;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件（*.properties）工具类
 * 
 */
public class PropertiesUtil {
	
	public static boolean setProperty(String key, String value) {
		ClassPathResource resource = new ClassPathResource("hbase.properties");
		Properties pros = new Properties();
		try {
			pros.load(resource.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			String errorMessage = "hbase.properties文件不存在！";
			System.out.println(errorMessage);
			return false;
		}
		pros.setProperty(key, value);
		String tempValue = pros.getProperty(key);
		if(value.equals(tempValue))
			return true;
		else
			return false;
	}
	
	/**
	 * 获取值
	 * @return
	 */
	public static String getProperty(String propertyName){
		ClassPathResource resource = new ClassPathResource("hbase.properties");
		Properties pros = new Properties();
		try {
			pros.load(resource.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			String errorMessage = "hbase.properties文件不存在！";
			System.out.println(errorMessage);
		}
		String propertyValue = pros.getProperty(propertyName);
		return propertyValue;
	}
	
	/**
	 * 获取Hbase地址
	 * @return
	 */
	public static String getHbaseIp(){
		return getProperty("hbaseIp");
	}
	
	/**
	 * 获取Hbase文件夹
	 * @return
	 */
	public static String getHbaseDir(){
		return getProperty("hbaseDir");
	}
	
	/**
	 * 获取Zookeeper地址
	 * @return
	 */
	public static String getZookeeperIp(){
		return getProperty("zookeeperIp");
	}
	
	/**
	 * 获取Zookeeper端口
	 * @return
	 */
	public static String getZookeeperPort(){
		return getProperty("zookeeperPort");
	}
}
