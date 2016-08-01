package com.ats.NIEN.abongda.fragment.LIVE;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.IMAGELOADER_ImageLoader;
import com.ats.NIEN.abongda.UTIL.InternetChecking;
import com.ats.NIEN.abongda.UTIL.ProgressHUD;
import com.ats.NIEN.abongda.UTIL.InternetChecking.InternetCheckingCallback;
import com.ats.NIEN.abongda.UTIL.JSONParser;
import com.ats.NIEN.abongda.activity.BaseActivity;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.adapter.TINTUC_SlidingAdapter;
import com.ats.NIEN.abongda.fragment.BaseFragment;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_TintucChitietFragment;
import com.ats.NIEN.abongda.model.TintucModel;
import vn.ats.aBongDa.R;
import com.imbryk.viewPager.LoopViewPager;
import com.viewpagerindicator.CirclePageIndicator;

public class LIVE_TintucFragment extends BaseFragment{
	public ListView listview;
	public ScrollView mainScrollView;
	public LinearLayout mainLayout, item;
	public RelativeLayout slidingLayout;
	public final int DELAY_TIME = 4000;
	public IMAGELOADER_ImageLoader imageLoader;
	public LayoutInflater inflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View v = inflater.inflate(R.layout.tintuc_mainlayout, container, false);
		slidingLayout = (RelativeLayout) inflater.inflate(R.layout.xml_cua_tao, container, false);
		
		mainScrollView = (ScrollView) v;
		this.inflater = inflater;
		mainLayout = (LinearLayout) v.findViewById(R.id.tintuc_mainlist);
		this.imageLoader = new IMAGELOADER_ImageLoader(getRealActivity());
		return v;
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		if (mSharePreferences.getInt("CALLBACK_FRAGMENT", 0) != Constant.TINTUC_CODE) {
			return;
		}
		netAsync();
	}
	
	@Override
	public void refreshContent() {
		// TODO Auto-generated method stub
		super.refreshContent();
		new ProcessListContents(false).execute();
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	

	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private class ProcessListContents extends
			AsyncTask<String, String, List<TintucModel>> {
		public Dialog MpDialog;
		public boolean flag; 

		public ProcessListContents() {
			super();
			// TODO Auto-generated constructor stub
			this.flag = true;
		}

		public ProcessListContents(boolean flag) {
			super();
			this.flag = flag;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (flag) {
				//MpDialog = new ProgressHUD(getRealActivity()).show(getRealActivity(), "", true, false, null);
			}else{
				MpDialog = new ProgressHUD(getRealActivity(),false).show(getRealActivity(), true, false, null);
				((BaseActivity)getRealActivity()).animateRightImage();
			}
			
		}
		

		@Override
		protected List<TintucModel> doInBackground(String... args) {
			if (getActivity() != null) {
				mActivity = getActivity();
			}
			List<TintucModel> list = new ArrayList<TintucModel>();
			list = new JSONParser(mActivity).parseTintuc("0");
			return list;
		}

		@Override
		protected void onPostExecute(final List<TintucModel> list) {
			
			mainLayout.removeAllViews();
			String[] imgData;
			String[] textData, contentData;
			imgData = new String[list.size()];
			for (int i = 0; i < imgData.length; i++) {
				imgData[i] = list.get(i).getImageLink();
			}
			textData = new String[list.size()];
			contentData = new String[list.size()];
			for (int i = 0; i < textData.length; i++) {
				textData[i] = list.get(i).getTitle();
				contentData[i] = list.get(i).getContent();
			}
			if (list.size() > 0) {
				if (flag) {
					setSlidingView(imgData, textData, contentData);
				}
				mainLayout.addView(slidingLayout);
				for (int i = 0; i < list.size(); i++) {
					final TintucModel model = list.get(i);
					item = (LinearLayout) inflater.inflate(R.layout.tintuc_list_item, null);
					imageLoader.DisplayImage(model.getImageLink(), ((ImageView)item.findViewById(R.id.tintuc_listitem_thumbimg)));
					((TextView)item.findViewById(R.id.tintuc_listitem_title)).setText(model.getTitle());
					mainLayout.addView(item);
					item.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							final String htmlContent = "<b>"+model.getTitle()+"</b></br>"+model.getContent();
							MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.TINTUCCHITIETFRAGMENT,
									new ArrayList<Object>(){{
										add(htmlContent);
									}});
							((MainActivity)getRealActivity()).switchContent(frag);
							MainActivity bs = (MainActivity) getActivity();
							bs.changeRightImageToBack();
						}
					});
					
				}
			}
			if (!flag) {
				this.MpDialog.dismiss();
				((BaseActivity)getRealActivity()).stopAnimateRightImage();
			}
			
			
		}
	}

	@Override
	public Activity getRealActivity() {
		// TODO Auto-generated method stub
		return (getActivity()==null) ? mActivity : getActivity();
	}
	
	@Override
	public void netAsync() {
		new InternetChecking(new WeakReference<Context>(getRealActivity()), new InternetCheckingCallback() {
			
			@Override
			public void onCheckComplete(boolean result,final Dialog progressDialog) {
				// TODO Auto-generated method stub
				if (!result) {
					progressDialog.dismiss();				
				}else{
					new ProcessListContents() {
						@Override
						protected void onPostExecute(final List<TintucModel> list) {
							// TODO Auto-generated method stub
							super.onPostExecute(list);
							/*if (progressDialog.isShowing()) {
								progressDialog.dismiss();	
							}*/
							progressDialog.dismiss();
						}
					}.execute();
				}
			}
		}).execute();
	}
	
	
	
	public void setSlidingView(String[] imgData, String[] textData, String[] contentData){
		final LoopViewPager viewPager = (LoopViewPager) slidingLayout.findViewById(R.id.view_pager);
		final CirclePageIndicator indicator = (CirclePageIndicator) slidingLayout.findViewById(R.id.view_pager_indicator);
		
		viewPager.setBoundaryCaching(true);
		viewPager.setAdapter(new TINTUC_SlidingAdapter(imgData, textData, contentData, this));
		final Handler handler = new Handler();
		final OnPageChangeListener[] listener = new OnPageChangeListener[1];
		final Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				indicator.setOnPageChangeListener(null);
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
				indicator.setOnPageChangeListener(listener[0]);
				
				handler.postDelayed(this, DELAY_TIME);
			}
		};
		
		listener[0] = new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.d("TinTucAdapter", "OnPageSelected called");
				handler.removeCallbacks(runnable);
				handler.postDelayed(runnable, DELAY_TIME);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		handler.postDelayed(runnable, 3000);
		
		indicator.setViewPager(viewPager);
		indicator.setOnPageChangeListener(listener[0]);
		
		Field mScroller;
		try {
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			mScroller.set(viewPager, new FixedSpeedScroller(viewPager.getContext()));
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	@SuppressLint("NewApi")
	public class FixedSpeedScroller extends Scroller {

	    private int mDuration = 1500;

	    public FixedSpeedScroller(Context context) {
	        super(context);
	    }

	    public FixedSpeedScroller(Context context, Interpolator interpolator) {
	        super(context, interpolator);
	    }

	    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
	        super(context, interpolator, flywheel);
	    }


	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
	        // Ignore received duration, use fixed one instead
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy) {
	        // Ignore received duration, use fixed one instead
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }
	}
}

