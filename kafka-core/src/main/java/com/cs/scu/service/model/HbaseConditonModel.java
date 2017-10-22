package com.cs.scu.service.model;

import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;

public class HbaseConditonModel {
	private String field;
	private String value;
	private CompareOp op;

	public HbaseConditonModel(String field, String value, CompareOp op) {
		super();
		this.field = field;
		this.value = value;
		this.op = op;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public CompareOp getOp() {
		return op;
	}
	public void setOp(CompareOp op) {
		this.op = op;
	}
	
}
