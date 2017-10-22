package com.cs.scu.service.model;

public class HbaseCell {

	private HbaseColCouple colCouple;
	private String value;
	
	public HbaseCell(String family, String qualifier, String value) {
		super();
		colCouple = new HbaseColCouple(family, qualifier);
		this.value = value;
	}
	public String getFamily() {
		return colCouple.getFamily();
	}
	public void setFamily(String family) {
		colCouple.setFamily(family);
	}
	public String getQualifier() {
		return colCouple.getQualifier();
	}
	public void setQualifier(String qualifier) {
		colCouple.setQualifier(qualifier);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public HbaseColCouple getColCouple() {
		return colCouple;
	}
	public void setColCouple(HbaseColCouple colCouple) {
		this.colCouple = colCouple;
	}
}
