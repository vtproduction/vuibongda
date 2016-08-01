package com.ats.NIEN.abongda.model;

import java.io.Serializable;
import java.util.List;


public class CauthuModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3865863509742836091L;
	private String Birthday;
	private int ClubId;
	private String ClubLogo;
	private String DateOfBirth;
	private int Goal;
	private String Height;
	private int Id;
	private String Image;
	private int Match;
	private String Name;
	private String Nationality;
	private List<TintucModel> News;
	private int Number;
	private String Position;
	private int RedCard;
	private String Weigt;
	private int YellowCard;
	private int Role;
	private int TimeIn;
	private int TimeOut;
	
	public int getRole() {
		return Role;
	}
	public void setRole(int role) {
		Role = role;
	}
	public int getTimeIn() {
		return TimeIn;
	}
	public void setTimeIn(int timeIn) {
		TimeIn = timeIn;
	}
	public int getTimeOut() {
		return TimeOut;
	}
	public void setTimeOut(int timeOut) {
		TimeOut = timeOut;
	}
	public String getBirthday() {
		return Birthday;
	}
	public void setBirthday(String birthday) {
		Birthday = birthday;
	}
	public int getClubId() {
		return ClubId;
	}
	public void setClubId(int clubId) {
		ClubId = clubId;
	}
	public String getClubLogo() {
		return ClubLogo;
	}
	public void setClubLogo(String clubLogo) {
		ClubLogo = clubLogo;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public int getGoal() {
		return Goal;
	}
	public void setGoal(int goal) {
		Goal = goal;
	}
	public String getHeight() {
		return Height;
	}
	public void setHeight(String height) {
		Height = height;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public int getMatch() {
		return Match;
	}
	public void setMatch(int match) {
		Match = match;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getNationality() {
		return Nationality;
	}
	public void setNationality(String nationality) {
		Nationality = nationality;
	}
	public List<TintucModel> getNews() {
		return News;
	}
	public void setNews(List<TintucModel> news) {
		News = news;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public int getRedCard() {
		return RedCard;
	}
	public void setRedCard(int redCard) {
		RedCard = redCard;
	}
	public String getWeigt() {
		return Weigt;
	}
	public void setWeigt(String weigt) {
		Weigt = weigt;
	}
	public int getYellowCard() {
		return YellowCard;
	}
	public void setYellowCard(int yellowCard) {
		YellowCard = yellowCard;
	}
	
	public CauthuModel(String birthday, int clubId, String clubLogo,
			String dateOfBirth, int goal, String height, int id, String image,
			int match, String name, String nationality, List<TintucModel> news,
			int number, String position, int redCard, String weigt,
			int yellowCard) {
		super();
		Birthday = birthday;
		ClubId = clubId;
		ClubLogo = clubLogo;
		DateOfBirth = dateOfBirth;
		Goal = goal;
		Height = height;
		Id = id;
		Image = image;
		Match = match;
		Name = name;
		Nationality = nationality;
		News = news;
		Number = number;
		Position = position;
		RedCard = redCard;
		Weigt = weigt;
		YellowCard = yellowCard;
	}
	public CauthuModel(String birthday, int clubId, String clubLogo,
			String dateOfBirth, int goal, String height, int id, String image,
			int match, String name, String nationality, int number,
			String position, int redCard, String weigt, int yellowCard) {
		super();
		Birthday = birthday;
		ClubId = clubId;
		ClubLogo = clubLogo;
		DateOfBirth = dateOfBirth;
		Goal = goal;
		Height = height;
		Id = id;
		Image = image;
		Match = match;
		Name = name;
		Nationality = nationality;
		Number = number;
		Position = position;
		RedCard = redCard;
		Weigt = weigt;
		YellowCard = yellowCard;
	}
	public CauthuModel(String clubLogo, int goal, String name) {
		super();
		ClubLogo = clubLogo;
		Goal = goal;
		Name = name;
	}
	public CauthuModel(int id, String image, String name, String position) {
		super();
		Id = id;
		Image = image;
		Name = name;
		Position = position;
	}
	public CauthuModel() {
		super();
	}
	public CauthuModel(int clubId, int goal, String name, int number,
			int redCard, int yellowCard, int role, int timeIn, int timeOut) {
		super();
		ClubId = clubId;
		Goal = goal;
		Name = name;
		Number = number;
		RedCard = redCard;
		YellowCard = yellowCard;
		Role = role;
		TimeIn = timeIn;
		TimeOut = timeOut;
	}
	
	
	
}
