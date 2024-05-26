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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.CateNewsAdapter;

import java.util.ArrayList;

public class CreateCateNews extends AppCompatActivity {

    ImageButton btnBackcrcatenews, btnDeleteAllCateNews;
    EditText edt_create_cate_news;
    Button btn_create_cate_news;
    RecyclerView rcv_display_cate_news;

    ArrayList<String> name,id;
    DBHelper dbHelper;
    CateNewsAdapter cateNewsAdapter;
    ImageView img_emptyview_cate_news;
    TextView textViewcatenews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cate_news);
        //Khai báo
        btnBackcrcatenews=findViewById(R.id.btnBackcrcatenews);
        edt_create_cate_news=findViewById(R.id.edt_create_cate_news);
        btn_create_cate_news=findViewById(R.id.btn_create_cate_news);
        rcv_display_cate_news=findViewById(R.id.rcv_display_cate_news);
        btnDeleteAllCateNews=findViewById(R.id.btnDeleteAllCateNews);
        img_emptyview_cate_news=findViewById(R.id.img_emptyview_cate_news);
        textViewcatenews=findViewById(R.id.textViewcatenews);
        dbHelper = new DBHelper(CreateCateNews.this);
        name=new ArrayList<>();
        id=new ArrayList<>();

        storeDataInArrays();

        cateNewsAdapter=new CateNewsAdapter(CreateCateNews.this, this, id, name);
        rcv_display_cate_news.setLayoutManager(new LinearLayoutManager(this));
        rcv_display_cate_news.setAdapter(cateNewsAdapter);


        //Tạo sự kiện cho các nút
        btnDeleteAllCateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });


        btnBackcrcatenews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_create_cate_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(CreateCateNews.this);
                dbHelper.addNameCateNews(edt_create_cate_news.getText().toString().trim());
                finish();
                recreate();
            }
        });
    }

    //Làm mới lại giao diện
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            recreate();
        }
    }

    //lấy dữ liệu get lên rcv
    private void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataCateNews();
        if(cursor.getCount() == 0){
            Toast.makeText(CreateCateNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
            img_emptyview_cate_news.setVisibility(View.VISIBLE);
            textViewcatenews.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
            }
            img_emptyview_cate_news.setVisibility(View.GONE);
            textViewcatenews.setVisibility(View.GONE);
        }
    }

    //Tạo comfirm dialog để xóa tất cả dữ liệu trong bản
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa tài khoản"+ id + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(CreateCateNews.this);
                dbHelper.deleteCateNewsAllData();
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