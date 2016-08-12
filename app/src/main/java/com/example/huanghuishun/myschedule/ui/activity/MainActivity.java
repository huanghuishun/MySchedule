package com.example.huanghuishun.myschedule.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;

import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.ui.activity.BaseActivity;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
