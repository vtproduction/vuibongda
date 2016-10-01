package ats.abongda.model;

import java.util.List;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class LiveModel  {
    private int _leagueID;
    private String _leagueName;
    private List<Match> _matches;

    public int get_leagueID() {
        return _leagueID;
    }

    public void set_leagueID(int _leagueID) {
        this._leagueID = _leagueID;
    }

    public String get_leagueName() {
        return _leagueName;
    }

    public void set_leagueName(String _leagueName) {
        this._leagueName = _leagueName;
    }

    public List<Match> get_matches() {
        return _matches;
    }

    public void set_matches(List<Match> _matches) {
        this._matches = _matches;
    }
}
