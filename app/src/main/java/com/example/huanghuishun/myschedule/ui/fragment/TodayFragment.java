package com.example.huanghuishun.myschedule.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huanghuishun.myschedule.R;

/**
 * Created by huanghuishun on 2016/8/12.
 */
public class TodayFragment extends BaseFragment {
    private View view;

    public static TodayFragment newInstance(int index) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);
        return view;
    }
}
