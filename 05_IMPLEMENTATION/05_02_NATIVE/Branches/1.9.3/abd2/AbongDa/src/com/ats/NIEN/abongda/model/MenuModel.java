package com.ats.NIEN.abongda.model;

import java.util.ArrayList;


public class MenuModel {
	private String title;
	private int imageId;
	private int actionCode;
	private ArrayList<MenuModel> childs;
	
	public MenuModel(String title, int imageId, int actionCode,
			ArrayList<MenuModel> childs) {
		super();
		this.title = title;
		this.imageId = imageId;
		this.actionCode = actionCode;
		this.childs = childs;
	}
	public MenuModel(String title, int imageId, int actionCode) {
		super();
		this.title = title;
		this.imageId = imageId;
		this.actionCode = actionCode;
		this.childs = null;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}
	public ArrayList<MenuModel> getChilds() {
		return childs;
	}
	public void setChilds(ArrayList<MenuModel> childs) {
		this.childs = childs;
	}
	
	
	
}
