package com.example.huanghuishun.myschedule.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.media.RatingCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.entity.City;
import com.example.huanghuishun.myschedule.entity.Weather;
import com.example.huanghuishun.myschedule.sqlite.MyCitiesDatabaseHelper;
import com.example.huanghuishun.myschedule.ui.fragment.ScheduleFragment;
import com.example.huanghuishun.myschedule.ui.fragment.TodayFragment;
import com.example.huanghuishun.myschedule.ui.fragment.WalletFragment;
import com.example.huanghuishun.myschedule.ui.fragment.WeatherFragment;
import com.example.huanghuishun.myschedule.utils.CollapsingUtils;
import com.example.huanghuishun.myschedule.utils.ICollapsingChanger;
import com.example.huanghuishun.myschedule.utils.WeatherUtils;
import com.example.huanghuishun.myschedule.utils.onDataLoadCompletedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by huanghuishun on 2016/8/11.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ArrayList<Fragment> fragmentList;
    private static String title;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private MenuItem refreshItem;
    private City primaryCity;
    private LinearLayout weatherCollasping;
    private RelativeLayout collapsingView;

    public MyCitiesDatabaseHelper citiesDatabaseHelper;

    TodayFragment todayFragment = new TodayFragment();
    WeatherFragment weatherFragment = new WeatherFragment();
    ScheduleFragment scheduleFragment = new ScheduleFragment();
    WalletFragment walletFragment = new WalletFragment();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initData();
    }

    private void initView() {
        weatherCollasping = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.weathercollapsing, null);
        collapsingView = (RelativeLayout) findViewById(R.id.rl_collapsing);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsinglayout);
        title = "今日";
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setTitleEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentList = new ArrayList<>();
        fragmentList.add(todayFragment);
        fragmentList.add(weatherFragment);
        fragmentList.add(scheduleFragment);
        fragmentList.add(walletFragment);
        changeNavi(0);


    }

    private void initData(){
        citiesDatabaseHelper = new MyCitiesDatabaseHelper(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        refreshItem = menu.findItem(R.id.title_refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.title_location) {
            Intent intent = new Intent(BaseActivity.this,LocationActivity.class);
            Log.d("++++++++","+++++++");
            startActivity(intent);
            return true;
        } else if (id == R.id.title_refresh) {
            queryWeather();
        }
        return super.onOptionsItemSelected(item);
    }
    private void startRefreshAnimation() {
        View refreshAction = toolbar.findViewById(R.id.title_refresh);
        if (refreshAction != null){
            refreshItem.setActionView(refreshAction);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            refreshAction.startAnimation(animation);
        }
    }

    private void stopRefreshAnimation(boolean isSuccess) {

        if (refreshItem != null) {
            View view = refreshItem.getActionView();
            if (view != null) {
                view.clearAnimation();
                refreshItem.setActionView(null);
                if (isSuccess) {
                    Snackbar.make(findViewById(R.id.fragmentcontainer), "天气信息已更新。", Snackbar.LENGTH_LONG)
                            .setAction("好", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                } else {
                    Snackbar.make(findViewById(R.id.fragmentcontainer), "更新失败，请检查网络设置。", Snackbar.LENGTH_LONG)
                            .setAction("好", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                }
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            collapsingToolbarLayout.setTitle("今天");
            queryWeather();
            changeNavi(0);
        } else if (id == R.id.nav_weather) {
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.nav_header_weather));
            changeNavi(1);
        } else if (id == R.id.nav_schedule) {
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.nav_header_schedule));
            changeNavi(2);
        } else if (id == R.id.nav_wallet) {
            changeNavi(3);
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.nav_header_wallet));
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_exit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeNavi(int index) {
        title = collapsingToolbarLayout.getTitle().toString();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentcontainer, fragmentList.get(index))
                .commit();

    }

    public void queryWeather() {
        startRefreshAnimation();
        List<City> list = new ArrayList<>();
        final WeatherUtils weatherUtils = new WeatherUtils(this);
        SQLiteDatabase db = citiesDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from city where isprimary = 1",null);
        if (cursor != null){
            while (cursor.moveToNext()){
                City city = new City();
                city.setName(cursor.getString(cursor.getColumnIndex("name")));
                city.setAdCode(cursor.getInt(cursor.getColumnIndex("adcode")));
                list.add(city);
                weatherUtils.queryWeather(list, new onDataLoadCompletedListener() {
                    @Override
                    public void getData(List list) {
                        CollapsingUtils collapsingUtils = new CollapsingUtils(BaseActivity.this);
                        View view = collapsingUtils.getWeatherCollapsingView((Weather) list.get(0));
                        String collapsingTitle = ((Weather) list.get(0)).getCity().getName() + "  "
                                +((Weather) list.get(0)).getDayWeather();
                        changeCollapsingView(view,collapsingTitle);
                    }
                });
            }
        } else {
            String collapsingTitle = "请先设置主要城市";
            changeCollapsingView(new LinearLayout(BaseActivity.this),collapsingTitle);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        collapsingToolbarLayout.setTitle(title);
        queryWeather();
    }

    @Override
    protected void onPause() {
        super.onPause();
        title = collapsingToolbarLayout.getTitle().toString();
    }

    public void changeCollapsingView(View view,String coallpsingTitle) {
            stopRefreshAnimation(true);
            collapsingToolbarLayout.setTitle(coallpsingTitle);
            collapsingView.removeAllViews();
            collapsingView.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }
}

