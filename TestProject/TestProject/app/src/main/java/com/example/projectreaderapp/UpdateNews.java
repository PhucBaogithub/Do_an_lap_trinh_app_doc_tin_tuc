package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateNews extends AppCompatActivity {

    EditText edt_update_name_news;
    ImageView img_news_update;
    ImageButton btnBack_update_news_setting;
    AutoCompleteTextView atcp_update_cate_news, atcp_update_source_news;
    Button btn_update_news, btn_create_details_news, btn_delete_news;
    ArrayList<String> NameCateNews;
    ArrayList<String> NameSourceNews;
    private Uri imagePath;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Bitmap imageToStore;
    DBHelper dbHelper;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems1;
    Integer IDCateNews;
    Integer IDSourceNews;
    String IDNews, NameNews;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        //Khai báo
        edt_update_name_news=findViewById(R.id.edt_update_name_news);
        img_news_update=findViewById(R.id.img_news_update);
        atcp_update_cate_news=findViewById(R.id.atcp_update_cate_news);
        atcp_update_source_news=findViewById(R.id.atcp_update_source_news);
        btnBack_update_news_setting=findViewById(R.id.btnBack_update_news_setting);
        btn_delete_news=findViewById(R.id.btn_delete_news);
        btn_update_news=findViewById(R.id.btn_update_news);
        btn_create_details_news=findViewById(R.id.btn_create_details_news);
        dbHelper = new DBHelper(UpdateNews.this);
        NameCateNews=new ArrayList<>();
        NameSourceNews=new ArrayList<>();
        storeDataInArrays();
        storeDataInArrays1();
        getAndsetIntentData();

        //Khai báo string[] autocomplete
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_role_admin, getPackages());
        atcp_update_cate_news.setAdapter(adapterItems);

        atcp_update_cate_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                IDCateNews=0;
                IDCateNews=position+1;
                Toast.makeText(UpdateNews.this, "Mục báo đã chọn: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        adapterItems1 = new ArrayAdapter<String>(this, R.layout.list_role_admin, getPackages1());
        atcp_update_source_news.setAdapter(adapterItems1);

        atcp_update_source_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                IDSourceNews=0;
                IDSourceNews=position+1;
                Toast.makeText(UpdateNews.this, "Mục báo đã chọn: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        //Tạo sự kiện cho các nút
        btn_delete_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

        btn_update_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameNews = edt_update_name_news.getText().toString().trim();
                if(NameNews.isEmpty()){
                    Toast.makeText(UpdateNews.this, "Bạn chưa nhập tiêu đề báo!", Toast.LENGTH_SHORT).show();
                }if(imageToStore==null){
                    Toast.makeText(UpdateNews.this, "Bạn chưa tải ảnh lên!", Toast.LENGTH_SHORT).show();
                }if(IDCateNews == 0){
                    Toast.makeText(UpdateNews.this, "Bạn chưa chọn mục cho báo!", Toast.LENGTH_SHORT).show();
                }if(IDSourceNews == 0){
                    Toast.makeText(UpdateNews.this, "Bạn chưa chọn nguồn cho báo!", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateNews(IDNews, NameNews , imageToStore, IDCateNews, IDSourceNews);
                    finish();
                }
            }
        });

        btnBack_update_news_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_news_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        btn_create_details_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateNews.this, CreateDetailsNews.class);
                Integer i = Integer.parseInt(IDNews);
                Bundle bundle = new Bundle();
                bundle.putInt("NewID", i);
                intent.putExtra("mybundle", bundle);
                startActivity(intent);
            }
        });
    }

    //Tạo lấy data
    void getAndsetIntentData(){
        if(getIntent().hasExtra("IDNews") && getIntent().hasExtra("NameNews")){
            IDNews = getIntent().getStringExtra("IDNews");
            NameNews = getIntent().getStringExtra("NameNews");

            //Cài đặt intent data
            edt_update_name_news.setText(NameNews);
        }else{
            Toast.makeText(this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }
    }

    //Tạo mục string[] cho cate
    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataCateNews();
        if(cursor.getCount() == 0){
            Toast.makeText(UpdateNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                NameCateNews.add(cursor.getString(1));
            }
        }
    }

    void storeDataInArrays1(){
        Cursor cursor = dbHelper.readAllDataSourceNews();
        if(cursor.getCount() == 0){
            Toast.makeText(UpdateNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
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
                img_news_update.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Tạo xác nhận xóa
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa báo"+ IDNews + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa báo này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateNews.this);
                dbHelper.deleteNewsOneRow(IDNews);
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