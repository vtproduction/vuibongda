package ats.abongda.customClass.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by NienLe on 20-Aug-16.
 */
public class OffsetDecoration extends RecyclerView.ItemDecoration{
    private int mLeftOffset;
    private int mTopOffset;
    private int mRightOffset;
    private int mBottomOffset;

    public OffsetDecoration(int mLeftOffset, int mTopOffset, int mRightOffset, int mBottomOffset) {
        this.mLeftOffset = mLeftOffset;
        this.mTopOffset = mTopOffset;
        this.mRightOffset = mRightOffset;
        this.mBottomOffset = mBottomOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dataSize = state.getItemCount();
        int position = parent.getChildPosition(view);
        if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(mLeftOffset, mTopOffset, mRightOffset, mBottomOffset);
        } else {
            outRect.set(0, 0, 0, 0);
        }

    }
}
