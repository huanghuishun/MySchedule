package com.example.huanghuishun.myschedule.adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.entity.City;
import com.example.huanghuishun.myschedule.entity.Weather;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by huanghuishun on 2016/8/19.
 */
public class CityChooseRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<City> cityList;
    private List<Weather> weatherList;
    private City location;

    public HashMap<String, Integer> alphaIndex;

    final public static int ITEM_MINE = 1;
    final public static int ITEM_LOCATION = 2;
    final public static int ITEM_ALL = 3;

    private String[] backgroundColors = new String[]{"#e8854c","#d4584e","#5b7262","#29555e"};

    public CityChooseRVAdapter(Context context, List<Weather> weatherList, City location, List<City> cityList) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.weatherList = weatherList;
        this.location = location;
        this.cityList = cityList;
        alphaIndex = new HashMap<>();
        for (int i = 0; i < cityList.size(); i++) {
            char currentChar = Pinyin.toPinyin(cityList.get(i).getName().charAt(0)).charAt(0);
            char previewChar = (i - 1) >= 0 ? Pinyin.toPinyin(cityList.get(i - 1).getName().charAt(0)).charAt(0) : '0';
            if (currentChar != previewChar) {
                String alpha = String.valueOf(currentChar);
                int index = i + weatherList.size() + 1;
                alphaIndex.put(alpha, index);
            }
        }
        alphaIndex.put("我的",0);
        alphaIndex.put("定位",weatherList.size());
        Log.d("character", alphaIndex.toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case ITEM_MINE:
                holder = new MineViewHolder(mLayoutInflater.inflate(R.layout.locationitem_mine, parent, false));
                break;
            case ITEM_LOCATION:
                holder = new LocationViewHolder(mLayoutInflater.inflate(R.layout.locationitem_location, parent, false));
                break;
            case ITEM_ALL:
                holder = new AllViewHolder(mLayoutInflater.inflate(R.layout.locationitem_all, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MineViewHolder) {
            ((MineViewHolder) holder).tv_weather.setText(weatherList.get(position).getDayWeather());
            ((MineViewHolder) holder).tv_temp.setText(weatherList.get(position).getDayTemp());
            ((MineViewHolder) holder).tv_place.setText(weatherList.get(position).getCity().getName());
            ((MineViewHolder) holder).itemView.setBackgroundColor(Color.parseColor(backgroundColors[position%4]));
            ((MineViewHolder) holder).itemView.setClickable(true);
        } else if (holder instanceof LocationViewHolder) {
            ((LocationViewHolder) holder).tv_place.setText(location.getName());
            ((LocationViewHolder) holder).itemView.setClickable(true);
        } else {
            ((AllViewHolder) holder).tv_place.setText(cityList.get(position - weatherList.size() - 1).getName());
            ((AllViewHolder) holder).itemView.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        return cityList.size() + weatherList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < weatherList.size()) {
            return ITEM_MINE;
        } else if (position == weatherList.size()) {
            return ITEM_LOCATION;
        } else {
            return ITEM_ALL;
        }
    }

    class MineViewHolder extends RecyclerView.ViewHolder {
        TextView tv_place;
        TextView tv_weather;
        TextView tv_temp;

        public MineViewHolder(View itemView) {
            super(itemView);
            tv_place = (TextView) itemView.findViewById(R.id.item1_tv_place);
            tv_weather = (TextView) itemView.findViewById(R.id.item1_tv_weather);
            tv_temp = (TextView) itemView.findViewById(R.id.item1_tv_temp);
        }
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tv_place;

        public LocationViewHolder(View itemView) {
            super(itemView);
            tv_place = (TextView) itemView.findViewById(R.id.item2_tv_place);
        }
    }

    class AllViewHolder extends RecyclerView.ViewHolder {
        TextView tv_place;

        public AllViewHolder(View itemView) {
            super(itemView);
            tv_place = (TextView) itemView.findViewById(R.id.item3_tv_place);
        }
    }

}
