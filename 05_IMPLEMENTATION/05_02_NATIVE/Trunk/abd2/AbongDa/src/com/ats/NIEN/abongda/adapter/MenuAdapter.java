package com.ats.NIEN.abongda.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.AnimatedExpandableListView;
import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.MenuData;
import com.ats.NIEN.abongda.UTIL.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MenuFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_HopthuFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_TopcaothuFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_TopdaigiaFragment;
import com.ats.NIEN.abongda.model.MenuModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class MenuAdapter extends AnimatedExpandableListAdapter implements Constant {
    private LayoutInflater inflater;
    private static MAINVIEW_MenuFragment fragment;
    public static MenuAdapter menuAdapter;
    public static AnimatedExpandableListView listView;
    public SharedPreferences sharePreferences;
    private List<GroupItem> items;
    private ArrayList<MenuModel> models;
    public Context context;
    
    @SuppressWarnings("static-access")
	public MenuAdapter(Context context, MAINVIEW_MenuFragment fragment, AnimatedExpandableListView listView) {
         inflater = LayoutInflater.from(context);
         this.fragment = fragment;
         menuAdapter = this;
         this.listView = listView;
         this.context = context;
         this.models = MenuData.menuData;
    }

    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        //ChildItem item = getChild(groupPosition, childPosition);
        final MenuModel model = models.get(groupPosition).getChilds().get(childPosition);
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.menu_sub_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.menusubitem_text);
            holder.logo = (ImageView) convertView.findViewById(R.id.menusubitem_img);
            //holder.hint = (TextView) convertView.findViewById(R.id.textHint);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        
        holder.title.setText(model.getTitle());
        holder.logo.setImageResource(model.getImageId());
        //holder.hint.setText(item.hint);
        convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int count = menuAdapter.getGroupCount();
				for (int i = 0; i <count ; i++)
					 listView.collapseGroup(i);
				int actionCode = model.getActionCode();
					MAINVIEW_MainFragment mMainView = new MAINVIEW_MainFragment(actionCode);
					Log.d("Menu Log", "Current ActionCode: " + actionCode);
					
					switch (actionCode) {
					case LEAGUE_PL_CODE:
					case LEAGUE_LFP_CODE:
					case LEAGUE_SA_CODE:
					case LEAGUE_BDL_CODE:
					case LEAGUE_L1_CODE:
					case LEAGUE_CL_CODE:
					case LEAGUE_EL_CODE:
						sharePreferences = context.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
						SharedPreferences.Editor   editor = sharePreferences.edit();
						editor.putInt("CURRENT_LEAGUE_ID", actionCode);
						editor.putInt("LTD_SM2", 0);
						editor.putInt("LTD_SS", 0);
						editor.putInt("XH_SG", 0);
						editor.putInt("XH_SS", 0);
						editor.putInt("TK_SS", 0);
						editor.commit();
						editor.commit();
						
						break;
					default:
						
						break;
					}
					fragment.switchFragment(mMainView);
				

						
			}
		});
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return models.get(groupPosition).getChilds().size();
    }

    @Override
    public GroupItem getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return models.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public int getGroupActionCode(int groupPosition){
    	return models.get(groupPosition).getActionCode();
    }
    @Override
	public int getGroupType(int groupPosition) {
		// TODO Auto-generated method stub
    	if (models.get(groupPosition).getActionCode() == Constant.MENUGROUP_CODE) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getGroupTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        MenuModel model = models.get(groupPosition);
        int groupType = getGroupType(groupPosition);
        switch (groupType) {
		case 0:
	        if (convertView == null) {
	            holder = new GroupHolder();
	            convertView = inflater.inflate(R.layout.menu_item, parent, false);
	            holder.title = (TextView) convertView.findViewById(R.id.menuitem_text);
	            holder.logo = (ImageView) convertView.findViewById(R.id.menuitem_img);
	            holder.indicator = (ImageView) convertView.findViewById(R.id.menuitem_indicator);
	            convertView.setTag(holder);
	        } else {
	            holder = (GroupHolder) convertView.getTag();
	        }
	        if (isExpanded) {
				holder.indicator.setImageResource(R.drawable.ic_action_expand);
			}else{
				holder.indicator.setImageResource(R.drawable.ic_action_collapse);
			}
	        holder.title.setText(model.getTitle());
	        holder.logo.setImageResource(model.getImageId());
			break;
		case 1:
	        if (convertView == null) {
	            holder = new GroupHolder();
	            convertView = inflater.inflate(R.layout.menu_item, parent, false);
	            holder.title = (TextView) convertView.findViewById(R.id.menuitem_text);
	            holder.logo = (ImageView) convertView.findViewById(R.id.menuitem_img);
	            holder.indicator = (ImageView) convertView.findViewById(R.id.menuitem_indicator);
	            convertView.setTag(holder);
	        } else {
	            holder = (GroupHolder) convertView.getTag();
	        }
	        holder.indicator.setVisibility(View.INVISIBLE);
	        holder.title.setText(model.getTitle());
	        holder.logo.setImageResource(model.getImageId());
			break;
		default:
			break;
		}

        

        
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
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
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }
    
    
    public static class GroupItem {
        public String title;
    	public ImageView logo;
    	public ImageView indicator;
        public List<ChildItem> items = new ArrayList<ChildItem>();
    }
    
    public static class ChildItem {
    	public String title;
    	public ImageView logo;
    	public int actionCode;
    }
    
    public static class ChildHolder {
    	public TextView title;
    	public ImageView logo;
    	public int actionCode;
    }
    
    public static class GroupHolder {
    	public TextView title;
    	public ImageView logo;
    	public ImageView indicator;
    }

	public void switchFrag(int actionCode) {
		// TODO Auto-generated method stub
		MAINVIEW_MainFragment mMainView = new MAINVIEW_MainFragment(actionCode);
		fragment.switchFragment(mMainView);
	}

}
