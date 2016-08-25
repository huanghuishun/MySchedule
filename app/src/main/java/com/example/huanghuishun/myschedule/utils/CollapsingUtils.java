package com.example.huanghuishun.myschedule.utils;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.entity.City;
import com.example.huanghuishun.myschedule.entity.Weather;

import java.util.List;

/**
 * Created by huanghuishun on 2016/8/25.
 */
public class CollapsingUtils {
    private Context context;
    private LinearLayout weatherCollasping;
    private TextView tvTempture;
    private TextView tvWindForce;
    private TextView tvWindDire;
    private TextView tvHumidity;
    private ImageView ivWeather;
    private int[] weatherSunny = new int[]{R.drawable.snow, R.drawable.sunny, R.drawable.sunny2, R.drawable.sunny3};
    private int[] weatherCloudy = new int[]{R.drawable.cloudy, R.drawable.cloudy2, R.drawable.cloudy3, R.drawable.cloudy4};
    private int[] weatherRainy = new int[]{R.drawable.rainy2, R.drawable.rainy1};

    public CollapsingUtils(Context context) {
        this.context = context;
        initview();
    }
    private void initview() {
        weatherCollasping = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.weathercollapsing, null);
        tvTempture = (TextView) weatherCollasping.findViewById(R.id.tv_tempture);
        tvWindForce = (TextView) weatherCollasping.findViewById(R.id.tv_windforce);
        tvWindDire = (TextView) weatherCollasping.findViewById(R.id.tv_winddire);
        tvHumidity = (TextView) weatherCollasping.findViewById(R.id.tv_humidity);
        ivWeather = (ImageView) weatherCollasping.findViewById(R.id.iv_weather);
    }

    public View getWeatherCollapsingView(Weather weather){
        tvTempture.setText(weather.getDayTemp());
        tvWindForce.setText(weather.getDayWindPower() + "级");
        tvWindDire.setText(weather.getDayWindDirection() + "风");
        tvHumidity.setText(weather.getHumidity() + "%");
        //imageview还没设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            weatherCollasping.setBackground(context.getDrawable(R.drawable.sunny3));
        }
        return weatherCollasping;
    }
}
