package com.example.huanghuishun.myschedule.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.entity.City;
import com.example.huanghuishun.myschedule.entity.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanghuishun on 2016/8/14.
 */
public class WeatherUtils {
    private Context context;

    public WeatherUtils(Context context) {
        this.context = context;
    }

    public void queryWeather(final List<City> cities, final onDataLoadCompletedListener listener) {
        final List<Weather> weatherList = new ArrayList<>();
        for (City city : cities) {
            final Weather weather = new Weather();
            weather.setCity(city);
            WeatherSearchQuery query = new WeatherSearchQuery(""+city.getAdCode(), WeatherSearchQuery.WEATHER_TYPE_LIVE);
            WeatherSearch weatherSearch = new WeatherSearch(context);
            weatherSearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                @Override
                public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
                    if (i == 1000) {
                        if (localWeatherLiveResult != null && localWeatherLiveResult.getLiveResult() != null) {
                            weather.setDayTemp(localWeatherLiveResult.getLiveResult().getTemperature());
                            weather.setDayWeather(localWeatherLiveResult.getLiveResult().getWeather());
                            weather.setDayWindDirection(localWeatherLiveResult.getLiveResult().getWindDirection());
                            weather.setDayWindPower(localWeatherLiveResult.getLiveResult().getWindPower());
                            weather.setHumidity(localWeatherLiveResult.getLiveResult().getHumidity());
                            weatherList.add(weather);
                            if (weatherList.size() == cities.size()){
                                listener.getData(weatherList);
                            }
                        }
                    }
                }

                @Override
                public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

                }
            });
            weatherSearch.setQuery(query);
            weatherSearch.searchWeatherAsyn();
        }
    }






}
