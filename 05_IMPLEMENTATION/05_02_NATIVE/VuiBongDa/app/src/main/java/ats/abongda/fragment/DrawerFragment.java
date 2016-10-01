package ats.abongda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import ats.abongda.R;
import ats.abongda.adapter.DrawerAdapter;
import ats.abongda.customClass.AnimatedExpandableListView;
import ats.abongda.fragment.base.BaseFragment;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.GroupItem;

/**
 * Created by NienLe on 02-Aug-16.
 */
public class DrawerFragment extends BaseFragment {

    private static AnimatedExpandableListView listView;
    private static DrawerAdapter adapter;
    public DrawerFragment mMainMenu;
    public ItemClickedImp callback;

    public DrawerFragment() {
    }

    public static DrawerFragment newInstance(ItemClickedImp callback) {
        DrawerFragment f = new DrawerFragment();
        f.callback = callback;
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        listView = (AnimatedExpandableListView) v.findViewById(R.id.live_page_listview);
        listView.setDividerHeight(0);
        listView.setFadingEdgeLength(0);
        adapter = new DrawerAdapter(getActivity(), listView);
        listView.setAdapter(adapter);
        listView.setGroupIndicator(null);
        mMainMenu = this;
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int i = adapter.getGroupType(groupPosition);
                if (i == 0) {
                    if (listView.isGroupExpanded(groupPosition)) {
                        listView.collapseGroupWithAnimation(groupPosition);
                    } else {
                        listView.expandGroupWithAnimation(groupPosition);
                    }
                } else {
                    int actionCode = adapter.getGroupActionCode(groupPosition);
                    callback.onItemClicked(actionCode);
                }

                return true;
            }

        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                listView.collapseGroupWithAnimation(i);
                int actionCode = adapter.getChildActionCode(i, i1);
                callback.onItemClicked(actionCode);
                return true;
            }
        });
        return v;
    }

    public interface ItemClickedImp{
        void onItemClicked(int code);
    }

    @Override
    public void loadContent() {

    }
}
