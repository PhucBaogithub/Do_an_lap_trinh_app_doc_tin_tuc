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
import com.example.projectreaderapp.UpdateGiaVang;
import com.example.projectreaderapp.UpdateXoso;

import java.util.ArrayList;

public class AdminXosoAdapter extends RecyclerView.Adapter<AdminXosoAdapter.AdminXosoViewHolder> {

    public AdminXosoAdapter(Activity activity, Context context, ArrayList ID, ArrayList nameCountry, ArrayList PS, ArrayList p1, ArrayList p2, ArrayList p3, ArrayList p4, ArrayList p5, ArrayList p6, ArrayList p7, ArrayList p8) {
        this.activity = activity;
        this.context = context;
        this.ID = ID;
        NameCountry = nameCountry;
        this.PS = PS;
        P1 = p1;
        P2 = p2;
        P3 = p3;
        P4 = p4;
        P5 = p5;
        P6 = p6;
        P7 = p7;
        P8 = p8;
    }

    Activity activity;
    Context context;
    ArrayList ID, NameCountry, PS, P1, P2, P3, P4, P5, P6, P7, P8;

    @NonNull
    @Override
    public AdminXosoAdapter.AdminXosoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemxoso, parent, false);
        return new AdminXosoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminXosoAdapter.AdminXosoViewHolder holder, int position) {
        holder.ID.setText(String.valueOf(ID.get(position)));
        holder.NameCountry.setText(String.valueOf(NameCountry.get(position)));
        holder.PS.setText(String.valueOf(PS.get(position)));
        holder.P1.setText(String.valueOf(P1.get(position)));
        holder.P2.setText(String.valueOf(P2.get(position)));
        holder.P3.setText(String.valueOf(P3.get(position)));
        holder.P4.setText(String.valueOf(P4.get(position)));
        holder.P5.setText(String.valueOf(P5.get(position)));
        holder.P6.setText(String.valueOf(P6.get(position)));
        holder.P7.setText(String.valueOf(P7.get(position)));
        holder.P8.setText(String.valueOf(P8.get(position)));
        holder.cv_xoso_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateXoso.class);
                intent.putExtra("ID", String.valueOf(ID.get(position)));
                intent.putExtra("NameCountry", String.valueOf(NameCountry.get(position)));
                intent.putExtra("PS", String.valueOf(PS.get(position)));
                intent.putExtra("P1", String.valueOf(P1.get(position)));
                intent.putExtra("P2", String.valueOf(P2.get(position)));
                intent.putExtra("P3", String.valueOf(P3.get(position)));
                intent.putExtra("P4", String.valueOf(P4.get(position)));
                intent.putExtra("P5", String.valueOf(P5.get(position)));
                intent.putExtra("P6", String.valueOf(P6.get(position)));
                intent.putExtra("P7", String.valueOf(P7.get(position)));
                intent.putExtra("P8", String.valueOf(P8.get(position)));
                activity.startActivityForResult(intent,8);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ID.size();
    }

    public class AdminXosoViewHolder extends RecyclerView.ViewHolder {
        TextView ID, NameCountry, PS, P1, P2, P3, P4, P5, P6, P7, P8;
        CardView cv_xoso_layout;
        public AdminXosoViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.tv_id);
            NameCountry=itemView.findViewById(R.id.tv_namecountry);
            PS=itemView.findViewById(R.id.tv_priceS);
            P1=itemView.findViewById(R.id.tv_price1);
            P2=itemView.findViewById(R.id.tv_price2);
            P3=itemView.findViewById(R.id.tv_price3);
            P4=itemView.findViewById(R.id.tv_price4);
            P5=itemView.findViewById(R.id.tv_price5);
            P6=itemView.findViewById(R.id.tv_price6);
            P7=itemView.findViewById(R.id.tv_price7);
            P8=itemView.findViewById(R.id.tv_price8);
            cv_xoso_layout=itemView.findViewById(R.id.cv_xoso_layout);
        }
    }
}
