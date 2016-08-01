package com.ats.NIEN.abongda.model;

import java.util.ArrayList;
import java.util.List;

public class LeagueSeason {
	private int isCurrent;
	private List<MonthData> monthData;
	private int rowId;
	private String seasonName;
	public int getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}
	public List<MonthData> getMonthData() {
		return monthData;
	}
	public void setMonthData(List<MonthData> monthData) {
		this.monthData = monthData;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getSeasonName() {
		return seasonName;
	}
	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}
	public LeagueSeason(int isCurrent, List<MonthData> monthData, int rowId,
			String seasonName) {
		super();
		this.isCurrent = isCurrent;
		this.monthData = new ArrayList<MonthData>();
		this.monthData.addAll(monthData);
		
		this.rowId = rowId;
		this.seasonName = seasonName;
	}
	
	public LeagueSeason(int isCurrent, int rowId,
			String seasonName) {
		super();
		this.isCurrent = isCurrent;
		this.monthData = new ArrayList<MonthData>();
		
		this.rowId = rowId;
		this.seasonName = seasonName;
	}
	
}
