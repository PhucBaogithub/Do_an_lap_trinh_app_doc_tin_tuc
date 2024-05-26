package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.model.SourceNews;

import java.util.ArrayList;

public class CreateNews extends AppCompatActivity {

    ImageButton btnBack_create_news_setting;
    ImageView img_news_avatar;
    EditText edt_create_source_news;
    AutoCompleteTextView atcp_cate_news, atcp_source_news;
    Button btn_create_new_news;
    private Uri imagePath;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Bitmap imageToStore;
    ArrayList<String> NameCateNews;
    ArrayList<String> NameSourceNews;
    Integer IDCateNews;
    Integer IDSourceNews;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems1;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);
        //Khai báo
        btnBack_create_news_setting=findViewById(R.id.btnBack_create_news_setting);
        img_news_avatar=findViewById(R.id.img_news_avatar);
        edt_create_source_news=findViewById(R.id.edt_create_source_news);
        atcp_cate_news=findViewById(R.id.atcp_cate_news);
        atcp_source_news=findViewById(R.id.atcp_source_news);
        btn_create_new_news=findViewById(R.id.btn_create_new_news);
        dbHelper = new DBHelper(CreateNews.this);
        NameCateNews=new ArrayList<>();
        NameSourceNews=new ArrayList<>();
        //Tạo DropdownMenu cho nút mục và nguồn
        //Dropdown Menu Role Admin
        storeDataInArrays();
        storeDataInArrays1();
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_role_admin, getPackages());
        atcp_cate_news.setAdapter(adapterItems);

        atcp_cate_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                IDCateNews=0;
                IDCateNews=position+1;
                Toast.makeText(CreateNews.this, "Mục báo đã chọn: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        adapterItems1 = new ArrayAdapter<String>(this, R.layout.list_role_admin, getPackages1());
        atcp_source_news.setAdapter(adapterItems1);

        atcp_source_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                IDSourceNews=0;
                IDSourceNews=position+1;
                Toast.makeText(CreateNews.this, "Mục báo đã chọn: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        //Tạo sự kiện cho các nút
        btnBack_create_news_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_create_new_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameNews = edt_create_source_news.getText().toString().trim();
                if(img_news_avatar.getDrawable() == null){
                    Toast.makeText(CreateNews.this, "Bạn chưa tải ảnh lên!", Toast.LENGTH_LONG).show();
                }if(imageToStore == null){
                    Toast.makeText(CreateNews.this, "Bạn chưa tải ảnh lên!", Toast.LENGTH_LONG).show();
                }if(NameNews.isEmpty()){
                    Toast.makeText(CreateNews.this, "Bạn chưa điền tên báo!", Toast.LENGTH_LONG).show();
                }else{
                    dbHelper.addNews(new com.example.projectreaderapp.model.CreateNews(NameNews, IDCateNews, IDSourceNews, imageToStore));
                    finish();
                }
            }
        });

        img_news_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });
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
                img_news_avatar.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Tạo mục string[] cho cate
    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataCateNews();
        if(cursor.getCount() == 0){
            Toast.makeText(CreateNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                NameCateNews.add(cursor.getString(1));
            }
        }
    }

    void storeDataInArrays1(){
        Cursor cursor = dbHelper.readAllDataSourceNews();
        if(cursor.getCount() == 0){
            Toast.makeText(CreateNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                NameSourceNews.add(cursor.getString(1));
            }
        }
    }

    public ArrayList<String> getPackages() {
        final int max = NameCateNews.size();
        for (int i=0; i<max; i++) {
            NameCateNews.get(i);
            Log.e("TAG", NameCateNews.get(i).toString());
        }
        return NameCateNews;
    }

    public ArrayList<String> getPackages1() {
        final int max = NameSourceNews.size();
        for (int i=0; i<max; i++) {
            NameSourceNews.get(i);
            Log.e("TAG", NameSourceNews.get(i).toString());
        }
        return NameSourceNews;
    }
}