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
        WeatherUtils weatherUtils = new WeatherUtils(MainActivity.this);
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setAdCode(110101);
        cityList.add(city);
        City city2 = new City();
        city.setAdCode(340100);
        cityList.add(city2);
        City city3 = new City();
        city.setAdCode(340200);
        cityList.add(city);
        City city4 = new City();
        city.setAdCode(341200);
        cityList.add(city);
        City city5 = new City();
        city.setAdCode(350200);
        cityList.add(city);
        City city6 = new City();
        city.setAdCode(350500);
        cityList.add(city);
        City city7 = new City();
        city.setAdCode(360400);
        cityList.add(city);
        City city8 = new City();
        city.setAdCode(370500);
        cityList.add(city);
        weatherUtils.queryWeather(cityList, new onDataLoadCompletedListener() {
            @Override
            public void sendData(List list) {
                Log.d("sizzzzzzze",list.toString());
            }
        });


    }
}
