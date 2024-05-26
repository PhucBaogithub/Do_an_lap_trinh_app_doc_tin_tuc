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

public class UpdateDetailsNews extends AppCompatActivity {


    Button btn_new_detail_news_update, btn_delete_details_news;
    ImageView img_news_detail_update;
    ImageButton btnBack_update_detail_news;
    EditText edt_create_detail_news_title_update, edt_create_detail_news_detail1_update, edt_create_detail_news_detail2_update;
    String ID, Title, Details1, Details2;
    DBHelper dbHelper;
    private Uri imagePath;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Bitmap imageToStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details_news);
        //Khai báo
        img_news_detail_update=findViewById(R.id.img_news_detail_update);
        btnBack_update_detail_news=findViewById(R.id.btnBack_update_detail_news);
        edt_create_detail_news_title_update=findViewById(R.id.edt_create_detail_news_title_update);
        edt_create_detail_news_detail1_update=findViewById(R.id.edt_create_detail_news_detail1_update);
        edt_create_detail_news_detail2_update=findViewById(R.id.edt_create_detail_news_detail2_update);
        btn_delete_details_news=findViewById(R.id.btn_delete_details_news);
        btn_new_detail_news_update=findViewById(R.id.btn_new_detail_news_update);
        dbHelper = new DBHelper(UpdateDetailsNews.this);

        getDataInArrays();



        //Tạo sự kiện cho các nút
        btn_delete_details_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

        img_news_detail_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        btnBack_update_detail_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_new_detail_news_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = edt_create_detail_news_title_update.getText().toString().trim();
                Details1 = edt_create_detail_news_detail1_update.getText().toString().trim();
                Details2 = edt_create_detail_news_detail2_update.getText().toString().trim();
                if(Title.isEmpty() || Details1.isEmpty() || Details2.isEmpty() || imageToStore==null){
                    Toast.makeText(UpdateDetailsNews.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateNewsDetails(ID, Title, Details1, Details2, imageToStore);
                    finish();
                }
            }
        });
    }

    void getDataInArrays(){
        if(getIntent().hasExtra("ID") &&
                getIntent().hasExtra("Title") &&
                getIntent().hasExtra("Details1") &&
                getIntent().hasExtra("Details2")){

            ID = getIntent().getStringExtra("ID");
            Title = getIntent().getStringExtra("Title");
            Details1 = getIntent().getStringExtra("Details1");
            Details2 = getIntent().getStringExtra("Details2");

            edt_create_detail_news_title_update.setText(Title);
            edt_create_detail_news_detail1_update.setText(Details1);
            edt_create_detail_news_detail2_update.setText(Details2);
        }else{
            Toast.makeText(this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
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
                img_news_detail_update.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Tạo xác nhận xóa
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa chi tiết báo"+ ID + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa chi tiết báo này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateDetailsNews.this);
                dbHelper.deleteDetailsNewsOneRow(ID);
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