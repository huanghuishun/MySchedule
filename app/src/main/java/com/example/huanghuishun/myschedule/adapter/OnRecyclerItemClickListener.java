package com.example.huanghuishun.myschedule.adapter;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by huanghuishun on 2016/8/23.
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(),new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    public abstract void onItemClick(RecyclerView.ViewHolder holder);
//    public abstract void onLongClick(RecyclerView.ViewHolder holder);

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
            if (child != null){
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(child);
                holder.itemView.onTouchEvent(e);
                onItemClick(holder);
            }
            return true;
        }
        /*
        1. selector的问题，直接将item布局设置android:clickable="true"，然后在ItemTouchHelperGestureListener类的两个方法中将事件传递给itemview：vh.itemView.onTouchEvent(e);
         */

//        @Override
//        public void onLongPress(MotionEvent e) {
//            View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
//            if (child != null){
//                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(child);
//                holder.itemView.onTouchEvent(e);
//                onLongClick(holder);
//            }
//        }
    }
}

