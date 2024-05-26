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

public class AdminGiaVangAdapter extends RecyclerView.Adapter<AdminGiaVangAdapter.AdminGiaVangViewHolder> {

    public AdminGiaVangAdapter(Activity activity, Context context, ArrayList ID, ArrayList nameCountry, ArrayList buy, ArrayList sold, ArrayList<Bitmap> logo) {
        this.activity = activity;
        this.context = context;
        this.ID = ID;
        NameCountry = nameCountry;
        Buy = buy;
        Sold = sold;
        Logo = logo;
    }

    Activity activity;
    Context context;
    ArrayList ID, NameCountry, Buy, Sold;
    ArrayList<Bitmap> Logo;

    @NonNull
    @Override
    public AdminGiaVangAdapter.AdminGiaVangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_gold_price_admin, parent, false);
        return new AdminGiaVangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminGiaVangAdapter.AdminGiaVangViewHolder holder, int position) {
        holder.txt_id_gold_price_admin.setText(String.valueOf(ID.get(position)));
        holder.tvQuocGia_admin.setText(String.valueOf(NameCountry.get(position)));
        holder.txtGiamua_admin.setText(String.valueOf(Buy.get(position)));
        holder.txtGiaban_admin.setText(String.valueOf(Sold.get(position)));
        holder.ivCoQuocGia_admin.setImageBitmap(Logo.get(position));
        holder.id_cardview_layout_gold_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateGiaVang.class);
                intent.putExtra("ID", String.valueOf(ID.get(position)));
                intent.putExtra("NameCountry", String.valueOf(NameCountry.get(position)));
                intent.putExtra("Buy", String.valueOf(Buy.get(position)));
                intent.putExtra("Sold", String.valueOf(Sold.get(position)));
                activity.startActivityForResult(intent,8);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ID.size();
    }

    public class AdminGiaVangViewHolder extends RecyclerView.ViewHolder {

        TextView txt_id_gold_price_admin, tvQuocGia_admin, txtGiamua_admin, txtGiaban_admin;
        ImageView ivCoQuocGia_admin;
        CardView id_cardview_layout_gold_price;

        public AdminGiaVangViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id_gold_price_admin=itemView.findViewById(R.id.txt_id_gold_price_admin);
            tvQuocGia_admin=itemView.findViewById(R.id.tvQuocGia_admin);
            txtGiamua_admin=itemView.findViewById(R.id.txtGiamua_admin);
            txtGiaban_admin=itemView.findViewById(R.id.txtGiaban_admin);
            ivCoQuocGia_admin=itemView.findViewById(R.id.ivCoQuocGia_admin);
            id_cardview_layout_gold_price=itemView.findViewById(R.id.id_cardview_layout_gold_price);
        }
    }
}
