package com.ats.NIEN.abongda.model;

public class TK_LichsuModel {
	private long afterChange;
	private long beforeChange;
	private long change;
	private String description;
	private int logType;
	private String registerDatetime;
	public long getAfterChange() {
		return afterChange;
	}
	public void setAfterChange(long afterChange) {
		this.afterChange = afterChange;
	}
	public long getBeforeChange() {
		return beforeChange;
	}
	public void setBeforeChange(long beforeChange) {
		this.beforeChange = beforeChange;
	}
	public long getChange() {
		return change;
	}
	public void setChange(long change) {
		this.change = change;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getLogType() {
		return logType;
	}
	public void setLogType(int logType) {
		this.logType = logType;
	}
	public String getRegisterDatetime() {
		return registerDatetime;
	}
	public void setRegisterDatetime(String registerDatetime) {
		this.registerDatetime = registerDatetime;
	}
	public TK_LichsuModel(long afterChange, long beforeChange, long change,
			String description, int logType, String registerDatetime) {
		super();
		this.afterChange = afterChange;
		this.beforeChange = beforeChange;
		this.change = change;
		this.description = description;
		this.logType = logType;
		this.registerDatetime = registerDatetime;
	}
	
	
}
