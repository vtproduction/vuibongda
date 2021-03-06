package com.ats.NIEN.abongda.model;

public class MonthData {
	private int isCurrent;
	private int month;
	private String name;
	private int year;
	public int getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public MonthData(int isCurrent, int month, String name, int year) {
		super();
		this.isCurrent = isCurrent;
		this.month = month;
		this.name = name;
		this.year = year;
	}
	public String monthAsString(){
		return "Th�ng " + this.month;
	}
}
