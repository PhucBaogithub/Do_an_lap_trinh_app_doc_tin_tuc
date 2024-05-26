package com.example.projectreaderapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.DisplayDetailsNews;
import com.example.projectreaderapp.R;

import java.util.ArrayList;

public class NewsDisplayAdapter extends RecyclerView.Adapter<NewsDisplayAdapter.MyDisplayViewHolder> {

    public NewsDisplayAdapter(Context context, ArrayList<Bitmap> imgNews, ArrayList<Bitmap> imgLogoNews, ArrayList nameNews, ArrayList idNews) {
        this.context = context;
        this.imgNews = imgNews;
        this.imgLogoNews = imgLogoNews;
        this.nameNews = nameNews;
        this.idNews = idNews;
    }

    private Context context;
    private ArrayList<Bitmap> imgNews, imgLogoNews;
    private ArrayList  nameNews, idNews;

    @NonNull
    @Override
    public NewsDisplayAdapter.MyDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemnewsdisplay, parent, false);

        return new MyDisplayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsDisplayAdapter.MyDisplayViewHolder holder, int position) {
        holder.nameNews.setText(String.valueOf(nameNews.get(position)));
        holder.idNews.setText(String.valueOf(idNews.get(position)));
        holder.imgNews.setImageBitmap(imgNews.get(position));
        holder.imgLogoNews.setImageBitmap(imgLogoNews.get(position));
        holder.item_news_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisplayDetailsNews.class);
                Integer i = Integer.parseInt(String.valueOf(idNews.get(position)));
                Bundle bundle = new Bundle();
                bundle.putInt("IDnews", i);
                intent.putExtra("mybundle1", bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idNews.size();
    }

    public class MyDisplayViewHolder extends RecyclerView.ViewHolder{

            TextView nameNews, idNews;
            ImageView imgNews, imgLogoNews;
            CardView item_news_display;

            public MyDisplayViewHolder(@NonNull View itemView) {
            super(itemView);
            nameNews=itemView.findViewById(R.id.tv_name_news_display);
            idNews=itemView.findViewById(R.id.tv_id_news_display);
            imgNews=itemView.findViewById(R.id.img_news_display);
            imgLogoNews=itemView.findViewById(R.id.img_logo_news_display);
            item_news_display=itemView.findViewById(R.id.item_news_display);
        }
    }
}
