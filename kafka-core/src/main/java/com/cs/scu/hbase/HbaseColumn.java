package com.cs.scu.hbase;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD) 
@Retention(RUNTIME)
public @interface HbaseColumn {
	String family();
	String qualifier() default "";
}
