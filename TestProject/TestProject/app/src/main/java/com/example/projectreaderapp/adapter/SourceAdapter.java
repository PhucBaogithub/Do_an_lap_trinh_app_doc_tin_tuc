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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UpdateSourceNews;

import java.util.ArrayList;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceViewHolder> {

    public SourceAdapter(Activity activity, Context context, ArrayList<Bitmap> logo) {
        this.activity = activity;
        this.context = context;
        this.logo = logo;
    }

    Activity activity;
    Context context;
    ArrayList<Bitmap> logo;

    @NonNull
    @Override
    public SourceAdapter.SourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemsourcenewsuser, parent, false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceAdapter.SourceViewHolder holder, int position) {
        holder.img_logo_source_name.setImageBitmap(logo.get(position));
    }

    @Override
    public int getItemCount() {
        return logo.size();
    }

    public class SourceViewHolder extends RecyclerView.ViewHolder {
        ImageView img_logo_source_name;
        public SourceViewHolder(@NonNull View itemView) {
            super(itemView);
            img_logo_source_name=itemView.findViewById(R.id.img_logo_source_name);
        }
    }
}
