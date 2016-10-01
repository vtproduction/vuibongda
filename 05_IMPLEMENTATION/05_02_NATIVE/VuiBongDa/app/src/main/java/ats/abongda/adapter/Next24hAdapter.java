package ats.abongda.adapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ats.abongda.R;
import ats.abongda.customClass.AnimatedExpandableListView;
import ats.abongda.model.Match;
import ats.abongda.model.Next24hModel;

/**
 * Created by NienLe on 07-Aug-16.
 */
public class Next24hAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private List<Next24hModel> models = new ArrayList<Next24hModel>();
    private List<Match> dataList = new ArrayList<Match>();
    public Context context;
    private LayoutInflater layoutInflater;
    public static AnimatedExpandableListView listView;




    public Next24hAdapter(List<Match> dataList,
                           Context context, AnimatedExpandableListView listView) {
        super();
        this.dataList = dataList;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        generateModel();

    }

    public void generateModel(){
        Next24hModel today = new Next24hModel(context.getString(R.string.hom_nay));
        Next24hModel tomorrow = new Next24hModel(context.getString(R.string.ngay_mai));
        List<Match> todayMatchList = new ArrayList<Match>();
        List<Match> tomorrowMatchList = new ArrayList<Match>();
        for(int i = 0; i < dataList.size(); i++){
            Match match = dataList.get(i);
            if (match.getMatchStartDayInWeekEn().equalsIgnoreCase(getToday())) {
                todayMatchList.add(match);
            }else{
                tomorrowMatchList.add(match);
            }
        }
        today.setMatchList(todayMatchList);
        tomorrow.setMatchList(tomorrowMatchList);
        if (todayMatchList.size() > 0) {
            models.add(today);
        }
        if (tomorrowMatchList.size() > 0) {
            models.add(tomorrow);
        }
    }

    public String getToday(){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String weekDay = "";
        if (Calendar.MONDAY == dayOfWeek) weekDay = "mon";
        else if (Calendar.TUESDAY == dayOfWeek) weekDay = "tue";
        else if (Calendar.WEDNESDAY == dayOfWeek) weekDay = "wed";
        else if (Calendar.THURSDAY == dayOfWeek) weekDay = "thu";
        else if (Calendar.FRIDAY == dayOfWeek) weekDay = "fri";
        else if (Calendar.SATURDAY == dayOfWeek) weekDay = "sat";
        else if (Calendar.SUNDAY == dayOfWeek) weekDay = "sun";
        return weekDay;
    }
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return models.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return models.get(groupPosition);
    }

    @Override
    public Match getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return models.get(groupPosition).getMatchList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Next24hModel model = (Next24hModel) getGroup(groupPosition);
        GroupHolder holder;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = (RelativeLayout) layoutInflater.inflate(R.layout.next24_page_group_item, parent, false);
            holder.dayName = (TextView) convertView.findViewById(R.id.next24Page_group_dayname);
            holder.leftLayout = (LinearLayout) convertView.findViewById(R.id.next24Page_group_leftlayout);
            holder.indicator = (ImageView) convertView.findViewById(R.id.next24Page_group_indicator);
            convertView.setTag(holder);

        }else{
            holder = (GroupHolder) convertView.getTag();
        }
        if (isExpanded) {
            holder.indicator.setImageResource(R.drawable.ic_action_expand_dark);
        }else{
            holder.indicator.setImageResource(R.drawable.ic_action_collapse_dark);
        }

        if (model.getDay().equalsIgnoreCase("hôm nay")) {
            holder.dayName.setText(model.getDay());
            holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l3));
        }else{
            holder.dayName.setText(model.getDay());
            holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l5));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Match model = (Match) getChild(groupPosition, childPosition);
        ChildHolder holder;
        switch (getRealChildType(groupPosition, childPosition)) {
            case 0:
                if(convertView == null){
                    holder = new ChildHolder();
                    convertView = (RelativeLayout) layoutInflater.inflate(R.layout.next24_page_child_item_nobet, parent, false);
                    holder.matchStartTime = (TextView) convertView.findViewById(R.id.next24page_child_matchTime);
                    holder.leagueName = (ImageView) convertView.findViewById(R.id.next24Page_child_logo);
                    holder.hostName = (TextView) convertView.findViewById(R.id.next24Page_child_host);
                    holder.guestName = (TextView) convertView.findViewById(R.id.next24Page_child_guest);
                    holder.tvChanel = (TextView) convertView.findViewById(R.id.next24Page_child_tvChanel);
                    convertView.setTag(holder);
                }else{
                    holder = (ChildHolder) convertView.getTag();
                }
                holder.matchStartTime.setText(model.getMatchStartTimeWithoutPlus());
                holder.leagueName.setImageResource(model.getLeagueLogoFromMatch());
                holder.hostName.setText(model.get_home());
                holder.guestName.setText(model.get_guest());
                holder.tvChanel.setText(model.get_tvChanel());
                break;
            case 1:
                if(convertView == null){
                    holder = new ChildHolder();
                    convertView = (RelativeLayout) layoutInflater.inflate(R.layout.next24_page_child_item, parent, false);
                    holder.matchStartTime = (TextView) convertView.findViewById(R.id.next24page_child_matchTime);
                    holder.leagueName = (ImageView) convertView.findViewById(R.id.next24Page_child_logo);
                    holder.hostName = (TextView) convertView.findViewById(R.id.next24Page_child_host);
                    holder.guestName = (TextView) convertView.findViewById(R.id.next24Page_child_guest);
                    holder.tvChanel = (TextView) convertView.findViewById(R.id.next24Page_child_tvChanel);
                    convertView.setTag(holder);
                }else{
                    holder = (ChildHolder) convertView.getTag();
                }
                holder.matchStartTime.setText(model.getMatchStartTimeWithoutPlus());
                holder.leagueName.setImageResource(model.getLeagueLogoFromMatch());
                holder.hostName.setText(model.get_home());
                holder.guestName.setText(model.get_guest());
                holder.tvChanel.setText(model.get_tvChanel());
                break;

            default:
                break;
        }
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return models.get(groupPosition).getMatchList().size();
    }

    @Override
    public int getRealChildType(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        final Match model = (Match) getChild(groupPosition, childPosition);
        if (model.get_isBet()==1) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getRealChildTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    public static class GroupHolder{
        public LinearLayout leftLayout;
        public TextView dayName;
        public ImageView indicator;
    }

    public static class ChildHolder{
        public TextView matchStartTime;
        public ImageView leagueName;
        public TextView hostName;
        public TextView guestName;
        public TextView tvChanel;
        public ImageView isBetWatermark;
    }
}
