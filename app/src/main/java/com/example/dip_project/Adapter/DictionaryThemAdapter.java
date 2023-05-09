package com.example.dip_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dip_project.Class.DictionaryThem;
import com.example.dip_project.R;

import java.util.List;

public class DictionaryThemAdapter extends RecyclerView.Adapter<DictionaryThemAdapter.ViewHolder> {
    public List<DictionaryThem> mDictionaryThems;
    public Context mContext;

    public DictionaryThemAdapter(Context context,List<DictionaryThem> mDictionaryThems) {
        this.mDictionaryThems = mDictionaryThems;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dictionaryView = inflater.inflate(R.layout.dictionary_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(dictionaryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DictionaryThem dictionaryThem = mDictionaryThems.get(position);
        Glide.with(mContext)
                .load(dictionaryThem.getURL())
                .into(holder.UrlImage);
        holder.ThemText.setText(dictionaryThem.getText());
    }

    @Override
    public int getItemCount() {
        return mDictionaryThems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ThemText;
        public ImageView UrlImage;
        public ViewHolder(View itemView){
            super(itemView);
            ThemText = itemView.findViewById(R.id.them_text);
            UrlImage = itemView.findViewById(R.id.url_image);
        }
    }
}
