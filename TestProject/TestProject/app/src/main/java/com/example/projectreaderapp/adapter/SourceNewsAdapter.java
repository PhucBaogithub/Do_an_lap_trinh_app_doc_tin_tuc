package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.ConfirmEmail;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UpdateDetailsNews;
import com.example.projectreaderapp.UpdateSourceNews;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SourceNewsAdapter extends RecyclerView.Adapter<SourceNewsAdapter.SourceNewsViewHolder> {

    public SourceNewsAdapter(Activity activity, Context context, ArrayList id, ArrayList name, ArrayList<Bitmap> logo) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.logo = logo;
    }

    Activity activity;
    Context context;
    ArrayList id, name;
    ArrayList<Bitmap> logo;

    @NonNull
    @Override
    public SourceNewsAdapter.SourceNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemsourcenews, parent, false);
        return new SourceNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceNewsAdapter.SourceNewsViewHolder holder, int position) {
        holder.tv_source_id.setText(String.valueOf(id.get(position)));
        holder.tv_source_name.setText(String.valueOf(name.get(position)));
        holder.img_logo_source_name.setImageBitmap(logo.get(position));
        holder.cv_source_news_update_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateSourceNews.class);
                intent.putExtra("ID", String.valueOf(id.get(position)));
                intent.putExtra("Title", String.valueOf(name.get(position)));
                activity.startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class SourceNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView img_logo_source_name;
        TextView tv_source_name, tv_source_id;
        CardView cv_source_news_update_layout;
        public SourceNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            img_logo_source_name=itemView.findViewById(R.id.img_logo_source_name);
            tv_source_name=itemView.findViewById(R.id.tv_source_name);
            tv_source_id=itemView.findViewById(R.id.tv_source_id);
            cv_source_news_update_layout=itemView.findViewById(R.id.cv_source_news_update_layout);
        }
    }
}
