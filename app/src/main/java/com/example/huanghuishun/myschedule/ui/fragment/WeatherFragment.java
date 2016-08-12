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
public class WeatherFragment extends BaseFragment {
    private View view;

    public static WeatherFragment newInstance(int index) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        return view;
    }
}
