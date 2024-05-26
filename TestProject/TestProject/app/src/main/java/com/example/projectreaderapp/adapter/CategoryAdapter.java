package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.MainActivity;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.TranslateAnimationUtil;
import com.example.projectreaderapp.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context mContext;
    private Activity activity;
    private List<Category> mListCategory;

    public CategoryAdapter(Context mContext,Activity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    public void setData(List<Category> list){
        this.mListCategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if(category==null){
            return;
        }else{
            holder.tvNameCategory.setText(category.getNameCategory());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            holder.rvcNews.setLayoutManager(linearLayoutManager);

            NewsAdapter newsAdapter = new NewsAdapter(mContext,activity);
            newsAdapter.setData(category.getNews());
            holder.rvcNews.setOnTouchListener(new TranslateAnimationUtil(mContext, MainActivity.bottomNavigationView));
            holder.rvcNews.setAdapter(newsAdapter);
        }
    }

    @Override
    public int getItemCount() {
        if(mListCategory!=null){
            return mListCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameCategory;
        private RecyclerView rvcNews;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameCategory = itemView.findViewById(R.id.tv_name_category);
            rvcNews = itemView.findViewById(R.id.rcv_news);
        }
    }
}
