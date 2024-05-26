package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.CommentAdapter;
import com.example.projectreaderapp.adapter.DetailsNewsAdapter;
import com.example.projectreaderapp.adapter.NewsDisplayAdapter;

import java.util.ArrayList;

public class DisplayDetailsNews extends AppCompatActivity {

    ImageButton btnBackDetails;
    ImageView logonews_details;
    TextView tv_name_title_news_display,tv_name_details_author,tv_news_name_views;
    EditText edt_comment_news;
    Button btnComment;
    DBHelper dbHelper;
    Bitmap imgLogoNews;
    String  nameNews, authorNews;
    Integer newsID, ViewNewsAdd;
    String viewnews;
    RecyclerView rcv_detailsnews, rcv_comment;
    ArrayList<String> NewsTitle, NewsDetails1, NewsDetails2;
    ArrayList<Bitmap> ImageNewsDetails;
    DetailsNewsAdapter detailsNewsAdapter;
    Context context;
    Integer IDUser;
    String comment;
    //Khai báo cho comment
    ArrayList<String> idcomment, namecommentuser, commentrcv, idnewscm, idusercm;
    ArrayList<Bitmap> imgUser;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details_news);
        //Khai báo
        btnBackDetails=findViewById(R.id.btnBackDetails);
        logonews_details=findViewById(R.id.logonews_details);
        tv_name_title_news_display=findViewById(R.id.tv_name_title_news_display);
        tv_name_details_author=findViewById(R.id.tv_name_details_author);
        tv_news_name_views=findViewById(R.id.tv_news_name_views);
        edt_comment_news=findViewById(R.id.edt_comment_news);
        btnComment=findViewById(R.id.btnComment);
        rcv_comment=findViewById(R.id.rcv_comment);
        rcv_detailsnews=findViewById(R.id.rcv_detailsnews);
        dbHelper=new DBHelper(DisplayDetailsNews.this);
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("mybundle1");
        newsID = bundle.getInt("IDnews");
        if(!new PrefManager(DisplayDetailsNews.this).getUsername().equals("")){
            btnComment.setVisibility(View.VISIBLE);
            displayDataUser();
        }else{
            btnComment.setVisibility(View.GONE);
        }

        //Khai báo comment
        idcomment=new ArrayList<>();
        commentrcv=new ArrayList<>();
        idnewscm=new ArrayList<>();
        idusercm=new ArrayList<>();
        namecommentuser=new ArrayList<>();
        imgUser=new ArrayList<>();

        //Báo phụ(chi tiết báo)
        NewsTitle=new ArrayList<>();
        NewsDetails1=new ArrayList<>();
        NewsDetails2=new ArrayList<>();
        ImageNewsDetails=new ArrayList<>();

        displayData();

        storeDataInArrays();

        displayDataComment();

        if(viewnews==null){
            dbHelper.updateViewNews(newsID, 1);
        }else{
            ViewNewsAdd = Integer.valueOf(viewnews) + 1;
            dbHelper.updateViewNews(newsID, ViewNewsAdd);
        }

        tv_name_title_news_display.setText(nameNews);
        tv_name_details_author.setText(authorNews);
        logonews_details.setImageBitmap(imgLogoNews);
        tv_news_name_views.setText(viewnews);

        detailsNewsAdapter=new DetailsNewsAdapter(DisplayDetailsNews.this, ImageNewsDetails, NewsTitle, NewsDetails1, NewsDetails2);
        rcv_detailsnews.setAdapter(detailsNewsAdapter);
        rcv_detailsnews.setLayoutManager(new LinearLayoutManager(DisplayDetailsNews.this));

        commentAdapter = new CommentAdapter(DisplayDetailsNews.this, this, idcomment, namecommentuser, commentrcv, idnewscm, idusercm, imgUser);
        rcv_comment.setLayoutManager(new LinearLayoutManager(DisplayDetailsNews.this));
        rcv_comment.setAdapter(commentAdapter);


        //Khai báo các nút
        edt_comment_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!new PrefManager(DisplayDetailsNews.this).getUsername().equals("")){
                    comment = edt_comment_news.getText().toString().trim();
                }else{
                    comfirmDialog();
                }
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = edt_comment_news.getText().toString().trim();
                if(comment.isEmpty()){
                    Toast.makeText(DisplayDetailsNews.this, "Vui lòng nhập bình luận!", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.addComment(comment, newsID, IDUser);
                    edt_comment_news.setText("");
                    recreate();
                }
            }
        });

        btnBackDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //Làm mới lại giao diện
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 5){
            recreate();
        }
    }

    //Lấy dữ liệu đã đăng nhập hay chưa
    public String getUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", "");
    }

    //Lấy data cho báo phụ
    public void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataNewsDetails(newsID);
        if(cursor.getCount() == 0){
            Toast.makeText(DisplayDetailsNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                NewsTitle.add(cursor.getString(1));
                NewsDetails1.add(cursor.getString(2));
                NewsDetails2.add(cursor.getString(3));

                byte[] imageByte = cursor.getBlob(4);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                ImageNewsDetails.add(bitmap);
            }
        }
    }

    //Lấy data cho báo chính
    public void displayDataUser(){
        Cursor cursor = dbHelper.getUser(new PrefManager(DisplayDetailsNews.this).getUsername());
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "No entires", Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                String UserID = cursor.getString(0);
                IDUser = Integer.valueOf(UserID);
            }
        }
    }

    public void displayData(){
        Cursor cursor = dbHelper.readAllDataNewsLogoNews(newsID);
        if(cursor.getCount() == 0){
            Toast.makeText(DisplayDetailsNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                nameNews = cursor.getString(1);
                authorNews = cursor.getString(7);
                viewnews = cursor.getString(2);
                byte[] imageByte1 = cursor.getBlob(8);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageByte1, 0, imageByte1.length);
                imgLogoNews = bitmap1;
            }
        }
    }

    //Lấy dữ lệu cho rcvcomment
    public void displayDataComment(){
        Cursor cursor = dbHelper.readDataComment(newsID);
        if(cursor.getCount() == 0){
            Toast.makeText(DisplayDetailsNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                idcomment.add(cursor.getString(0));
                namecommentuser.add(cursor.getString(6));
                commentrcv.add(cursor.getString(1));
                idnewscm.add(cursor.getString(2));
                idusercm.add(cursor.getString(3));



                byte[] imageByte1 = cursor.getBlob(10);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageByte1, 0, imageByte1.length);
                imgUser.add(bitmap1);
            }
        }
    }

    //Tạo comfirm dialog để đăng nhập comment
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng nhập");
        builder.setMessage("Để được bình luận bạn vui lòng đăng nhập tài khoản!");
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DisplayDetailsNews.this, Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}