package ats.abongda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ats.abongda.fragment.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NienLe on 02-Aug-16.
 */
public class DummyFragment extends BaseFragment {


    @BindView(R.id.xx)
    TextView xx;

    public static DummyFragment newInstance() {
        DummyFragment fragment = new DummyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v2 = inflater.inflate(R.layout.fragment_dummy, container, false);
        ButterKnife.bind(this, v2);
        xx.setText("xxx");
        return v2;
    }

    @Override
    public void loadContent() {

    }

    public void changeText(String text){
        xx.setText(text);
    }
}
