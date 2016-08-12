package com.example.huanghuishun.myschedule.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huanghuishun.myschedule.R;

/**
 * Created by Administrator on 2016/8/12.
 */
public class SingleTaskFragment extends BaseFragment {
    private View view;

    public static SingleTaskFragment newInstance(int index) {
        SingleTaskFragment fragment = new SingleTaskFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_singletask, container, false);
        return view;
    }
}
