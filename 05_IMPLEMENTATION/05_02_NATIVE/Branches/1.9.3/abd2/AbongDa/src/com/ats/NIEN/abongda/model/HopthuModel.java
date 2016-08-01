package com.ats.NIEN.abongda.model;

public class HopthuModel {
	private long id;
	private String message;
	private String registerDatetime;
	private int status;
	private String title;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRegisterDatetime() {
		return registerDatetime;
	}
	public void setRegisterDatetime(String registerDatetime) {
		this.registerDatetime = registerDatetime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public HopthuModel(long id, String message, String registerDatetime,
			int status, String title) {
		super();
		this.id = id;
		this.message = message;
		this.registerDatetime = registerDatetime;
		this.status = status;
		this.title = title;
	}
	
	
}
