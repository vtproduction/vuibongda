package com.ats.NIEN.abongda.model;

import java.util.List;


public class ClubInforModel {
	private String Address;
	private int Capacity;
	private String Email;
	private int Founded;
	private String Honours;
	private int Id;
	private String Logo;
	private String Manager;
	private String Nane;
	private List<TintucModel> News;
	private String Phone;
	private String ShortName;
	private String Stadium;
	private String Web;
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getCapacity() {
		return Capacity;
	}
	public void setCapacity(int capacity) {
		Capacity = capacity;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getFounded() {
		return Founded;
	}
	public void setFounded(int founded) {
		Founded = founded;
	}
	public String getHonours() {
		return Honours;
	}
	public void setHonours(String honours) {
		Honours = honours;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public String getManager() {
		return Manager;
	}
	public void setManager(String manager) {
		Manager = manager;
	}
	public String getNane() {
		return Nane;
	}
	public void setNane(String nane) {
		Nane = nane;
	}
	public List<TintucModel> getNews() {
		return News;
	}
	public void setNews(List<TintucModel> news) {
		News = news;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getShortName() {
		return ShortName;
	}
	public void setShortName(String shortName) {
		ShortName = shortName;
	}
	public String getStadium() {
		return Stadium;
	}
	public void setStadium(String stadium) {
		Stadium = stadium;
	}
	public String getWeb() {
		return Web;
	}
	public void setWeb(String web) {
		Web = web;
	}
	public ClubInforModel(String address, int capacity, String email,
			int founded, String honours, int id, String logo, String manager,
			String nane, List<TintucModel> news, String phone,
			String shortName, String stadium, String web) {
		super();
		Address = address;
		Capacity = capacity;
		Email = email;
		Founded = founded;
		Honours = honours;
		Id = id;
		Logo = logo;
		Manager = manager;
		Nane = nane;
		News = news;
		Phone = phone;
		ShortName = shortName;
		Stadium = stadium;
		Web = web;
	}
	public ClubInforModel() {
		super();
	}
	
	
}
