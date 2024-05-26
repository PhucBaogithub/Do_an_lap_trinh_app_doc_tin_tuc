package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.NewsAdapter;
import com.example.projectreaderapp.adapter.NewsAdminAdapter;
import com.example.projectreaderapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsSetting extends AppCompatActivity {

    Button btn_create_news, btn_create_category_news, btn_create_source_news;
    ImageButton btnBacktoAdminCreateNews, btnDeleteAllNews;
    ImageView img_emptyview_newssetting;
    TextView textViewnewssetting;
    RecyclerView rcv_create_news;
    DBHelper dbHelper;
    ArrayList<Bitmap> imgNews;
    ArrayList<Bitmap> imgLogoNews;
    ArrayList<String> news_id, news_title;
    NewsAdminAdapter newsAdminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_setting);
        //Khai báo
        btn_create_news=findViewById(R.id.btn_create_news);
        btn_create_category_news=findViewById(R.id.btn_create_category_news);
        btn_create_source_news=findViewById(R.id.btn_create_source_news);
        btnBacktoAdminCreateNews=findViewById(R.id.btnBacktoAdminCreateNews);
        btnDeleteAllNews=findViewById(R.id.btnDeleteAllNews);
        img_emptyview_newssetting=findViewById(R.id.img_emptyview_newssetting);
        textViewnewssetting=findViewById(R.id.textViewnewssetting);
        rcv_create_news=findViewById(R.id.rcv_create_news);
        news_id=new ArrayList<>();
        news_title=new ArrayList<>();
        imgNews=new ArrayList<>();
        imgLogoNews=new ArrayList<>();
        dbHelper=new DBHelper(NewsSetting.this);

        storeDataInArrays();


        newsAdminAdapter = new NewsAdminAdapter(this, news_id, news_title, imgNews, imgLogoNews, NewsSetting.this);
        rcv_create_news.setAdapter(newsAdminAdapter);
        rcv_create_news.setLayoutManager(new LinearLayoutManager(NewsSetting.this, RecyclerView.VERTICAL, false));

        //Tạo recyclerview


        //Tạo sự kiện cho các nút
        btnDeleteAllNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

        btn_create_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsSetting.this, CreateNews.class);
                startActivityForResult(intent,2);
            }
        });

        btn_create_category_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsSetting.this, CreateCateNews.class);
                startActivity(intent);
            }
        });

        btn_create_source_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsSetting.this, CreateSourceNews.class);
                startActivity(intent);
            }
        });

        btnBacktoAdminCreateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Tạo hiển thị dữ liệu
    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataNews();
        if(cursor.getCount() == 0){
            img_emptyview_newssetting.setVisibility(View.VISIBLE);
            textViewnewssetting.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                news_id.add(cursor.getString(0));
                news_title.add(cursor.getString(1));

                byte[] imageByte = cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                imgNews.add(bitmap);

                byte[] imageByte1 = cursor.getBlob(10);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageByte1, 0, imageByte1.length);
                imgLogoNews.add(bitmap1);
            }
            img_emptyview_newssetting.setVisibility(View.GONE);
            textViewnewssetting.setVisibility(View.GONE);
        }
    }

    //Tạo comfirm dialog để xóa tất cả dữ liệu trong bản
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa tài khoản"+ news_id + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tin tức này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(NewsSetting.this);
                dbHelper.deleteNewsAllData();
                recreate();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    //Làm mới lại giao diện
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            recreate();
        }
    }
}