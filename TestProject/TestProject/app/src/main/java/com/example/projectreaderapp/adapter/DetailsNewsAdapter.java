package com.example.projectreaderapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.R;

import java.util.ArrayList;

public class DetailsNewsAdapter extends RecyclerView.Adapter<DetailsNewsAdapter.DetailsNewsViewHolder> {

    public DetailsNewsAdapter(Context context, ArrayList<Bitmap> imageNewsDetails, ArrayList newsTitle, ArrayList newsDetails1, ArrayList newsDetails2) {
        this.context = context;
        ImageNewsDetails = imageNewsDetails;
        NewsTitle = newsTitle;
        NewsDetails1 = newsDetails1;
        NewsDetails2 = newsDetails2;
    }

    Context context;
    ArrayList<Bitmap> ImageNewsDetails;
    ArrayList NewsTitle, NewsDetails1, NewsDetails2;

    @NonNull
    @Override
    public DetailsNewsAdapter.DetailsNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemdetailsnews, parent, false);

        return new DetailsNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsNewsAdapter.DetailsNewsViewHolder holder, int position) {
        holder.NewsTitle.setText(String.valueOf(NewsTitle.get(position)));
        holder.NewsDetails1.setText(String.valueOf(NewsDetails1.get(position)));
        holder.NewsDetails2.setText(String.valueOf(NewsDetails2.get(position)));
        holder.ImageNewsDetails.setImageBitmap(ImageNewsDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return NewsTitle.size();
    }

    public class DetailsNewsViewHolder extends RecyclerView.ViewHolder {

        TextView NewsTitle, NewsDetails1, NewsDetails2;
        ImageView ImageNewsDetails;
        public DetailsNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            NewsTitle=itemView.findViewById(R.id.tv_name_details_news_title);
            NewsDetails1=itemView.findViewById(R.id.tv_name_details_news_display1);
            NewsDetails2=itemView.findViewById(R.id.tv_name_details_news_display2);
            ImageNewsDetails=itemView.findViewById(R.id.img_news_display_detail);
        }
    }
}
