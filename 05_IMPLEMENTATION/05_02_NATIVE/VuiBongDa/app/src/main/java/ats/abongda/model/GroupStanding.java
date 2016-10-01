package ats.abongda.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class GroupStanding {
    private List<Club> _LStanding;
    private String _League_Name;
    public List<Club> get_LStanding() {
        return _LStanding;
    }
    public void set_LStanding(List<Club> _LStanding) {
        this._LStanding = _LStanding;
    }
    public String get_League_Name() {
        return _League_Name;
    }
    public void set_League_Name(String _League_Name) {
        this._League_Name = _League_Name;
    }
    public GroupStanding(List<Club> _LStanding, String _League_Name) {
        super();
        this._LStanding = new ArrayList<Club>();
        this._LStanding.addAll(_LStanding);
        this._League_Name = _League_Name;
    }
}
