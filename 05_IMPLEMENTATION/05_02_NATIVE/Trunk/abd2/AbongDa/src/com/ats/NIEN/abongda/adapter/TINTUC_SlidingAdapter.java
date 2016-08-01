package com.ats.NIEN.abongda.adapter;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.NIEN.abongda.UTIL.Constant;
import com.ats.NIEN.abongda.UTIL.IMAGELOADER_ImageLoader;
import com.ats.NIEN.abongda.activity.MainActivity;
import com.ats.NIEN.abongda.fragment.MAINVIEW_MainFragment;
import com.ats.NIEN.abongda.fragment.FULLSCR.FULLSCR_TintucChitietFragment;
import vn.ats.aBongDa.R;
import vn.ats.aBongDa.R.anim;

public class TINTUC_SlidingAdapter extends PagerAdapter {
	/**
	 * A LRU Cache for saving downloaded bitmap
	 */
	private static LruCache<String, Bitmap> cachedBitmaps = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 8))) {
		@Override
		protected int sizeOf(String key, Bitmap bitmap) {
			// Return the size of bitmap
			return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
		}
	};
	
	private OnItemClickListener listener;

	private String[] data;
	private String[] textData, contentData;
	public IMAGELOADER_ImageLoader iMAGELOADER_ImageLoader;
	public int curPos;
	ViewGroup indicator;
	public Fragment context;

	public TINTUC_SlidingAdapter(String[] data, String[] textData, String[] contentData, Fragment context) {
		this(context,data, textData, contentData, new OnItemClickListener() {
			@Override
			public void onClick(View view, int position) {
				// Default. Do nothing :)
			}
		});
	}
	
	public TINTUC_SlidingAdapter(Fragment context,String[] data, String[] textData, String[] contentData, OnItemClickListener listener) {
		this.data = data;
		this.textData = textData;
		this.contentData = contentData;
		this.listener = listener;
		this.context = context;
	}
	
	public void setListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {
		// TODO Auto-generated method stub
		this.curPos = position % data.length;
		this.iMAGELOADER_ImageLoader = new IMAGELOADER_ImageLoader(
				container.getContext());
		
		LayoutInflater inflater = (LayoutInflater) container.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rl = inflater.inflate(R.layout.tintuc_large_image, container, false);
		rl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("TINTUC_SlidingAdapter", "Clicked at position: " + position);
				listener.onClick(v, position);
			}
		});
		final ImageView imv = (ImageView) rl
				.findViewById(R.id.tintuc_largeimage_img);
		imv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d("tag8", "Clicked at position: " + position + " imv.onClickListener called");
				MAINVIEW_MainFragment frag = new MAINVIEW_MainFragment(Constant.TINTUCCHITIETFRAGMENT,
						new ArrayList<Object>(){{
							add(contentData[position % data.length]);
						}});
				((MainActivity)context.getActivity()).switchContent(frag);
				
				MainActivity bs = (MainActivity) context.getActivity();
				bs.changeRightImageToBack();
			}
		});
		TextView tv = (TextView) rl.findViewById(R.id.tintuc_largeimage_text);
		tv.setText(textData[position % data.length]);
		

		

		String htmlContent = data[position % data.length];

		
		if (cachedBitmaps.get(htmlContent) == null) {
			new DownloadImageTask(imv).execute(htmlContent);
		} else {
			imv.setImageBitmap(cachedBitmaps.get(htmlContent));
		}
		

		container.addView(rl);

		return rl;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		// super.destroyItem(container, position, object);
		container.removeView((View) object);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		private String url;
		private ImageView imv;
		public DownloadImageTask(ImageView imv) {
			this.imv = imv;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				url = params[0];
				InputStream is = new URL(params[0]).openStream();
				return BitmapFactory.decodeStream(is);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				cachedBitmaps.put(url, result);
				imv.setImageBitmap(result);
			}
		}
	}
	
	/**
	 * Called each time an item view is clicked
	 * @author Nguyen
	 *
	 */
	public interface OnItemClickListener {
		public void onClick(View view, int position);
	}
}
