package com.example.huanghuishun.myschedule.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;

import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.entity.City;
import com.example.huanghuishun.myschedule.ui.activity.BaseActivity;
import com.example.huanghuishun.myschedule.utils.WeatherUtils;
import com.example.huanghuishun.myschedule.utils.onDataLoadCompletedListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
