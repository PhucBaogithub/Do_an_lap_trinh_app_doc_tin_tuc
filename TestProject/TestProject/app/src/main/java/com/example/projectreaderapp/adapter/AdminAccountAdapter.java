package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UpdateAdminAccount;

import java.util.ArrayList;

public class AdminAccountAdapter extends RecyclerView.Adapter<AdminAccountAdapter.MyViewHoler> {

    private Context context;
    Activity activity;
    private ArrayList IDAdmin, UserName, Password, RoleAdmin;
    int position;


    public AdminAccountAdapter(Activity activity, Context context, ArrayList IDAdmin, ArrayList userName, ArrayList password, ArrayList roleAdmin) {
        this.context = context;
        this.IDAdmin = IDAdmin;
        UserName = userName;
        Password = password;
        RoleAdmin = roleAdmin;
        this.activity=activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminAccountAdapter.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adminaccount , parent, false);
        return new AdminAccountAdapter.MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAccountAdapter.MyViewHoler holder, int position) {
        holder.IDAdmin.setText(String.valueOf(IDAdmin.get(position)));
        holder.UserName.setText(String.valueOf(UserName.get(position)));
        holder.Password.setText(String.valueOf(Password.get(position)));
        holder.RoleAdmin.setText(String.valueOf(RoleAdmin.get(position)));
        holder.item_adminaccount_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateAdminAccount.class);
                intent.putExtra("IDAdmin", String.valueOf(IDAdmin.get(position)));
                intent.putExtra("UserName", String.valueOf(UserName.get(position)));
                intent.putExtra("Password", String.valueOf(Password.get(position)));
                intent.putExtra("RoleAdmin",String.valueOf(RoleAdmin.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return IDAdmin.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder{
        TextView IDAdmin, UserName, Password, RoleAdmin;
        LinearLayout item_adminaccount_layout;
        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            IDAdmin=itemView.findViewById(R.id.txtIDAdmin);
            UserName=itemView.findViewById(R.id.txtUserNameAdmin);
            Password=itemView.findViewById(R.id.txtPasswordAdmin);
            RoleAdmin=itemView.findViewById(R.id.txtRoleAdmin);
            item_adminaccount_layout=itemView.findViewById(R.id.item_adminaccount_layout);
        }
    }
}
