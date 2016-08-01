package com.ats.NIEN.abongda.fragment.FULLSCR;

import org.json.JSONException;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.CustomAlertDialog;
import com.ats.NIEN.abongda.UTIL.DeviceUtil;
import com.ats.NIEN.abongda.UTIL.GeneralConfig;
import com.ats.NIEN.abongda.UTIL.IMAGELOADER_ImageLoader;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.model.FullMatchModel;
import com.ats.NIEN.abongda.model.UserModel;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class FULLSCR_DatCuocFragment extends BaseFragment implements OnClickListener {
	public int betType;
	public FullMatchModel model;
	public View v;
	public IMAGELOADER_ImageLoader iMAGELOADER_ImageLoader;
	public UserModel user;
	public Button betBtn, betAllBtn;
	public EditText betText;
	public Spinner mSpinner1, mSpinner2;
	public String betRes;
	int minimumGoalBetValue, minimumNormalBetValue;
	String[] betArray = {"0","1","2","3","4","5","6","7","8","9","10"};
	public int userId, matchId;
	public FULLSCR_DatCuocFragment(int betType, FullMatchModel model) {
		super();
		this.betType = betType;
		this.model = model;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		userId = this.mSharePreferences.getInt("CURRENT_USER_ID", 0);
		matchId = this.mSharePreferences.getInt("MATCHID", 0);
		
		if (betType != 7) {
			v = inflater.inflate(R.layout.datcuoc_main_layout, container, false);
			((BaseActivity) getActivity()).changeRightImageToBack();
			this.betBtn = (Button) v.findViewById(R.id.datcuocpage_datcuocbtn);
			this.betAllBtn = (Button) v.findViewById(R.id.datcuocpage_dattattaybtn);
			this.betText = (EditText) v.findViewById(R.id.datcuocpage_soxudat);
		}else{
			v = inflater.inflate(R.layout.datcuoc_main_layout_2, container, false);
			((BaseActivity) getActivity()).changeRightImageToBack();
			this.betBtn = (Button) v.findViewById(R.id.datcuocpage2_datcuocbtn);
			this.betAllBtn = (Button) v.findViewById(R.id.datcuocpage2_dattattaybtn);
			this.betText = (EditText) v.findViewById(R.id.datcuocpage2_soxudat);
			this.mSpinner1 = (Spinner) v.findViewById(R.id.datcuocpage2_spinner1);
			this.mSpinner2 = (Spinner) v.findViewById(R.id.datcuocpage2_spinner2);
		}
		mSharePreferences = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		/*
		 * SharedPreferences.Editor edit = mSharePreferences.edit();
		 * edit.putBoolean("MATCH_AHEAD", true); edit.commit();
		 */

		new DeviceUtil(getActivity()).getAllSharePref();
		this.iMAGELOADER_ImageLoader = new IMAGELOADER_ImageLoader(getActivity());
		this.user = new JSONParser(getActivity()).getUser(mSharePreferences.getInt(
				"CURRENT_USER_ID", 0));
		GeneralConfig mConFig = new GeneralConfig(mSharePreferences.getString("GENERAL_CONFIG", ""));
		this.minimumGoalBetValue = mConFig.get_minimumGoalBetValue();
		this.minimumNormalBetValue = mConFig.get_minimumNormalBetValue();
		
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.DATCUOCFRAGMENT) {
			return;
		}
		((BaseActivity) getActivity()).changeTitle("Đặt cược");
		((BaseActivity) getActivity()).changeRightImageToBack();
		if (betType < 7) {
			setUpDCPage1();
		}else{
			setUpDCPage2();
		}

	}
	
	private void setUpDCPage2() {
		// TODO Auto-generated method stub
		iMAGELOADER_ImageLoader.DisplayImage("http://cdn.ats.vn/upload/football/club/"
				+ model.get_homeLogo(),
				(ImageView) v.findViewById(R.id.datcuocpage2_hostimg));
		iMAGELOADER_ImageLoader.DisplayImage("http://cdn.ats.vn/upload/football/club/"
				+ model.get_guestLogo(),
				(ImageView) v.findViewById(R.id.datcuocpage2_guestimg));
		try {
			((TextView) v.findViewById(R.id.datcuocpage2_tiledat)).setText((new JSONParser(getActivity()).getGeneralConfig("6x16")).getString("_goalBet"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((TextView) v.findViewById(R.id.datcuocpage2_taikhoan))
		.setText(mSharePreferences.getString("CURRENT_USER_NAME", ""));
		((TextView) v.findViewById(R.id.datcuocpage2_soxu)).setText(user
		.getAccount() + " xu");
		((TextView) v.findViewById(R.id.datcuocpage2_hostname)).setText(model
				.get_home());
		((TextView) v.findViewById(R.id.datcuocpage2_guestname)).setText(model
				.get_guest());
		ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.lichthidau_page_spinner_layout,
				betArray);
		mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<String> mSpinnerAdapter2 = new ArrayAdapter<String>(
				getActivity(), R.layout.lichthidau_page_spinner_layout,
				betArray);
		mSpinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner1.setAdapter(mSpinnerAdapter);
		mSpinner2.setAdapter(mSpinnerAdapter);
		betBtn.setOnClickListener(null);
		betAllBtn.setOnClickListener(this);
		this.betText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				changebtnState();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				changebtnState();

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				changebtnState();
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void setUpDCPage1(){
		iMAGELOADER_ImageLoader.DisplayImage("http://cdn.ats.vn/upload/football/club/"
				+ model.get_homeLogo(),
				(ImageView) v.findViewById(R.id.datcuocpage_hostimg));
		iMAGELOADER_ImageLoader.DisplayImage("http://cdn.ats.vn/upload/football/club/"
				+ model.get_guestLogo(),
				(ImageView) v.findViewById(R.id.datcuocpage_guestimg));
		((TextView) v.findViewById(R.id.datcuocpage_hostname)).setText(model
				.get_home());
		((TextView) v.findViewById(R.id.datcuocpage_guestname)).setText(model
				.get_guest());
		((TextView) v.findViewById(R.id.datcuocpage_taikhoan))
				.setText(mSharePreferences.getString("CURRENT_USER_NAME", ""));
		((TextView) v.findViewById(R.id.datcuocpage_soxu)).setText(user
				.getAccount() + " xu");
		switch (betType) {
		case 1:
			((TextView) v.findViewById(R.id.datcuocpage_cuadat)).setText(model
					.get_home() + " thắng");
			((TextView) v.findViewById(R.id.datcuocpage_loaitile))
					.setText("Châu Âu");
			((TextView) v.findViewById(R.id.datcuocpage_tiledat)).setText(model
					.get_homeWin1x2());
			break;
		case 2:
			((TextView) v.findViewById(R.id.datcuocpage_cuadat))
					.setText("Hai đội hòa");
			((TextView) v.findViewById(R.id.datcuocpage_loaitile))
					.setText("Châu Âu");
			((TextView) v.findViewById(R.id.datcuocpage_tiledat)).setText(model
					.get_draw1x2());
			break;
		case 3:
			((TextView) v.findViewById(R.id.datcuocpage_cuadat)).setText(model
					.get_guest() + " thắng");
			((TextView) v.findViewById(R.id.datcuocpage_loaitile))
					.setText("Châu Âu");
			((TextView) v.findViewById(R.id.datcuocpage_tiledat)).setText(model
					.get_guestWin1x2());
			break;
		case 4:
			((TextView) v.findViewById(R.id.datcuocpage_cuadat)).setText(model
					.get_home() + " thắng");
			((TextView) v.findViewById(R.id.datcuocpage_loaitile))
					.setText("Châu Á (" + model.get_handicap() + ")");
			((TextView) v.findViewById(R.id.datcuocpage_tiledat)).setText(model
					.get_homeWinHandicap());
			break;
		case 6:
			((TextView) v.findViewById(R.id.datcuocpage_cuadat)).setText(model
					.get_home() + " thắng");
			((TextView) v.findViewById(R.id.datcuocpage_loaitile))
					.setText("Châu Á (" + model.get_handicap() + ")");
			((TextView) v.findViewById(R.id.datcuocpage_tiledat)).setText(model
					.get_guestWinHandicap());
			break;

		default:
			break;
		}
		this.betBtn.setOnClickListener(null);
		if (user.getAccount() == 0) {
			this.betAllBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_btn_item_shape2));
			this.betAllBtn.setOnClickListener(null);
		} else {
			this.betAllBtn.setOnClickListener(this);
		}
		this.betText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				changebtnState();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				changebtnState();

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				changebtnState();
			}
		});
	}

	public String getCuaDat() {
		switch (betType) {
		case 1:
			return model.get_home() + " thắng";
		case 2:
			return "Hai đội hòa";
		case 3:
			return model.get_guest() + " thắng";

		case 4:
			return model.get_home() + " thắng";

		case 6:
			return model.get_guest() + " thắng";

		default:
			return "";
		}
	}

	@SuppressWarnings("deprecation")
	public void changebtnState() {
		this.betBtn.setOnClickListener(null);
		String xu = betText.getText().toString();
		if (xu.length() == 0) {
			this.betBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_btn_item_shape2));
			this.betBtn.setOnClickListener(null);
		} else {
			long xuInt = Long.parseLong(xu);
			if (xuInt <= user.getAccount()) {
				this.betBtn.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.custom_btn_item_shape));
				this.betBtn.setOnClickListener(this);
			} else {
				this.betBtn.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.custom_btn_item_shape2));
				this.betBtn.setOnClickListener(null);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.datcuocpage_datcuocbtn:
			final long xu = Long.parseLong(betText.getText().toString());
			if (xu < minimumNormalBetValue) {
				new CustomAlertDialog(getActivity(), "Lỗi",
						"Bạn phải đặt tối thiểu "+minimumNormalBetValue+" xu !", "OK").show();
			} else {
				new customConfirmDialog(getRealActivity()){
					@Override
					public void performOk() {
						// TODO Auto-generated method stub
						super.performOk();
						String betRes = performBet(xu + "");
						if (betRes.equals("1")) {
							new CustomAlertDialog(getActivity(), "Đặt cược",
									"Bạn đã đặt cược " + xu + "xu cho " + getCuaDat(),
									"OK") {

								@Override
								public void act() {
									// TODO Auto-generated method stub
									super.act();
									//((BaseActivity) getActivity()).onBackPressed();
								}

							}.show();
						} else {
							new CustomAlertDialog(getActivity(), "Lỗi",
									"Có lỗi xảy ra\nVui lòng thử lại sau !", "OK") {

								@Override
								public void act() {
									// TODO Auto-generated method stub
									super.act();
									//((BaseActivity) getActivity()).onBackPressed();
								}

							}.show();
						}
						
					}
				}.show();
				
			}
			break;
		case R.id.datcuocpage2_datcuocbtn:
			final long xu2 = Long.parseLong(betText.getText().toString());
			if (xu2 < minimumGoalBetValue) {
				new CustomAlertDialog(getActivity(), "Lỗi",
						"Bạn phải đặt tối thiểu "+minimumGoalBetValue+" xu !", "OK").show();
			} else {
				new customConfirmDialog(getRealActivity()){
					@Override
					public void performOk() {
						// TODO Auto-generated method stub
						super.performOk();
						String betRes = performBet2(xu2 + "");
						if (betRes.equals("1")) {
							new CustomAlertDialog(getActivity(), "Đặt cược",
									"Bạn đã đặt cược " + xu2 + "xu với tỉ số " + model.get_home()
									+ " " + mSpinner1.getSelectedItem().toString() + " - "
									+ mSpinner2.getSelectedItem().toString() + " " + model.get_guest(),
									"OK") {

								@Override
								public void act() {
									// TODO Auto-generated method stub
									super.act();
									//((BaseActivity) getActivity()).onBackPressed();
								}

							}.show();
						} else {
							new CustomAlertDialog(getActivity(), "Lỗi",
									"Có lỗi xảy ra\nVui lòng thử lại sau !", "OK") {

								@Override
								public void act() {
									// TODO Auto-generated method stub
									super.act();
									//((BaseActivity) getActivity()).onBackPressed();
								}

							}.show();
						}
					}
				}.show();
			}
			break;
		case R.id.datcuocpage2_dattattaybtn:
			new customConfirmDialog(getRealActivity()){
				public void performOk() {
					String betRes2 = performBet2(user.getAccount() + "");
					if (betRes2.equals("1")) {
						new CustomAlertDialog(getActivity(), "Đặt cược",
								"Bạn đã đặt cược " + user.getAccount() + "xu với tỉ số " + model.get_home()
								+ " " + mSpinner1.getSelectedItem().toString() + " - "
								+ mSpinner2.getSelectedItem().toString() + " " + model.get_guest(), "OK") {

							@Override
							public void act() {
								// TODO Auto-generated method stub
								super.act();
								//((BaseActivity) getActivity()).onBackPressed();
							}

						}.show();
					} else {
						new CustomAlertDialog(getActivity(), "Lỗi",
								"Có lỗi xảy ra\nVui lòng thử lại sau !", "OK").show();
					}
				};
			}.show();
			break;
		case R.id.datcuocpage_dattattaybtn:
			new customConfirmDialog(getRealActivity()){
				public void performOk() {
					String betRes = performBet(user.getAccount() + "");
					if (betRes.equals("1")) {
						new CustomAlertDialog(getActivity(), "Đặt cược",
								"Bạn đã đặt cược " + user.getAccount() + "xu cho "
										+ getCuaDat(), "OK") {

							@Override
							public void act() {
								// TODO Auto-generated method stub
								super.act();
								//((BaseActivity) getActivity()).onBackPressed();
							}

						}.show();
					} else {
						new CustomAlertDialog(getActivity(), "Lỗi",
								"Có lỗi xảy ra\nVui lòng thử lại sau !", "OK").show();
					}
				};
			}.show();
			break;
		default:
			break;
		}

	}

	private class customConfirmDialog extends Dialog implements android.view.View.OnClickListener{
		public LayoutInflater inflater;
		public LinearLayout btnOk, btnCancel;
		public customConfirmDialog(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}
		 
		
		@Override
		public void show() {
			// TODO Auto-generated method stub
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.show();
			
			View v = inflater.inflate(R.layout.verifybet_dialog, null);
			btnOk = (LinearLayout) v.findViewById(R.id.verifyBet_ok);
			btnCancel = (LinearLayout) v.findViewById(R.id.verifyBet_cancel);
			btnOk.setOnClickListener(this);
			btnCancel.setOnClickListener(this);
			setContentView(v);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.verifyBet_ok:
				performOk();
				break;
			case R.id.verifyBet_cancel:
				performCancel();
				break;
			default:
				break;
			}
		}	
		public void performOk(){
			
			this.dismiss();
		}
		
		public void performCancel(){		
			this.dismiss();
		}
		
	}
	
	private String performBet(String xu) {
		String serviceResult = new JSONParser(getActivity()).performBet(
				model.get_matchID() + "", "undefined",
				mSharePreferences.getInt("CURRENT_USER_ID", 0) + "", betType + "",
				xu, ((TextView) v.findViewById(R.id.datcuocpage_tiledat))
						.getText().toString(), model.get_handicap());
		return serviceResult;
	}
	private String performBet2(String xu){
		String serviceResult = new JSONParser(getActivity()).performBet2(
				model.get_matchID() + ""
				,"undefined"
				,mSharePreferences.getInt("CURRENT_USER_ID", 0) + ""
				,betType + ""
				,xu
				,((TextView) v.findViewById(R.id.datcuocpage2_tiledat)).getText().toString()
				,model.get_handicap()
				,mSpinner1.getSelectedItem().toString()
				,mSpinner2.getSelectedItem().toString()
				,0+"");
		return serviceResult;
	}
}
