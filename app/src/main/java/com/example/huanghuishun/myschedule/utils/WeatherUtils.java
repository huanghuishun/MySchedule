package com.example.huanghuishun.myschedule.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.huanghuishun.myschedule.R;

/**
 * Created by huanghuishun on 2016/8/14.
 */
public class WeatherUtils implements WeatherSearch.OnWeatherSearchListener {
    private Context context;
    private RelativeLayout weatherNav;
    private TextView textView;
    private INaviChanger naviChanger;
    private int[] pictureIds = new int[]{R.drawable.cloudy,R.drawable.cloudy2,R.drawable.cloudy3,R.drawable.cloudy4
    ,R.drawable.rainy2,R.drawable.rainy1,R.drawable.snow,R.drawable.sunny,R.drawable.sunny2,R.drawable.sunny3};

    public WeatherUtils(Context context) {
        this.context = context;
        initview();
    }

    public void queryWeather(String placeName) {
        WeatherSearchQuery query = new WeatherSearchQuery(placeName, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch weatherSearch = new WeatherSearch(context);
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();
    }

    public void forecastWeather(String placeName) {
        WeatherSearchQuery query = new WeatherSearchQuery(placeName, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        WeatherSearch weatherSearch = new WeatherSearch(context);
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();
    }

    public void setNaviChanger(INaviChanger naviChanger) {
        this.naviChanger = naviChanger;
    }

    private void initview() {
        weatherNav = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.weathernav, null);
        textView = (TextView) weatherNav.findViewById(R.id.textView8);
    }


    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        if (i == 1000) {
            if (localWeatherLiveResult != null && localWeatherLiveResult.getLiveResult() != null) {
                textView.setText(localWeatherLiveResult.getLiveResult().getWeather());
                Log.d("weather++adcode", localWeatherLiveResult.getLiveResult().getAdCode());
                Log.d("weather++city", localWeatherLiveResult.getLiveResult().getCity());
                Log.d("weather++humidity", localWeatherLiveResult.getLiveResult().getHumidity());
                Log.d("weather++province", localWeatherLiveResult.getLiveResult().getProvince());
                Log.d("weather++reporttime", localWeatherLiveResult.getLiveResult().getReportTime());
                Log.d("weather++tempture", localWeatherLiveResult.getLiveResult().getTemperature());
                Log.d("weather++weather", localWeatherLiveResult.getLiveResult().getWeather());
                Log.d("weather++weatherdirect", localWeatherLiveResult.getLiveResult().getWindDirection());
                Log.d("weather++winpower", localWeatherLiveResult.getLiveResult().getWindPower());
                naviChanger.changeNaviView(weatherNav);
            }
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
        if (i == 1000) {
            if (localWeatherForecastResult != null && localWeatherForecastResult.getForecastResult() != null) {
                Log.d("weatherfore+date",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getDate());
                Log.d("weatherfore+daytemp",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getDayTemp());
                Log.d("weatherfore+dayerather",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getDayWeather());
                Log.d("weatherfore+winddire",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getDayWindDirection());
                Log.d("weatherfore+winepowe",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getDayWindPower());
                Log.d("weatherfore+nightemp",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getNightTemp());
                Log.d("weatherfore+nightwea",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getNightWeather());
                Log.d("weatherfore+nightwidd",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getNightWindDirection());
                Log.d("weatherfore+nightwinp",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getNightWindPower());
                Log.d("weatherfore+week",localWeatherForecastResult.getForecastResult().getWeatherForecast().get(0).getWeek());
            }
        }
    }


}
