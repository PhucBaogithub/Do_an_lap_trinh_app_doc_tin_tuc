package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.DBHelper;
import com.example.projectreaderapp.DisplayDetailsNews;
import com.example.projectreaderapp.model.News;
import com.example.projectreaderapp.R;

import java.util.ArrayList;
import java.util.List;

import tablayout.NongFM;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements Filterable {

    private Context mContext;
    private Activity activity;
    private List<News> mListNews;
    private List<News> mListNewsOld;

    public NewsAdapter(Context mContext, Activity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    public void setData(List<News> list){
        this.mListNews = list;
        this.mListNewsOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemnews, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = mListNews.get(position);
        if(news == null){
            return;
        }
        else{
            holder.tv_news_id.setText(news.getIdnews());
            holder.tvNews.setText(news.getName());
            holder.tv_news_author.setText(news.getNameauthor());
            holder.imgNews.setImageBitmap(news.getResourceId());
            holder.img_logo_news.setImageBitmap(news.getResourceId2());
            holder.item_news.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper dbHelper = new DBHelper(mContext);
                    Boolean checkIDNews = dbHelper.checkDetailsNews(Integer.parseInt(String.valueOf(news.getIdnews())));
                    if(checkIDNews==true){
                        Intent intent = new Intent(mContext, DisplayDetailsNews.class);
                        Integer i = Integer.parseInt(String.valueOf(news.getIdnews()));
                        Bundle bundle = new Bundle();
                        bundle.putInt("IDnews", i);
                        intent.putExtra("mybundle1", bundle);
                        activity.startActivityForResult(intent,5);
                    }else{
                        Toast.makeText(mContext, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mListNews!=null){
            return mListNews.size();
        }
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgNews, img_logo_news;
        private TextView tvNews, tv_news_id, tv_news_author;
        private CardView item_news;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNews = itemView.findViewById(R.id.img_news);
            tvNews = itemView.findViewById(R.id.tv_news);
            img_logo_news = itemView.findViewById(R.id.img_logo_news);
            tv_news_id = itemView.findViewById(R.id.tv_news_id);
            tv_news_author = itemView.findViewById(R.id.tv_news_author);
            item_news = itemView.findViewById(R.id.item_news);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    mListNews = mListNewsOld;
                }else{
                    List<News> list = new ArrayList<>();
                    for(News news : mListNewsOld){
                        if(news.getName().toString().toLowerCase().contains(strSearch.toLowerCase()) || news.getNameauthor().toString().toLowerCase().contains(strSearch.toLowerCase()) || ((news.getName().toString().toLowerCase() + " " + news.getNameauthor().toString().toLowerCase()).contains(strSearch.toLowerCase()))){
                            list.add(news);
                        }
                    }
                    mListNews = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListNews;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListNews = (List<News>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
