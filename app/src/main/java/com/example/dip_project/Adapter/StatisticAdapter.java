package com.example.dip_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dip_project.Class.UserStatistic;
import com.example.dip_project.R;

import java.util.ArrayList;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {
    ArrayList<UserStatistic> mStatistic;
    Listener mListener;

    public StatisticAdapter(ArrayList<UserStatistic> mStatistic, Listener mListener) {
        this.mStatistic = mStatistic;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ticketView = inflater.inflate(R.layout.statistick_item,parent,false);
        return new StatisticAdapter.ViewHolder(ticketView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String statisticTitle = mStatistic.get(position).getStatisticTitle();
        holder.statistic.setText(statisticTitle);
        holder.statistic.setOnClickListener(view -> {
            mListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return mStatistic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView statistic;

        public ViewHolder(View itemView){
            super(itemView);
            statistic = itemView.findViewById(R.id.statistic_title);
        }
    }
    public interface Listener{
        void onClick(int statistic);
    }
}
