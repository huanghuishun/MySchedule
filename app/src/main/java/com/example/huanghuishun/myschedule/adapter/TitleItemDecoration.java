package com.example.huanghuishun.myschedule.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.example.huanghuishun.myschedule.entity.City;
import com.example.huanghuishun.myschedule.entity.Weather;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.List;

/**
 * Created by huanghuishun on 2016/10/26.
 */

/*
ItemDecoration 是RecyclerView的分割线使用的类
通过mRecyclerView.addItemDecoration（ItemDecoration）来使用

 */

public class TitleItemDecoration extends RecyclerView.ItemDecoration {
    private List<City> cityList;
    private List<Weather> weatherList;
    private Paint paint;
    private Rect rect;

    private static int COLOR_TITLE_BG = Color.parseColor("#DDDFDFDF");  //背景颜色
    private static int COLOR_TITLE_FRONT = Color.parseColor("#FF000000");  //字体颜色

    private int titleHeight;
    private int titleFrontSize;

    /*
    初始化构造器，注入相关数据list和context
    初始化画笔paint用于view绘制
    初始化矩形rect用于摆放textView
    初始化标题栏高度为30dp
    初始化标题字体为16sp
     */
    public TitleItemDecoration(Context context, List<Weather> weatherList, List<City> cityList) {
        super();
        this.weatherList = weatherList;
        this.cityList = cityList;
        paint = new Paint();
        rect = new Rect();
        titleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        titleFrontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        paint.setTextSize(titleFrontSize);
        paint.setAntiAlias(true);   //抗锯齿
    }

    /*
    需要重写的方法
    按照在RecyclerView中它们被调用的顺序排列：
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state)
     */


    /*
    利用parent和state变量获取绘制的上下做欲呕，childCount，childView等信息
    最后调用Canvas绘制出UI
    DonDraw绘制的内容在ItemView下层，可以超出Rect区域，但是不会显示

    在这里通过parent获取左右的padding和子View数量
    然后遍历子View，当子view满足情况的时候绘制标题
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            char currentChar = 1;
            char preChar = 0;
            if (position > weatherList.size()+1){
                currentChar = Pinyin.toPinyin(cityList.get(position - weatherList.size()-1).getName().charAt(0)).charAt(0);
                preChar =  Pinyin.toPinyin(cityList.get(position - weatherList.size() - 2).getName().charAt(0)).charAt(0);
            }
            if (position > -1) {
                //子view满足下列情况绘制标题（1.第一个；2.切换栏目；3.拼音不同）
                if (position == 0 || position == weatherList.size() || position == weatherList.size()+1 || currentChar != preChar) {
                    drawTitleArea(c, left, right, child, params, position);
                }
            }
        }
    }

    /*
    此方法用于绘制title文字和背景
     */
    private void drawTitleArea(Canvas canvas, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        paint.setColor(COLOR_TITLE_BG);
        //canvas.drawRect()
        canvas.drawRect(left, child.getTop() - params.topMargin - titleHeight, right, child.getTop() - params.topMargin, paint);
        paint.setColor(COLOR_TITLE_FRONT);
        //在这里根据position绘制不同的title
        if (position < weatherList.size()) {
            paint.getTextBounds("我的", 0, 2, rect);
            canvas.drawText("我的", 16.0f, child.getTop() - params.topMargin - (titleHeight / 2 - rect.height() / 2), paint);
        } else if (position == weatherList.size()) {
            paint.getTextBounds("定位", 0, 2, rect);
            canvas.drawText("定位", 16.0f, child.getTop() - params.topMargin - (titleHeight / 2 - rect.height() / 2), paint);
        } else {
            paint.getTextBounds(Pinyin.toPinyin(cityList.get(position - weatherList.size()).getName().charAt(0)).charAt(0)+"", 0, 1, rect);
            canvas.drawText(Pinyin.toPinyin(cityList.get(position - weatherList.size()).getName().charAt(0)).charAt(0)+"", 16.0f, child.getTop() - params.topMargin - (titleHeight / 2 - rect.height() / 2), paint);
        }
    }

    /*
    此方法用于绘制覆盖itemview的title
    首先通过parent获取LayoutManager（由于悬停分组列表的特殊性，写死了是LinearLayoutManger）
    然后获取当前第一个可见itemView以及position以及它所属的分类title
    然后绘制悬停View的背景和文字
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        String title = "我的";
        if (position < weatherList.size()) {

        } else if (position == weatherList.size()) {
            title = "定位";
        } else {
            title = Pinyin.toPinyin(cityList.get(position - weatherList.size()-1).getName().charAt(0)).charAt(0) + "";
        }
        View child = parent.findViewHolderForLayoutPosition(position).itemView;
        paint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getTop() + titleHeight, paint);
        paint.setColor(COLOR_TITLE_FRONT);
        paint.getTextBounds(title, 0, title.length(), rect);
        c.drawText(title, 16.0f, parent.getPaddingTop() + titleHeight - (titleHeight / 2 - rect.height() / 2), paint);

    }

    /*
    我们需要利用 parent和state变量，来获取需要的辅助信息
    例如position， 最终调用outRect.set(int left, int top, int right, int bottom)方法
    设置四个方向上 需要为ItemView设置padding的值。
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        char currentChar = 0;
        char preChar = 0;
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > weatherList.size()+1){
            currentChar = Pinyin.toPinyin(cityList.get(position - weatherList.size()-1).getName().charAt(0)).charAt(0);
            preChar = Pinyin.toPinyin(cityList.get(position - weatherList.size() - 2).getName().charAt(0)).charAt(0);
        }
        if (position > -1) {
            if (position == 0 || position == weatherList.size() || position == weatherList.size()+1 || currentChar != preChar) {
                outRect.set(0, titleHeight, 0, 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }
}
