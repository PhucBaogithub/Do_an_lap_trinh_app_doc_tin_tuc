package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateSourceNews extends AppCompatActivity {

    ImageButton btnBacksrnewsupdate, btn_delete_source_news;
    ImageView btnimgv_logo_sourcenews_update;
    EditText edt_create_source_news_update;
    Button btn_source_cate_news_update;
    DBHelper dbHelper;
    String ID,Name;
    private Uri imagePath;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Bitmap imageToStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_source_news);
        //Khai báo
        btnBacksrnewsupdate=findViewById(R.id.btnBacksrnewsupdate);
        btnimgv_logo_sourcenews_update=findViewById(R.id.btnimgv_logo_sourcenews_update);
        edt_create_source_news_update=findViewById(R.id.edt_create_source_news_update);
        btn_source_cate_news_update=findViewById(R.id.btn_source_cate_news_update);
        btn_delete_source_news=findViewById(R.id.btn_delete_source_news);
        dbHelper = new DBHelper(UpdateSourceNews.this);

        getIntentData();


        //Tạo sự kiện cho các nút
        btn_delete_source_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnimgv_logo_sourcenews_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        btnBacksrnewsupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_source_cate_news_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=edt_create_source_news_update.getText().toString().trim();
                if(Name.isEmpty() || imageToStore==null){
                    Toast.makeText(UpdateSourceNews.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateSourceNews(ID, Name, imageToStore);
                    finish();
                }
            }
        });
    }

    void getIntentData(){
        if(getIntent().hasExtra("ID") && getIntent().hasExtra("Title")){
            ID = getIntent().getStringExtra("ID");
            Name = getIntent().getStringExtra("Title");

            edt_create_source_news_update.setText(Name);
        }else{
            Toast.makeText(this, "Không có dữ liệu...   ", Toast.LENGTH_SHORT).show();
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
                btnimgv_logo_sourcenews_update.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Tạo xác nhận xóa
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa nguồn báo"+ Name + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa nguồn báo này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateSourceNews.this);
                dbHelper.deleteSourceNewsOneRow(ID);
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }
}