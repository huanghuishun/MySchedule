package com.example.huanghuishun.myschedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huanghuishun.myschedule.R;
import com.example.huanghuishun.myschedule.entity.City;

import java.util.List;

/**
 * Created by huanghuishun on 2016/8/23.
 */
public class SearchResultRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<City> searchList;

    public SearchResultRVAdapter(Context context, List<City> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.locationitem_search,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String result = searchList.get(position).getName()+"   "+searchList.get(position).getLocation();
        ((SearchViewHolder)holder).tv_place.setText(result);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView tv_place;
        public SearchViewHolder(View itemView) {
            super(itemView);
            tv_place = (TextView) itemView.findViewById(R.id.item4_tv_place);
        }
    }
}
