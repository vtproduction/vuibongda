package com.ats.NIEN.abongda.fragment.FULLSCR;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.model.HopthuModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FULLSCR_HopthuChitietFragment extends BaseFragment{
	public HopthuModel model;
	public TextView mTextView;
	
	public FULLSCR_HopthuChitietFragment(HopthuModel model) {
		super();
		this.model = model;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.hopthu_chitiet_main_layout, container, false);
		mTextView = (TextView) v.findViewById(R.id.hopthuchitiet_text);
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}
	
	public Activity getRealActivity(){
		return (getActivity()==null)? mActivity : getActivity();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.HOPTHUCHITIETFRAGMENT) {
			return;
		}
		((BaseActivity) getRealActivity()).changeTitle(model.getTitle());
		((BaseActivity) getRealActivity()).changeRightImageToBack();
		mTextView.setText(model.getMessage());
	}
	
	
	
	
}
