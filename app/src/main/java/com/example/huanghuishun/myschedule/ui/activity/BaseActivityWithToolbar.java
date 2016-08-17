package com.example.huanghuishun.myschedule.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.huanghuishun.myschedule.R;

/**
 * Created by huanghuishun on 2016/8/17.
 */
public class BaseActivityWithToolbar extends AppCompatActivity {
    private Toolbar toolbar;
    private CoordinatorLayout contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //attr属性
        int[] attrs = new int[]{R.attr.windowActionBarOverlay,R.attr.actionBarSize};
        //获取coordinator
        contentView = (CoordinatorLayout) LayoutInflater.from(this).inflate(R.layout.toolbar_base,null);
        toolbar = (Toolbar) contentView.findViewById(R.id.base_toolbar111);
        //获取MainActivity中的contentview的id
        ViewGroup userView = (ViewGroup) LayoutInflater.from(this).inflate(layoutResID,null);

        //设置marginTop
        ViewGroup.MarginLayoutParams marginLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = this.getTheme().obtainStyledAttributes(attrs);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
        int toolBarSize = (int) typedArray.getDimension(1,(int) this.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
        marginLayoutParams.topMargin = overly ? 0 : toolBarSize;

        contentView.addView(userView, marginLayoutParams);
        super.setContentView(contentView);

        initToolbar();
    }

    private void initToolbar() {
   //     toolbar = (Toolbar) findViewById(R.id.base_toolbar111);
        toolbar.setTitle("hello");
        setSupportActionBar(toolbar);

    }

    public void isBackButtonEnabled(boolean isEnabled) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
