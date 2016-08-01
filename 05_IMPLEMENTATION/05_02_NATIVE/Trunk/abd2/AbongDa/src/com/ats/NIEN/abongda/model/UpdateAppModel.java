package com.ats.NIEN.abongda.model;

public class UpdateAppModel {
	private String introductionContent;
	private String updateLink;
	private int versionNumber;
	
	public UpdateAppModel(String introductionContent, String updateLink,
			int versionNumber) {
		super();
		this.introductionContent = introductionContent;
		this.updateLink = updateLink;
		this.versionNumber = versionNumber;
	}

	public String getIntroductionContent() {
		return introductionContent;
	}

	public void setIntroductionContent(String introductionContent) {
		this.introductionContent = introductionContent;
	}

	public String getUpdateLink() {
		return updateLink;
	}

	public void setUpdateLink(String updateLink) {
		this.updateLink = updateLink;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public UpdateAppModel() {
		// TODO Auto-generated constructor stub
	}

}
