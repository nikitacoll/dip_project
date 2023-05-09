package com.example.dip_project.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dip_project.Class.TicketStatus;
import com.example.dip_project.R;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder>{
    List<TicketStatus> mTicketStatus;
    Listener mListener;

    public TicketAdapter(List<TicketStatus> mTicketStatus, Listener mListener) {
        this.mTicketStatus = mTicketStatus;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ticketView = inflater.inflate(R.layout.ticket_number_item,parent,false);
        return new TicketAdapter.ViewHolder(ticketView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder holder, int position) {
        String ticketNumber = mTicketStatus.get(position).getTicketName();
        String ticketStat = mTicketStatus.get(position).getStatus().trim();
        int redColor = Color.parseColor("#FF0000");
        int greenColor = Color.parseColor("#00FF19");
        holder.ticket.setText(ticketNumber);
        if(ticketStat.equals("пройден")){
            holder.status.setButtonDrawable(R.drawable.baseline_check_24);
            holder.status.setButtonTintList(ColorStateList.valueOf(greenColor));
            holder.status.setText("пройден");
        }
        else{
            holder.status.setButtonDrawable(R.drawable.baseline_close_24);
            holder.status.setButtonTintList(ColorStateList.valueOf(redColor));
            holder.status.setText("не пройден");
        }
        holder.ticket.setOnClickListener(view -> {
            mListener.onClick(ticketNumber);
        });
    }

    @Override
    public int getItemCount() {
        return mTicketStatus.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ticket;
        public RadioButton status;

        public ViewHolder(View itemView){
            super(itemView);
            ticket = itemView.findViewById(R.id.ticket_number_text_view);
            status = itemView.findViewById(R.id.ticket_number_radio_button);
        }
    }
    public interface Listener{
        void onClick(String ticket);
    }
}
