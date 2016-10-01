package ats.abongda.model;

import java.util.List;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class Next24hModel {
    private String day;
    private List<Match> matchList;

    public Next24hModel(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }
}
