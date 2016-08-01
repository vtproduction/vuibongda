package com.ats.NIEN.abongda.model;

public class TintucModel {
	private String content;
	private String description;
	private int id;
	private String imageLink;
	private int isHot;
	private String title;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public int getIsHot() {
		return isHot;
	}
	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public TintucModel(String content, String description, int id,
			String imageLink, int isHot, String title) {
		super();
		this.content = content;
		this.description = description;
		this.id = id;
		this.imageLink = imageLink;
		this.isHot = isHot;
		this.title = title;
	}
	
	
}
