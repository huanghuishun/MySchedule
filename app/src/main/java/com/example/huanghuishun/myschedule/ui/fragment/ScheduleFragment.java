package com.example.huanghuishun.myschedule.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huanghuishun.myschedule.R;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ScheduleFragment extends BaseFragment {
    private View view;

    public static ScheduleFragment newInstance(int index) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        return view;
    }
}
