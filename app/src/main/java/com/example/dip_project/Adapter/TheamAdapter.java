package com.example.dip_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dip_project.R;

import java.util.List;

public class TheamAdapter extends RecyclerView.Adapter<TheamAdapter.ViewHolder> {
    private List<String> mTheam;
    private Listener mListener;
    public TheamAdapter(List<String> theam,Listener listener){
        mTheam = theam;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ticketView = inflater.inflate(R.layout.theam_item,parent,false);
        return new ViewHolder(ticketView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ticketNumber = mTheam.get(position);
        holder.ticket.setText(ticketNumber);
        holder.ticket.setOnClickListener(view -> {
            mListener.onClick(ticketNumber);
        });
    }

    @Override
    public int getItemCount() {
        return mTheam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ticket;

        public ViewHolder(View itemView){
            super(itemView);
            ticket = itemView.findViewById(R.id.theam_numbers);
        }
    }
    public interface Listener{
        void onClick(String theam);
    }
}
