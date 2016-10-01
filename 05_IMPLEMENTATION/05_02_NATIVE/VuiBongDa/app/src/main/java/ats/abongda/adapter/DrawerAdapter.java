package ats.abongda.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.view.menu.MenuAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ats.abongda.R;
import ats.abongda.customClass.AnimatedExpandableListView;
import ats.abongda.fragment.DrawerFragment;
import ats.abongda.helper.Constant;
import ats.abongda.helper.MenuData;
import ats.abongda.listener.ListItemClickImp;
import ats.abongda.model.GroupItem;
import ats.abongda.model.MenuModel;

/**
 * Created by NienLe on 02-Aug-16.
 */
public class DrawerAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    public static DrawerAdapter menuAdapter;
    public static AnimatedExpandableListView listView;
    private List<GroupItem> items;
    private ArrayList<MenuModel> models;
    public Context context;

    public DrawerAdapter(Context context,  AnimatedExpandableListView listView) {
        inflater = LayoutInflater.from(context);
        menuAdapter = this;
        this.listView = listView;
        this.context = context;
        this.models = MenuData.menuData;
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
    public ChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public int getGroupType(int groupPosition) {
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
    public View getRealChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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
        return convertView;
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

    public int getGroupActionCode(int groupPosition){
        try {
            return models.get(groupPosition).getActionCode();
        }catch (Exception e){
            return Constant.ERRORCODE;
        }
    }

    public int getChildActionCode(int groupPosition, int childPosition){
        try {
            return models.get(groupPosition).getChilds().get(childPosition).getActionCode();
        }catch (Exception e){
            return Constant.ERRORCODE;
        }
    }
}
