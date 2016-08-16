package com.example.huanghuishun.myschedule.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.ui.fragment.ScheduleFragment;
import com.example.huanghuishun.myschedule.ui.fragment.TodayFragment;
import com.example.huanghuishun.myschedule.ui.fragment.WalletFragment;
import com.example.huanghuishun.myschedule.ui.fragment.WeatherFragment;
import com.example.huanghuishun.myschedule.utils.INaviChanger;
import com.example.huanghuishun.myschedule.utils.WeatherUtils;

import java.util.ArrayList;


/**
 * Created by huanghuishun on 2016/8/11.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,INaviChanger {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ArrayList<Fragment> fragmentList;
    private static String title;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private int[] pictureIds = new int[]{R.drawable.cloudy,R.drawable.cloudy2,R.drawable.cloudy3,R.drawable.cloudy4
            ,R.drawable.rainy2,R.drawable.rainy1,R.drawable.snow,R.drawable.sunny,R.drawable.sunny2,R.drawable.sunny3};
    FrameLayout naviView;
    WeatherUtils weatherUtils;

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
    }

    private void initView() {
        naviView = (FrameLayout) findViewById(R.id.naviView);
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

        naviView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naviView.setBackgroundResource(pictureIds[(int)(9*Math.random())]);
            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.title_refresh){

            ImageView refreshAction = (ImageView) getLayoutInflater().inflate(R.layout.action,null);
            refreshAction.setImageResource(R.drawable.refresh);
            item.setActionView(refreshAction);

            Animation animation = AnimationUtils.loadAnimation(this,R.anim.rotate);
            refreshAction.startAnimation(animation);
        }

        return super.onOptionsItemSelected(item);
    }
    /*
        private void hideRefreshAnimation() {
        if (refreshItem != null) {
            View view = refreshItem.getActionView();
            if (view != null) {
                view.clearAnimation();
                refreshItem.setActionView(null);
            }
        }
    }
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.nav_header_today));
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
            weatherUtils = new WeatherUtils(this);
            weatherUtils.setNaviChanger(this);
            weatherUtils.queryWeather("440113");
            weatherUtils.forecastWeather("440113");
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
                .replace(R.id.fragmentcontainer,fragmentList.get(index))
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    protected void onPause() {
        super.onPause();
        title = collapsingToolbarLayout.getTitle().toString();
    }

    @Override
    public void changeNaviView(View view) {
        naviView.removeAllViews();
        naviView.addView(view);
    }
}

