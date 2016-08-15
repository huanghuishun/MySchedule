package com.example.huanghuishun.myschedule.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.huanghuishun.myschedule.R;

/**
 * Created by huanghuishun on 2016/8/14.
 */
public class WeatherUtils implements WeatherSearch.OnWeatherSearchListener{
    private Context context;
    private RelativeLayout weatherNav;
    private TextView textView;
    private INaviChanger naviChanger;

    public WeatherUtils(Context context) {
        this.context = context;
        initview();
    }
    public void queryWeather(String placeName){
        WeatherSearchQuery query = new WeatherSearchQuery(placeName,WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch weatherSearch = new WeatherSearch(context);
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();
    }

    public void setNaviChanger(INaviChanger naviChanger) {
        this.naviChanger = naviChanger;
    }

    private void initview(){
        weatherNav = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.weathernav,null);
        textView = (TextView) weatherNav.findViewById(R.id.textView6);
    }


    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        if (i == 1000){
            if (localWeatherLiveResult != null && localWeatherLiveResult.getLiveResult() != null){
                textView.setText(localWeatherLiveResult.getLiveResult().getWeather());
                naviChanger.changeNaviView(weatherNav);
            }
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }


}
