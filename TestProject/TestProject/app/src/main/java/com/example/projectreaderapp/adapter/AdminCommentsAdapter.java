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

import com.example.projectreaderapp.DeleteComment;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UpdateAdminAccount;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminCommentsAdapter extends RecyclerView.Adapter<AdminCommentsAdapter.AdminCommentsViewHolder> {

    public AdminCommentsAdapter(Activity activity, Context context, ArrayList ID, ArrayList userName, ArrayList comment, ArrayList nameNews, ArrayList<Bitmap> image_avatar) {
        this.activity = activity;
        this.context = context;
        this.ID = ID;
        UserName = userName;
        Comment = comment;
        NameNews = nameNews;
        Image_avatar = image_avatar;
    }

    Activity activity;
    Context context;
    ArrayList ID, UserName, Comment, NameNews;
    ArrayList<Bitmap> Image_avatar;

    @NonNull
    @Override
    public AdminCommentsAdapter.AdminCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemcommentadmin, parent, false);
        return new AdminCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCommentsAdapter.AdminCommentsViewHolder holder, int position) {
        holder.tv_id_admin_comment.setText(String.valueOf(ID.get(position)));
        holder.tv_name_user_admin_comment.setText(String.valueOf(UserName.get(position)));
        holder.tv_comment_admin_user.setText(String.valueOf(Comment.get(position)));
        holder.tv_name_sourcenews_admin_comment.setText(String.valueOf(NameNews.get(position)));
        holder.imgv_user_admin_comment.setImageBitmap(Image_avatar.get(position));
        holder.cv_comment_admin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeleteComment.class);
                intent.putExtra("IDComment", String.valueOf(ID.get(position)));
                activity.startActivityForResult(intent,7);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ID.size();
    }

    public class AdminCommentsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id_admin_comment, tv_name_user_admin_comment, tv_comment_admin_user, tv_name_sourcenews_admin_comment;
        ImageView imgv_user_admin_comment;
        CardView cv_comment_admin_layout;

        public AdminCommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id_admin_comment=itemView.findViewById(R.id.tv_id_admin_comment);
            tv_name_user_admin_comment=itemView.findViewById(R.id.tv_name_user_admin_comment);
            tv_comment_admin_user=itemView.findViewById(R.id.tv_comment_admin_user);
            tv_name_sourcenews_admin_comment=itemView.findViewById(R.id.tv_name_sourcenews_admin_comment);
            imgv_user_admin_comment=itemView.findViewById(R.id.imgv_user_admin_comment);
            cv_comment_admin_layout=itemView.findViewById(R.id.cv_comment_admin_layout);
        }
    }
}
