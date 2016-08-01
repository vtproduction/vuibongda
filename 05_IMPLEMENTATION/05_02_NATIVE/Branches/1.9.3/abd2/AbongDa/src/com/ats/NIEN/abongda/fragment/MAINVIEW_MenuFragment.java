package com.ats.NIEN.abongda.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.ats.NIEN.abongda.UTIL.AnimatedExpandableListView;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.adapter.MenuAdapter;
import com.ats.NIEN.abongda.adapter.MenuAdapter.GroupItem;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;


public class MAINVIEW_MenuFragment extends SherlockFragment{
    private static AnimatedExpandableListView listView;
    private static MenuAdapter adapter;	
    public MAINVIEW_MenuFragment mMainMenu;
    List<GroupItem> items = new ArrayList<GroupItem>();

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View v = inflater.inflate(R.layout.activity_menu, container, false);
        listView = (AnimatedExpandableListView) v.findViewById(R.id.live_page_listview);
        listView.setDividerHeight(0);
        listView.setFadingEdgeLength(0);
		adapter = new MenuAdapter(getSherlockActivity(), this, listView);
        listView.setAdapter(adapter);
        listView.setGroupIndicator(null);
        mMainMenu = this;
        listView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group 
                // expansion/collapse.
            	int i = adapter.getGroupType(groupPosition);
            	if (i==0) {
            		if (listView.isGroupExpanded(groupPosition)) {
                        listView.collapseGroupWithAnimation(groupPosition);
                    } else {
                        listView.expandGroupWithAnimation(groupPosition);
                    }
				}else{
					int actionCode = adapter.getGroupActionCode(groupPosition);
					
					adapter.switchFrag(actionCode);
				}
                
                return true;
            }
            
        });
		return v;
		
	}
	
	/*@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Fragment mFragment = null;
		
		super.onListItemClick(l, v, position, id);
		String string = list_contents[position];
		Log.d("tag1", string);
		mFragment = new MAINVIEW_MainFragment(string);
		switchFragment(mFragment);
		
	}*/
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		//setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_contents));

		
	}
	
	
	
	
	public void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		} 
	}

}
