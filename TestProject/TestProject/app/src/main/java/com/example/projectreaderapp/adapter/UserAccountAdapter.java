package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.AccountUserManager;
import com.example.projectreaderapp.DeleteUser;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UIEmployee;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class UserAccountAdapter extends RecyclerView.Adapter<UserAccountAdapter.MyViewHoler> {

    public UserAccountAdapter(Activity activity, Context context, ArrayList IDUser, ArrayList userName, ArrayList fullname, ArrayList email, ArrayList phonemumber, ArrayList password, ArrayList<Bitmap> imgUser) {
        this.context = context;
        this.IDUser = IDUser;
        UserName = userName;
        Fullname = fullname;
        Email = email;
        Phonemumber = phonemumber;
        Password = password;
        this.imgUser = imgUser;
        this.activity=activity;
        notifyDataSetChanged();
    }

    private Context context;
    Activity activity;
    private ArrayList IDUser, UserName, Fullname, Email, Phonemumber, Password;
    private ArrayList<Bitmap> imgUser;



    @NonNull
    @Override
    public UserAccountAdapter.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_account_user , parent, false);
        return new UserAccountAdapter.MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAccountAdapter.MyViewHoler holder, int position) {
        holder.IDUser.setText(String.valueOf(IDUser.get(position)));
        holder.UserName.setText(String.valueOf(UserName.get(position)));
        holder.Fullname.setText(String.valueOf(Fullname.get(position)));
        holder.Email.setText(String.valueOf(Email.get(position)));
        holder.Phonemumber.setText(String.valueOf(Phonemumber.get(position)));
        holder.Password.setText(String.valueOf(Password.get(position)));

        holder.imgUser.setImageBitmap(imgUser.get(position));

        holder.user_account_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeleteUser.class);
                String idUser = String.valueOf(IDUser.get(position));
                String nameUser = String.valueOf(UserName.get(position));
                intent.putExtra("IDUser", idUser);
                intent.putExtra("NameUser", nameUser);
                activity.startActivityForResult(intent,4);
            }
        });
    }

    @Override
    public int getItemCount() {
        return IDUser.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder{
        TextView IDUser, UserName, Fullname, Email, Phonemumber, Password;
        ImageView imgUser;
        LinearLayout user_account_list_layout;
        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            IDUser=itemView.findViewById(R.id.txtIDUser);
            imgUser=itemView.findViewById(R.id.imgv_user_avatar);
            UserName=itemView.findViewById(R.id.txtUsernameUser);
            Fullname=itemView.findViewById(R.id.txtFullnameUser);
            Email=itemView.findViewById(R.id.txtEmailUser);
            Phonemumber=itemView.findViewById(R.id.txtPhonenumberUser);
            Password=itemView.findViewById(R.id.txtPasswordUser);
            user_account_list_layout=itemView.findViewById(R.id.user_account_list_layout);
        }
    }
}
