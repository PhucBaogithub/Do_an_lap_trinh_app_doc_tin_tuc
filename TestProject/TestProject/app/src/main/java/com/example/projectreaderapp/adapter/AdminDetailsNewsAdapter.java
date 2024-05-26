package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UpdateDetailsNews;

import java.util.ArrayList;

public class AdminDetailsNewsAdapter extends RecyclerView.Adapter<AdminDetailsNewsAdapter.AdminDetailsNewsViewHoler> {

    public AdminDetailsNewsAdapter(Activity activity, Context context, ArrayList ID, ArrayList title, ArrayList details1, ArrayList details2, ArrayList<Bitmap> image) {
        this.activity = activity;
        this.context = context;
        this.ID = ID;
        Title = title;
        Details1 = details1;
        Details2 = details2;
        Image = image;
    }

    Activity activity;
    Context context;
    ArrayList ID, Title, Details1, Details2;
    ArrayList<Bitmap> Image;

    @NonNull
    @Override
    public AdminDetailsNewsAdapter.AdminDetailsNewsViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemadmindisplaydetailsnews, parent, false);
        return new AdminDetailsNewsViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDetailsNewsAdapter.AdminDetailsNewsViewHoler holder, int position) {
        holder.tv_admin_details_news_id.setText(String.valueOf(ID.get(position)));
        holder.tv_admin_details_news_title.setText(String.valueOf(Title.get(position)));
        holder.tv_admin_details_news_display1.setText(String.valueOf(Details1.get(position)));
        holder.tv_admin_details_news_display2.setText(String.valueOf(Details2.get(position)));
        holder.img_admin_details_news.setImageBitmap(Image.get(position));
        holder.layout_create_news_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDetailsNews.class);
                intent.putExtra("ID", String.valueOf(ID.get(position)));
                intent.putExtra("Title", String.valueOf(Title.get(position)));
                intent.putExtra("Details1", String.valueOf(Details1.get(position)));
                intent.putExtra("Details2", String.valueOf(Details2.get(position)));
                activity.startActivityForResult(intent, 2);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ID.size();
    }

    public class AdminDetailsNewsViewHoler extends RecyclerView.ViewHolder {

        TextView tv_admin_details_news_id, tv_admin_details_news_title, tv_admin_details_news_display1, tv_admin_details_news_display2;
        ImageView img_admin_details_news;
        LinearLayout layout_create_news_linear;

        public AdminDetailsNewsViewHoler(@NonNull View itemView) {
            super(itemView);
            tv_admin_details_news_id=itemView.findViewById(R.id.tv_admin_details_news_id);
            tv_admin_details_news_title=itemView.findViewById(R.id.tv_admin_details_news_title);
            tv_admin_details_news_display1=itemView.findViewById(R.id.tv_admin_details_news_display1);
            tv_admin_details_news_display2=itemView.findViewById(R.id.tv_admin_details_news_display2);
            img_admin_details_news=itemView.findViewById(R.id.img_admin_details_news);
            layout_create_news_linear=itemView.findViewById(R.id.layout_create_news_linear);
        }
    }
}
