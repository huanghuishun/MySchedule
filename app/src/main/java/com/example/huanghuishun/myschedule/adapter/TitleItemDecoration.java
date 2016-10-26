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
 * Created by Administrator on 2016/10/26.
 */
public class TitleItemDecoration extends RecyclerView.ItemDecoration {
    private List<City> cityList;
    private List<Weather> weatherList;
    private Paint paint;
    private Rect rect;

    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FRONT = Color.parseColor("FF000000");

    private int titleHeight;
    private int titleFrontSize;

    public TitleItemDecoration(Context context, List<Weather> weatherList, List<City> cityList) {
        super();
        this.weatherList = weatherList;
        this.cityList = cityList;
        paint = new Paint();
        rect = new Rect();
        titleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        titleFrontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        paint.setTextSize(titleFrontSize);
        paint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            char currentChar = Pinyin.toPinyin(cityList.get(position).getName().charAt(0)).charAt(0);
            char preChar = (position - 1) >= 0 ? Pinyin.toPinyin(cityList.get(position - 1).getName().charAt(0)).charAt(0) : '0';
            if (position > -1) {
                if (position == 0 || position == weatherList.size() || currentChar != preChar) {
                    drawTitleArea(c, left, right, child, params, position);
                }
            }
        }
    }

    private void drawTitleArea(Canvas canvas, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        paint.setColor(COLOR_TITLE_BG);
        canvas.drawRect(left, child.getTop() - params.topMargin - titleHeight, right, child.getTop() - params.topMargin, paint);
        paint.setColor(COLOR_TITLE_FRONT);
        if (position < weatherList.size()) {
            paint.getTextBounds("我的", 0, 2, rect);
            canvas.drawText("我的", child.getPaddingLeft(), child.getTop() - params.topMargin - (titleHeight / 2 - rect.height() / 2), paint);
        } else if (position == weatherList.size()) {
            paint.getTextBounds("定位", 0, 2, rect);
            canvas.drawText("定位", child.getPaddingLeft(), child.getTop() - params.topMargin - (titleHeight / 2 - rect.height() / 2), paint);
        } else {
            paint.getTextBounds(Pinyin.toPinyin(cityList.get(position - weatherList.size()).getName().charAt(0)), 0, 1, rect);
            canvas.drawText(Pinyin.toPinyin(cityList.get(position - weatherList.size()).getName().charAt(0)), child.getPaddingLeft(), child.getTop() - params.topMargin - (titleHeight / 2 - rect.height() / 2), paint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        String title = "我的";
        if (position < weatherList.size()) {

        } else if (position == weatherList.size()) {
            title = "定位";
        } else {
            title = Pinyin.toPinyin(cityList.get(position - weatherList.size()).getName().charAt(0)).charAt(0) + "";
        }
        View child = parent.findViewHolderForLayoutPosition(position).itemView;
        paint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getTop() + titleHeight, paint);
        paint.setColor(COLOR_TITLE_FRONT);
        paint.getTextBounds(title, 0, title.length(), rect);
        c.drawText(title, child.getPaddingLeft(), parent.getPaddingTop() + titleHeight - (titleHeight / 2 - rect.height() / 2), paint);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        char currentChar = Pinyin.toPinyin(cityList.get(position).getName().charAt(0)).charAt(0);
        char preChar = (position - 1) >= 0 ? Pinyin.toPinyin(cityList.get(position - 1).getName().charAt(0)).charAt(0) : '0';
        if (position > -1) {
            if (position == 0 || position == weatherList.size() || currentChar != preChar) {
                outRect.set(0, titleHeight, 0, 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }
}
