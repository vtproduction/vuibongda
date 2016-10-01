package ats.abongda.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by NienLe on 06-Aug-16.
 */
@Parcel
public class Player {
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
    private List<News> News;
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
    public List<News> getNews() {
        return News;
    }
    public void setNews(List<News> news) {
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
}
