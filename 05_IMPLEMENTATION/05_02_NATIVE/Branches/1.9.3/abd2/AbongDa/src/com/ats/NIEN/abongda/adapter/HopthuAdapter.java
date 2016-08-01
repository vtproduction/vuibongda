package com.ats.NIEN.abongda.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ats.NIEN.abongda.model.HopthuModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HopthuAdapter extends ArrayAdapter<HopthuModel>{

	public List<HopthuModel> models;
	public Context context;
	public LayoutInflater layoutInflater;
	public HopthuAdapter(Context context, int textViewResourceId,
			List<HopthuModel> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		models = new ArrayList<HopthuModel>();
		models.addAll(objects);
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return models.size();
	}
	@Override
	public HopthuModel getItem(int position) {
		// TODO Auto-generated method stub
		return models.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int viewType = getItemViewType(position);
		HopthuModel model = getItem(position);
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.hopthu_item, null);
			holder.mDate = (TextView) convertView.findViewById(R.id.hopthu_textDate);
			holder.mTitle = (TextView) convertView.findViewById(R.id.hopthu_textTitle);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mDate.setText(model.getRegisterDatetime());
		holder.mTitle.setText(model.getTitle());
		if (viewType == 1) {
			holder.mDate.setTypeface(null, Typeface.BOLD);
			holder.mTitle.setTypeface(null, Typeface.BOLD);
		}
		return convertView;
	}
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (getItem(position).getStatus() == 5) {
			return 1;
		}
		return 0;
	}
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	class ViewHolder{
		public TextView mDate;
		public TextView mTitle;
	}

}
