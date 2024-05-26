package com.example.projectreaderapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateCateNews extends AppCompatActivity {
    ImageButton btnBackcrcatenewsupdate;
    EditText edt_update_cate_news;
    Button btn_update_cate_news, btn_delete_cate_news;
    DBHelper dbHelper;
    String ID, Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cate_news);
        //Khai báo
        btnBackcrcatenewsupdate=findViewById(R.id.btnBackcrcatenewsupdate);
        edt_update_cate_news=findViewById(R.id.edt_update_cate_news);
        btn_update_cate_news=findViewById(R.id.btn_update_cate_news);
        btn_delete_cate_news=findViewById(R.id.btn_delete_cate_news);
        dbHelper=new DBHelper(UpdateCateNews.this);

        getIntentData();


        //Tạo sự kiện cho các nút
        btn_delete_cate_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });


        btn_update_cate_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = edt_update_cate_news.getText().toString().trim();
                if(Name.isEmpty()){
                    Toast.makeText(UpdateCateNews.this, "Vui lòng nhập tên mục!", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateCategoryNews(ID, Name);
                    finish();
                }
            }
        });

        btnBackcrcatenewsupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void getIntentData(){
        if(getIntent().hasExtra("ID") && getIntent().hasExtra("Name")){
            ID = getIntent().getStringExtra("ID");
            Name = getIntent().getStringExtra("Name");

            edt_update_cate_news.setText(Name);
        }else{
            Toast.makeText(this, "Không có dữ liệu...   ", Toast.LENGTH_SHORT).show();
        }
    }

    //Tạo xác nhận xóa
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa mục báo"+ Name + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa mục báo này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateCateNews.this);
                dbHelper.deleteCateNewsOneRow(ID);
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