package ats.abongda.customClass.view.slidingview;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ats.abongda.MainApplication;
import ats.abongda.R;
import ats.abongda.activity.base.BaseSlidingActivity2;
import ats.abongda.adapter.DrawerAdapter;
import ats.abongda.customClass.AnimatedExpandableListView;
import ats.abongda.helper.LogUtil;
import ats.abongda.listener.MenuItemClickImp;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 06-Aug-16.
 */
public class MenuSliding extends MySlidingLayer {
    BaseSlidingActivity2 activity;
    MainApplication mApp;
    @BindView(R.id.menuitem_indicator)
    ImageView menuitemIndicator;
    @BindView(R.id.menusubitem_text)
    TextView menusubitemText;
    @BindView(R.id.live_page_listview)
    AnimatedExpandableListView listView;
    @BindView(R.id.menu_main_layout)
    LinearLayout menuMainLayout;
    private DrawerAdapter adapter;
    private int mWith;
    private Context context;
    private MenuItemClickImp callback;

    public MenuSliding(Context context, MenuItemClickImp callback) {
        this(context);
        this.context = context;
        this.callback = callback;
        View menuView = LayoutInflater.from(context).inflate(R.layout.menu, null, false);
        setStickTo(SlidingLayer.STICK_TO_LEFT);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                1300,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        addView(menuView);
        mWith = menuView.getWidth();
        setCloseOnTapEnabled(true);
        setLayoutParams(lp);
        setSlidingEnabled(true);
        ButterKnife.bind(this, menuView);
        setContent();
    }


    private void setContent(){
        try{
            listView.setDividerHeight(0);
            listView.setFadingEdgeLength(0);
            adapter = new DrawerAdapter(context, listView);
            listView.setAdapter(adapter);
            listView.setGroupIndicator(null);
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
                        callback.onMenuItemClicked(actionCode);
                    }

                    return true;
                }

            });
            listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    listView.collapseGroupWithAnimation(i);
                    int actionCode = adapter.getChildActionCode(i, i1);
                    callback.onMenuItemClicked(actionCode);
                    return true;
                }
            });
        }catch (Exception e){
            LogUtil.e(e);
        }
    }


    public MenuSliding(Context context) {
        super(context);
    }

    public MenuSliding(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuSliding(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setActivity(final BaseSlidingActivity2 activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mWith = point.x;
        this.activity = activity;
        mApp = (MainApplication) activity.getApplication();
        //initModels();
        setOnInteractListener(new OnInteractListener() {
            @Override
            public void onOpen() {
            }

            @Override
            public void onClose() {

            }

            @Override
            public void onOpened() {
            }

            @Override
            public void onClosed() {
                if (activity.mForegroundMenu != null) {
                    activity.mForegroundMenu.setVisibility(GONE);
                }
            }

            @Override
            public void onScrollTo(int x, int y) {
                if (mWith > 0 && activity.mForegroundMenu != null) {
                    activity.mForegroundMenu.setVisibility(VISIBLE);
                    activity.mForegroundMenu.setAlpha(Math.max(0f, Math.min(1f,
                            1f - (float) Math.abs(x) / (float) mWith)));
                } else {
                }
            }

            @Override
            public void onActionMove(float x, float y) {

            }

            @Override
            public boolean onInterceptActionMove(float x, float y) {
                return false;
            }
        });
    }
}
