package ats.abongda.model;

import java.util.List;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class ClubInfo {
    private String Address;
    private Integer Capacity;
    private String Email;
    private Integer Founded;
    private String Honours;
    private Integer Id;
    private String Logo;
    private String Manager;
    private String Name;
    private String Phone;
    private String ShortName;
    private String Stadium;
    private String Web;
    private List<News> News;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getCapacity() {
        return Capacity;
    }

    public void setCapacity(Integer capacity) {
        Capacity = capacity;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getFounded() {
        return Founded;
    }

    public void setFounded(Integer founded) {
        Founded = founded;
    }

    public String getHonours() {
        return Honours;
    }

    public void setHonours(String honours) {
        Honours = honours;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public List<ats.abongda.model.News> getNews() {
        return News;
    }

    public void setNews(List<ats.abongda.model.News> news) {
        News = news;
    }
}
