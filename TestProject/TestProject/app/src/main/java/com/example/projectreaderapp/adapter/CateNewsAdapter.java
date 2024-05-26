package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UpdateCateNews;
import com.example.projectreaderapp.UpdateSourceNews;

import java.util.ArrayList;

public class CateNewsAdapter extends RecyclerView.Adapter<CateNewsAdapter.CateNewsViewHolder> {

    public CateNewsAdapter(Activity activity, Context context, ArrayList id, ArrayList name) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
    }

    Activity activity;
    Context context;
    ArrayList id,name;



    @NonNull
    @Override
    public CateNewsAdapter.CateNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemcatenews, parent, false);
        return new CateNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CateNewsAdapter.CateNewsViewHolder holder, int position) {
        holder.tv_cate_news_id.setText(String.valueOf(id.get(position)));
        holder.tv_cate_news_name.setText(String.valueOf(name.get(position)));
        holder.cv_cate_news_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateCateNews.class);
                intent.putExtra("ID", String.valueOf(id.get(position)));
                intent.putExtra("Name", String.valueOf(name.get(position)));
                activity.startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class CateNewsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cate_news_id, tv_cate_news_name;
        CardView cv_cate_news_layout;
        public CateNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_cate_news_id=itemView.findViewById(R.id.tv_cate_news_id);
            tv_cate_news_name=itemView.findViewById(R.id.tv_cate_news_name);
            cv_cate_news_layout=itemView.findViewById(R.id.cv_cate_news_layout);
        }
    }
}
