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
import com.example.projectreaderapp.UpdateGiaVang;

import java.util.ArrayList;

public class GiaVangAdapter extends RecyclerView.Adapter<GiaVangAdapter.GiaVangViewHolder> {

    public GiaVangAdapter(Activity activity, Context context, ArrayList nameCountry, ArrayList buy, ArrayList sold, ArrayList<Bitmap> logo) {
        this.activity = activity;
        this.context = context;
        NameCountry = nameCountry;
        Buy = buy;
        Sold = sold;
        Logo = logo;
    }

    Activity activity;
    Context context;
    ArrayList NameCountry, Buy, Sold;
    ArrayList<Bitmap> Logo;

    @NonNull
    @Override
    public GiaVangAdapter.GiaVangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items, parent, false);
        return new GiaVangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaVangAdapter.GiaVangViewHolder holder, int position) {
        holder.tvQuocGia_admin.setText(String.valueOf(NameCountry.get(position)));
        holder.txtGiamua_admin.setText(String.valueOf(Buy.get(position)));
        holder.txtGiaban_admin.setText(String.valueOf(Sold.get(position)));
        holder.ivCoQuocGia_admin.setImageBitmap(Logo.get(position));
    }

    @Override
    public int getItemCount() {
        return NameCountry.size();
    }

    public class GiaVangViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuocGia_admin, txtGiamua_admin, txtGiaban_admin;
        ImageView ivCoQuocGia_admin;

        public GiaVangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuocGia_admin=itemView.findViewById(R.id.gvQuocGia);
            txtGiamua_admin=itemView.findViewById(R.id.txtGiamua);
            txtGiaban_admin=itemView.findViewById(R.id.txtGiaban);
            ivCoQuocGia_admin=itemView.findViewById(R.id.ivCoQuocGia);
        }
    }
}
