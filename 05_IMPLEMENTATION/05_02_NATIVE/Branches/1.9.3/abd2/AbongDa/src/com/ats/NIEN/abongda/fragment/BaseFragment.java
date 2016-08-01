package com.ats.NIEN.abongda.fragment;

import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment{
	public Activity mActivity;
	public SharedPreferences mSharePreferences;
	public SharedPreferences.Editor mEditor;
	public int mActionCode;
	public JSONParser mJsonParser;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}
	
	public Activity getRealActivity() {
		return (getActivity() == null) ? mActivity : getActivity();
	}
	
	public BaseActivity getBaseActivity(){
		return (BaseActivity) getRealActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.mSharePreferences = getRealActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		this.mEditor = mSharePreferences.edit();
		this.mJsonParser = new JSONParser(getRealActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void refreshContent(){
		
	}
	
	public void loadFromBackStack(){
		
	}
	
	public void netAsync(){
		
	}
	
	public void saveModel(Object myObject, String header){
		Gson gson = new Gson();
		String jsonString = gson.toJson(myObject);
		mEditor.putString(header, jsonString);
		mEditor.commit();
		Log.d("BF", "saved obj: " + jsonString);
	}
}
