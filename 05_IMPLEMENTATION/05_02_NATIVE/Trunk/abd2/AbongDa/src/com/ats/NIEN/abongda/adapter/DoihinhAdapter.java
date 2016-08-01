package com.ats.NIEN.abongda.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ats.NIEN.abongda.UTIL.IMAGELOADER_ImageLoader;
import com.ats.NIEN.abongda.model.CauthuModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DoihinhAdapter extends ArrayAdapter<CauthuModel>{
	public Context mContext;
	public List<CauthuModel> model;
	public IMAGELOADER_ImageLoader iMAGELOADER_ImageLoader;
	private LayoutInflater layoutInflater;
	
	public DoihinhAdapter(Context context, 
			List<CauthuModel> objects) {
		super(context,0, objects);
		this.model = new ArrayList<CauthuModel>();
		this.model.addAll(objects);
		this.mContext = context;
		this.iMAGELOADER_ImageLoader = new IMAGELOADER_ImageLoader(context);
		this.layoutInflater = LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public CauthuModel getItem(int position) {
		// TODO Auto-generated method stub
		return model.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.doihinh_list_item,null);
			holder = new Holder();
			holder.image = (ImageView) convertView.findViewById(R.id.TKHS_email_cf);
			holder.name = (TextView) convertView.findViewById(R.id.doihinh_item_name);
			holder.pos = (TextView) convertView.findViewById(R.id.doihinh_item_pos);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
			
		}
		CauthuModel model = getItem(position);
		holder.name.setText(model.getName());
		holder.pos.setText(model.getPosition());
		iMAGELOADER_ImageLoader.DisplayImage(model.getImage().replaceAll(" ", "%20"), holder.image);
		return convertView;
	}
	
	private class Holder{
		ImageView image;
		TextView name;
		TextView pos;
	}
	
}
