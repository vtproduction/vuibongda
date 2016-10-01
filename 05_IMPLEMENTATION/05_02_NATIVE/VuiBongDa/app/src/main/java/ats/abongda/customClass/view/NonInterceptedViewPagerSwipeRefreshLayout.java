package ats.abongda.customClass.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import ats.abongda.R;

/**
 * Created by NienLe on 07-Aug-16.
 */
public class NonInterceptedViewPagerSwipeRefreshLayout  extends SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;

    public NonInterceptedViewPagerSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(context.getResources().getColor(R.color.base_app_color));
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
