package com.ats.NIEN.abongda.model;

import java.util.ArrayList;
import java.util.List;

public class FragmentModel {
	public int actionCode;
	public List<Object> argsList;
	public String tag;
	public boolean isInTab;
	
	public FragmentModel(int actionCode, List<Object> argsList, boolean isInTab) {
		super();
		this.actionCode = actionCode;
		this.argsList = new ArrayList<Object>();
		if (argsList != null) {
			this.argsList.addAll(argsList);
		}
		this.isInTab = isInTab;
	}
	
	public FragmentModel(int actionCode, List<Object> argsList, String tag,
			boolean isInTab) {
		super();
		this.actionCode = actionCode;
		this.argsList = new ArrayList<Object>();
		if (argsList != null) {
			this.argsList.addAll(argsList);
		}
		this.tag = tag;
		this.isInTab = isInTab;
	}

	public FragmentModel(int actionCode, List<Object> argsList, String tag) {
		super();
		this.actionCode = actionCode;
		this.argsList = new ArrayList<Object>();
		if (argsList != null) {
			this.argsList.addAll(argsList);
		}
		this.tag = tag;
	}
	public FragmentModel(int actionCode, List<Object> argsList) {
		super();
		this.actionCode = actionCode;
		this.argsList = new ArrayList<Object>();
		if (argsList != null) {
			this.argsList.addAll(argsList);
		}
		this.tag = this.actionCode + "";
	}
	public FragmentModel(int actionCode) {
		super();
		this.actionCode = actionCode;
		this.argsList = new ArrayList<Object>();
		this.tag = this.actionCode + "";
	}
	
	
	
	
}
