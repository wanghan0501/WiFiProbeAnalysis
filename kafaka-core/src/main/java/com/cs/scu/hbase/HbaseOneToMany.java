package com.cs.scu.hbase;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//对应的field一般是TreeMap<String,List>
@Target(FIELD) 
@Retention(RUNTIME)
public @interface HbaseOneToMany {
	String splitSign();	//分隔符
	String joinField();		//关联依据的列
	Class<?> joinTableDao();	//连接表的Dao
	boolean lazy() default true;	//是否主动加载
}
