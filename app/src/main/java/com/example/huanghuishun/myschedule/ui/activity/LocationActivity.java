package com.example.huanghuishun.myschedule.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.adapter.CityChooseRVAdapter;
import com.example.huanghuishun.myschedule.adapter.OnRecyclerItemClickListener;
import com.example.huanghuishun.myschedule.adapter.SearchResultRVAdapter;
import com.example.huanghuishun.myschedule.entity.City;
import com.example.huanghuishun.myschedule.entity.Weather;
import com.example.huanghuishun.myschedule.sqlite.AllCitiesDatabaseHelper;
import com.example.huanghuishun.myschedule.sqlite.MyCitiesDatabaseHelper;
import com.example.huanghuishun.myschedule.utils.WeatherUtils;
import com.example.huanghuishun.myschedule.utils.onDataLoadCompletedListener;
import com.example.huanghuishun.myschedule.widget.MyLetterView;
import com.github.promeg.pinyinhelper.Pinyin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class LocationActivity extends BaseActivityWithToolbar {

    final private static int CLOSE_DIALOG = 0;
    final private static int REFRESH_RECYCLERVIEW = 1;

    private MyLetterView myLetterView;
    private TextView tvDialog;
    private RecyclerView recyclerView;
    private View mProgressView;
    private RelativeLayout progress;

    private List<Weather> weatherList;
    private City location;
    private List<City> cityList;
    private List<City> searchResultList;

    public MyCitiesDatabaseHelper citiesDatabaseHelper;

    public CityChooseRVAdapter cityChooseRVAdapter;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
        showProgress(true);
        initData();
        setListener();
        insertMyCity();
        initWeatherListData();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CLOSE_DIALOG:
                        showProgress(false);
                        setAdapter();
                        break;
                    case REFRESH_RECYCLERVIEW:
                        cityChooseRVAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initCityList();
                Message message = new Message();
                message.what = CLOSE_DIALOG;
                handler.sendMessage(message);
            }
        });
        thread.start();

    }

    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_place);
        isBackButtonEnabled(true);
        getToolbar().setTitle(R.string.activity_choose_city);
        myLetterView = (MyLetterView) findViewById(R.id.my_letterview);
        tvDialog = (TextView) findViewById(R.id.tv_dialog);
        //将中间展示字母的TextView传递到myLetterView中并在其中控制它的显示与隐藏
        myLetterView.setTextView(tvDialog);
        //注册MyLetterView中监听(跟setOnClickListener这种系统默认写好的监听一样只不过这里是我们自己写的)
        progress = (RelativeLayout) findViewById(R.id.rl_progress);
        mProgressView = findViewById(R.id.pb_login);
    }

    public void initData() {
        weatherList = new ArrayList<>();
        location = new City();
        cityList = new ArrayList<>();
        searchResultList = new ArrayList<>();
        citiesDatabaseHelper = new MyCitiesDatabaseHelper(this);

    }

    public void setListener() {
        myLetterView.setOnSlidingListener(new MyLetterView.OnSlidingListener() {
            @Override
            public void sliding(String str) {
                if (cityChooseRVAdapter.alphaIndex.get(str) != null) {
                    int position = cityChooseRVAdapter.alphaIndex.get(str);
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
                    tvDialog.setText(str);
                }
            }
        });

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder) {
                SQLiteDatabase db = citiesDatabaseHelper.getReadableDatabase();
                switch (holder.getItemViewType()) {
                    case CityChooseRVAdapter.ITEM_MINE:
                        int position = holder.getLayoutPosition();
                        Weather weather = weatherList.get(position);
                        db.execSQL("update city set isprimary = 0");
                        db.execSQL("update city set isprimary = 1 where adcode =" + weather.getCity().getAdCode());
                        Snackbar.make(findViewById(R.id.location_root), "已将 " + weather.getCity().getName() + " 设为主要城市。", Snackbar.LENGTH_LONG)
                                .setAction("好", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                        break;
                    case CityChooseRVAdapter.ITEM_LOCATION:
                        break;
                    case CityChooseRVAdapter.ITEM_ALL:
                        final int realPosition = holder.getLayoutPosition() - weatherList.size() - 1;
                        final City city = cityList.get(realPosition);
                        db.execSQL("insert or ignore into city(name,adcode) values ('" + city.getName() + "'," + city.getAdCode() + ")");
                        Snackbar.make(findViewById(R.id.location_root), "已经添加到“我的城市”。", Snackbar.LENGTH_LONG)
                                .setAction("删除", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        SQLiteDatabase db = citiesDatabaseHelper.getReadableDatabase();
                                        db.execSQL("delete from city where adcode =" + city.getAdCode());
                                        db.close();
                                        initWeatherListData();
                                        cityChooseRVAdapter.notifyDataSetChanged();
                                    }
                                }).show();
                        break;
                }
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Message message = new Message();
//                        message.what = REFRESH_RECYCLERVIEW;
//                        handler.sendMessage(message);
//                    }
//                });
//                thread.start();
                initWeatherListData();
                setAdapter();
                db.close();

            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder holder) {
                SQLiteDatabase db = citiesDatabaseHelper.getReadableDatabase();
                int position = holder.getLayoutPosition();
                final Weather weather = weatherList.get(position);
                switch (holder.getItemViewType()) {
                    case CityChooseRVAdapter.ITEM_MINE:
                        AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this)
                                .setTitle("是否删除？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SQLiteDatabase db = citiesDatabaseHelper.getReadableDatabase();
                                        db.execSQL("delete from city where adcode =" + weather.getCity().getAdCode());
                                        db.close();
                                        initWeatherListData();
                                        cityChooseRVAdapter.notifyDataSetChanged();
                                        Snackbar.make(findViewById(R.id.location_root), "删除成功！", Snackbar.LENGTH_LONG)
                                                .setAction("好", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                }).show();
                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create().show();
                        break;
                    case CityChooseRVAdapter.ITEM_LOCATION:
                        break;
                    case CityChooseRVAdapter.ITEM_ALL:
                        break;
                }
            }
        });

    }

    public List searchCity(String name) {
        List<City> list = new ArrayList<>();
        AllCitiesDatabaseHelper helper = new AllCitiesDatabaseHelper(this);
        try {
            helper.createDataBase();
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from adcode where name like '%" + name + "%'"
                    + "or location like '%" + name + "%'", null);
            while (cursor.moveToNext()) {
                City city = new City();
                city.setName(cursor.getString(cursor.getColumnIndex("name")));
                city.setAdCode(cursor.getInt(cursor.getColumnIndex("adcode")));
                city.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                list.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void initWeatherListData() {
        WeatherUtils weatherUtils = new WeatherUtils(LocationActivity.this);
        List<City> queryCities = new ArrayList<>();
        SQLiteDatabase db = citiesDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from city", null);
        while (cursor.moveToNext()) {
            City city = new City();
            city.setName(cursor.getString(cursor.getColumnIndex("name")));
            city.setAdCode(cursor.getInt(cursor.getColumnIndex("adcode")));
            city.setPrimary(cursor.getInt(cursor.getColumnIndex("isprimary")) == 1);
            city.setId(cursor.getInt(cursor.getColumnIndex("id")));
            Log.d("idddddd", "" + city.getId());
            Log.d("idddddddddd", "" + cursor.getInt(cursor.getColumnIndex("id")));
            queryCities.add(city);
        }
        cursor.close();
        db.close();
        weatherUtils.queryWeather(queryCities, new onDataLoadCompletedListener() {
            @Override
            public void getData(List list) {
                weatherList.clear();
                weatherList.addAll(list);
                Collections.sort(weatherList, new Comparator<Weather>() {
                    @Override
                    public int compare(Weather weather, Weather t1) {
                        if (weather.getCity().getId() > t1.getCity().getId()) {
                            return 1;
                        } else if (weather.getCity().getId() < t1.getCity().getId()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                Log.d("sortttttttt", weatherList.toString());
                cityChooseRVAdapter.notifyDataSetChanged();
            }
        });
    }

    public void initCityList() {
        SQLiteDatabase db;
        Cursor cursor = null;
        AllCitiesDatabaseHelper acdh = new AllCitiesDatabaseHelper(this);
        try {
            acdh.createDataBase();
            db = acdh.getWritableDatabase();
            cursor = db.rawQuery("select * from adcode", null);
            while (cursor.moveToNext()) {
                City city = new City();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                city.setName(name);
                city.setAdCode(cursor.getInt(cursor.getColumnIndex("adcode")));
                city.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                cityList.add(city);
            }
            Collections.sort(cityList, comparator);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void setAdapter() {
        location = new City();
        location.setName("hi！");

        cityChooseRVAdapter = new CityChooseRVAdapter(this, weatherList, location, cityList);
        recyclerView.setAdapter(cityChooseRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || "".equals(newText)) {
                    myLetterView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(cityChooseRVAdapter);
                } else {
                    searchResultList = searchCity(newText);
                    recyclerView.setAdapter(new SearchResultRVAdapter(LocationActivity.this, searchResultList));
                    myLetterView.setVisibility(View.GONE);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                break;
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City c1, City c2) {
            String a = transformPinYin(c1.getName());
            String b = transformPinYin(c2.getName());
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    public String transformPinYin(String character) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Pinyin.toPinyin(character.charAt(i)));
        }
        return buffer.toString();
    }

    public void insertMyCity() {
        SQLiteDatabase db = citiesDatabaseHelper.getReadableDatabase();
        db.execSQL("insert or ignore into city(name,adcode) values ('北京',110000)");
        db.execSQL("insert or ignore into city(name,adcode) values ('上海',310000)");
        db.execSQL("insert or ignore into city(name,adcode) values ('广州',440100)");
        db.close();
    }

}
