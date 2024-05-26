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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.AdminDetailsNewsAdapter;

import java.util.ArrayList;

public class CreateDetailsNews extends AppCompatActivity {
    ImageButton btnBack_create_detail_news,btnReload_create_detail_news, btnDelete_All_detail_news;
    ImageView img_news_detail, img_emptyview_details_news;
    EditText edt_create_detail_news_title, edt_create_detail_news_detail1, edt_create_detail_news_detail2;
    Button btn_create_new_detail_news;
    TextView textViewDetailsNews;
    RecyclerView rcv_display_details_news;
    private Uri imagePath;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Bitmap imageToStore;
    DBHelper dbHelper;
    String NewsTitle ,NewsDetails1 ,NewsDetails2;
    Integer NewsID;
    ArrayList<String> ID, Title, Details1, Details2;
    ArrayList<Bitmap> Image;
    AdminDetailsNewsAdapter adminDetailsNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_details_news);
        //Khai báo
        btnBack_create_detail_news=findViewById(R.id.btnBack_create_detail_news);
        img_news_detail=findViewById(R.id.img_news_detail);
        edt_create_detail_news_title=findViewById(R.id.edt_create_detail_news_title);
        edt_create_detail_news_detail1=findViewById(R.id.edt_create_detail_news_detail1);
        edt_create_detail_news_detail2=findViewById(R.id.edt_create_detail_news_detail2);
        btn_create_new_detail_news=findViewById(R.id.btn_create_new_detail_news);
        rcv_display_details_news=findViewById(R.id.rcv_display_details_news);
        btnReload_create_detail_news=findViewById(R.id.btnReload_create_detail_news);
        btnDelete_All_detail_news=findViewById(R.id.btnDelete_All_detail_news);
        img_emptyview_details_news=findViewById(R.id.img_emptyview_details_news);
        textViewDetailsNews=findViewById(R.id.textViewDetailsNews);
        dbHelper = new DBHelper(CreateDetailsNews.this);

        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("mybundle");
        NewsID = bundle.getInt("NewID");

        ID=new ArrayList<>();
        Title=new ArrayList<>();
        Details1=new ArrayList<>();
        Details2=new ArrayList<>();
        Image=new ArrayList<>();

        DisplayDataInArrays();

        adminDetailsNewsAdapter = new AdminDetailsNewsAdapter(CreateDetailsNews.this, this, ID, Title, Details1, Details2, Image);
        rcv_display_details_news.setLayoutManager(new LinearLayoutManager(CreateDetailsNews.this));
        rcv_display_details_news.setAdapter(adminDetailsNewsAdapter);


        //Tạo sự kiện cho các nút
        btnDelete_All_detail_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

        btnReload_create_detail_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDetailsNews.this.recreate();
            }
        });

        btn_create_new_detail_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsTitle = edt_create_detail_news_title.getText().toString().trim();
                NewsDetails1 = edt_create_detail_news_detail1.getText().toString().trim();
                NewsDetails2 = edt_create_detail_news_detail2.getText().toString().trim();
                dbHelper.addDetailNews(NewsTitle, NewsDetails1, NewsDetails2, imageToStore, NewsID);
                finish();
                recreate();
            }
        });

        btnBack_create_detail_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_news_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });
    }

    private void DisplayDataInArrays(){
        Cursor cursor = dbHelper.readAllDataNewsDetails(NewsID);
        if(cursor.getCount() == 0){
            Toast.makeText(CreateDetailsNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
            img_emptyview_details_news.setVisibility(View.VISIBLE);
            textViewDetailsNews.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                ID.add(cursor.getString(0));
                Title.add(cursor.getString(1));
                Details1.add(cursor.getString(2));
                Details2.add(cursor.getString(3));

                byte[] imageByte = cursor.getBlob(4);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                Image.add(bitmap);
            }
            img_emptyview_details_news.setVisibility(View.GONE);
            textViewDetailsNews.setVisibility(View.GONE);
        }
    }

    //Chọn ảnh của Bitmap
    private void choseImage(){
        try{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                img_news_detail.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Tạo comfirm dialog để xóa tất cả dữ liệu trong bản
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa tài khoản"+ ID + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(CreateDetailsNews.this);
                dbHelper.deleteNewsDetailAllData();
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
}