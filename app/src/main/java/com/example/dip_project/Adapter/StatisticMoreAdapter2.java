package com.example.dip_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dip_project.Class.UserMoreStatistic;
import com.example.dip_project.R;

import java.util.ArrayList;

public class StatisticMoreAdapter2 extends RecyclerView.Adapter<StatisticMoreAdapter2.ViewHolder>{
    ArrayList<UserMoreStatistic> sStatistic;

    public StatisticMoreAdapter2(ArrayList<UserMoreStatistic> sStatistic) {
        this.sStatistic = sStatistic;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ticketView = inflater.inflate(R.layout.statistic_more_item,parent,false);
        return new StatisticMoreAdapter2.ViewHolder(ticketView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String statisticTitle;

        statisticTitle = sStatistic.get(position).getDate() + sStatistic.get(position).getValue();

        holder.statistic.setText(statisticTitle);
    }

    @Override
    public int getItemCount() {
        return sStatistic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView statistic;

        public ViewHolder(View itemView){
            super(itemView);
            statistic = itemView.findViewById(R.id.statistic_more_text_view);
        }
    }
}
