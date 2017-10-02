package com.cs.scu.service.model;

import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;

public class HbaseServiceConditonModel {
	private String family;
	private String col;
	private String value;
	private CompareOp op;
	
	public HbaseServiceConditonModel(String family, String col, String value,
			CompareOp op) {
		super();
		this.family = family;
		this.col = col;
		this.value = value;
		this.op = op;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
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
