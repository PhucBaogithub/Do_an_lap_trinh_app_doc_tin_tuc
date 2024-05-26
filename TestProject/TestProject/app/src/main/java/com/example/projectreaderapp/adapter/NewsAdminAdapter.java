package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UpdateNews;

import java.util.ArrayList;

public class NewsAdminAdapter extends RecyclerView.Adapter<NewsAdminAdapter.MyViewHolder> {
    public NewsAdminAdapter(Context context, ArrayList news_id, ArrayList news_title, ArrayList<Bitmap> imgNews, ArrayList<Bitmap> imgLogoNews, Activity activity) {
        this.context = context;
        this.news_id = news_id;
        this.news_title = news_title;
        this.imgNews = imgNews;
        this.imgLogoNews = imgLogoNews;
        this.activity = activity;
    }
    Activity activity;
    Context context;
    ArrayList news_id, news_title;
    ArrayList<Bitmap> imgNews, imgLogoNews;
    int position;



    @NonNull
    @Override
    public NewsAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_admin_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdminAdapter.MyViewHolder holder, int position) {
        holder.news_id.setText(String.valueOf(news_id.get(position)));
        holder.news_title.setText(String.valueOf(news_title.get(position)));
        holder.imgNews.setImageBitmap(imgNews.get(position));
        holder.imgLogoNews.setImageBitmap(imgLogoNews.get(position));
        holder.update_news_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNews.class);
                intent.putExtra("IDNews", String.valueOf(news_id.get(position)));
                intent.putExtra("NameNews", String.valueOf(news_title.get(position)));
                activity.startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news_id.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView news_id, news_title;
        ImageView imgNews, imgLogoNews;
        LinearLayout update_news_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            news_id=itemView.findViewById(R.id.id_news_admin_item);
            news_title=itemView.findViewById(R.id.tv_news_admin_item);
            imgNews=itemView.findViewById(R.id.img_news_admin_item);
            imgLogoNews=itemView.findViewById(R.id.img_logo_news_admin_item);
            update_news_layout=itemView.findViewById(R.id.update_news_layout);
        }
    }
}
