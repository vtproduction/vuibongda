package com.ats.NIEN.abongda.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ats.NIEN.abongda.UTIL.AnimatedExpandableListView;
import com.ats.NIEN.abongda.UTIL.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import com.ats.NIEN.abongda.model.LiveModel;
import com.ats.NIEN.abongda.model.MatchModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LIVE_Adapter extends AnimatedExpandableListAdapter{
	public List<LiveModel> models;
	public Context context;
	private LayoutInflater layoutInflater;
	public static AnimatedExpandableListView listView;
	public static List<String> tagList;
	
		
	@SuppressWarnings("static-access")
	public LIVE_Adapter(List<LiveModel> models, Context context, AnimatedExpandableListView listView) {
		super();
		layoutInflater = LayoutInflater.from(context);
		this.models = new ArrayList<LiveModel>();
		this.models = models;
		this.context = context;
		this.listView = listView;
		tagList = new ArrayList<String>();
		setHeader();

	}


	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		
		return models.size();
	}


	@Override
	public LiveModel getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return models.get(groupPosition);
	}


	@Override
	public MatchModel getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		
		return models.get(groupPosition).getMatch().get(childPosition);
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
		return true;
	}
	
	@Override
	public int getRealChildTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	
	@Override
	public int getRealChildType(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if (getChild(groupPosition, childPosition).isHeaderNeeded()) {
			return 0;
		}
		return 1;
		//return super.getRealChildType(groupPosition, childPosition);
	}

    @Override
	public void onGroupCollapsed(int groupPosition) {
		// TODO Auto-generated method stub
    	
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		// TODO Auto-generated method stub
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public int getGroupType(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
	
	@Override
	public int getGroupTypeCount() {
		// TODO Auto-generated method stub
		return models.size();
	}

	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		GroupHolder holder;
		if(convertView == null){
			holder = new GroupHolder();
			convertView = (RelativeLayout) layoutInflater.inflate(R.layout.live_page_group_item, parent, false);
			holder.leftLayout = (LinearLayout) convertView.findViewById(R.id.livePage_group_leftLayout);
			holder.logo = (ImageView) convertView.findViewById(R.id.livePage_group_leagueIcon);
			holder.leagueName = (TextView) convertView.findViewById(R.id.livePage_group_leagueName);
			holder.indicator = (ImageView) convertView.findViewById(R.id.livePage_group_Indicator);
			convertView.setTag(holder);
		}else{
			holder = (GroupHolder) convertView.getTag();
		}
		if (isExpanded) {
			holder.indicator.setImageResource(R.drawable.ic_action_expand_dark);
		}else{
			holder.indicator.setImageResource(R.drawable.ic_action_collapse_dark);
		}

		int leagueId = getGroup(getGroupType(groupPosition)).getLeagueId();
		if (leagueId < 6) {
			switch (leagueId) {
			case 1:
				holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l0));
				holder.logo.setImageResource(R.drawable.league_pl);
				holder.leagueName.setText("Premier League");
				break;
			case 2:
				holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l1));
				holder.logo.setImageResource(R.drawable.league_lfp);
				holder.leagueName.setText("Primera Liga");
				break;
			case 3:
				holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l2));
				holder.logo.setImageResource(R.drawable.league_seriea);
				holder.leagueName.setText("Serie A");
				break;
			case 4:
				holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l3));
				holder.logo.setImageResource(R.drawable.league_bundesliga);
				holder.leagueName.setText("Bundesliga");
				break;
			case 5:
				holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l4));
				holder.logo.setImageResource(R.drawable.league_l1);
				holder.leagueName.setText("League One");
				break;

			default:
				holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l7));
				holder.logo.setImageResource(R.drawable.league_other);
				holder.leagueName.setText("Giải đấu khác");
				break;
			}
		}
		else if(leagueId > 5 && leagueId <= 23){
			holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l5));
			holder.logo.setImageResource(R.drawable.league_cl);
			holder.leagueName.setText("Champion League");
		}
		else if(leagueId > 23 && leagueId <= 46){
			holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l6));
			holder.logo.setImageResource(R.drawable.league_el);
			holder.leagueName.setText("Europa League");
		}
		else if(leagueId > 46 && leagueId <= 62){
			holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l0));
			holder.logo.setImageResource(R.drawable.league_fa);
			holder.leagueName.setText("FA");
		}else{
			holder.leftLayout.setBackgroundColor(context.getResources().getColor(R.color.l7));
			holder.logo.setImageResource(R.drawable.league_other);
			holder.leagueName.setText("Giải đấu khác");
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
		MatchModel model = (MatchModel) getChild(groupPosition, childPosition);
		ChildHolder childHolder;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.live_page_child_item, parent, false);
			childHolder = new ChildHolder();
			childHolder.matchStartTime = (TextView) convertView.findViewById(R.id.livePage_child_matchDateTime);
			childHolder.matchTime = (TextView) convertView.findViewById(R.id.livePage_child_matchTime);
			
			childHolder.hostName = (TextView) convertView.findViewById(R.id.livePage_child_host);
			childHolder.guestName = (TextView) convertView.findViewById(R.id.livePage_child_guest);
			
			childHolder.hostGoal = (TextView) convertView.findViewById(R.id.livePage_child_hostGoal);
			childHolder.guestGoal = (TextView) convertView.findViewById(R.id.livePage_child_guestGoal);
			
			childHolder.hostPenalty = (ImageView) convertView.findViewById(R.id.livePage_child_hostStar);
			childHolder.guestPenalty = (ImageView) convertView.findViewById(R.id.livePage_child_guestStar);
			
			childHolder.headerLeft = (TextView) convertView.findViewById(R.id.livePage_child_header_leftText);
			childHolder.headerRight = (TextView) convertView.findViewById(R.id.livePage_child_header_rightText);
			childHolder.header = (RelativeLayout) convertView.findViewById(R.id.livePage_child_header);
			convertView.setTag(childHolder);
			
		}else{
			childHolder = (ChildHolder) convertView.getTag();
		}
		childHolder.matchStartTime.setText(model.getMatchStartTimeWithoutPlus());
		childHolder.matchTime.setText(model.getMatchMinute());
		childHolder.hostName.setText(model.getHome());
		childHolder.guestName.setText(model.getGuest());
		//Log.d("tag4", "get child: " + childPosition + " group: " + groupPosition + " - " + tagList.size());
		int type = getRealChildType(groupPosition, childPosition);
		if (type == 0) {
			childHolder.header.setVisibility(View.VISIBLE);
			childHolder.headerLeft.setText(model.getMatchStartDayInWeekVi());
			childHolder.headerRight.setText(model.getMatchStartDate());
			
		}else{
			childHolder.header.setVisibility(View.GONE);
		}

		if (model.getHomePenaltyShoot() == 0) {
			childHolder.hostPenalty.setVisibility(View.INVISIBLE);
		}else{
			childHolder.hostPenalty.setVisibility(View.VISIBLE);
		}
		if (model.getGuestPenaltyShoot() == 0) {
			childHolder.guestPenalty.setVisibility(View.INVISIBLE);
		}else{
			childHolder.guestPenalty.setVisibility(View.VISIBLE);
		}
		
		if (model.getHomeGoal() == 0 && model.getMatchStatus() == 0) {
			childHolder.hostGoal.setText("-");
		}else{
			childHolder.hostGoal.setText(model.getHomeGoal() + "");
		}
		
		if (model.getGuestGoal() == 0 && model.getMatchStatus() == 0) {
			childHolder.guestGoal.setText("-");
		}else{
			childHolder.guestGoal.setText(model.getGuestGoal() + "");
		}
		
		
		return convertView;
	}


	@Override
	public int getRealChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return models.get(groupPosition).getMatch().size();
	}

	public boolean isDatePresented(String date){
		for(int i = 0; i < tagList.size(); i++){
			if (date.equals(tagList.get(i))) {
				return true;
			}
		}
		return false;
	}

	
    
    public static class ChildHolder {
    	public TextView matchStartTime;
    	public TextView hostName;
    	public TextView guestName;
    	public TextView hostGoal;
    	public TextView guestGoal;
    	public TextView matchTime;
    	public TextView headerLeft;
    	public TextView headerRight;
    	public RelativeLayout header;
    	public ImageView hostPenalty;
    	public ImageView guestPenalty;
    }
    
    public static class GroupHolder {
        public LinearLayout leftLayout;
    	public ImageView logo;
    	public ImageView indicator;
        public TextView leagueName;
    }
    
    public void setHeader(){
    	for (int i = 0; i < models.size(); i++) {
			List<MatchModel> matchList = getGroup(i).getMatch();
			for (int j = 0; j < matchList.size(); j++) {
				String matchDate = matchList.get(j).getMatchStartDayInWeekVi();
				if (isDatePresented(matchDate + models.get(i).getLeagueId())) {
					matchList.get(j).setHeaderNeeded(false);
				}else{
					matchList.get(j).setHeaderNeeded(true);
					tagList.add(matchDate + models.get(i).getLeagueId());
				}
				
			}
		}
    	
    	/*for (String abc : tagList) {
			Log.d("liveadapter", abc);
		}*/
    }

}
