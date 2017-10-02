package com.cs.scu.service.model;

import com.cs.scu.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class HbaseRow {
	private String rowKey;
	private List<HbaseCell> cells;
	
	public HbaseRow(String rowKey) {
		super();
		this.rowKey = rowKey;
	}
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public List<HbaseCell> getCells() {
		return cells==null?new ArrayList<HbaseCell>():cells;
	}
	public void setCells(List<HbaseCell> cells) {
		this.cells = cells;
	}
	public void addCell(HbaseCell cell) {
		if(cells==null){
			cells = new ArrayList<HbaseCell>();
		}
		//cell不为null,cell值不为null
		if(cell!=null&&StringUtil.isNotBlank(cell.getValue())){
			cells.add(cell);
		}
	}
}
