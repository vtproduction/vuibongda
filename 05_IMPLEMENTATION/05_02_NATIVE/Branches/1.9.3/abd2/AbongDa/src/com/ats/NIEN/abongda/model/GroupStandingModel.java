package com.ats.NIEN.abongda.model;

import java.util.ArrayList;
import java.util.List;

public class GroupStandingModel {
	private List<ClubModel> _LStanding;
	private String _League_Name;
	public List<ClubModel> get_LStanding() {
		return _LStanding;
	}
	public void set_LStanding(List<ClubModel> _LStanding) {
		this._LStanding = _LStanding;
	}
	public String get_League_Name() {
		return _League_Name;
	}
	public void set_League_Name(String _League_Name) {
		this._League_Name = _League_Name;
	}
	public GroupStandingModel(List<ClubModel> _LStanding, String _League_Name) {
		super();
		this._LStanding = new ArrayList<ClubModel>();
		this._LStanding.addAll(_LStanding);
		this._League_Name = _League_Name;
	}
	
}
