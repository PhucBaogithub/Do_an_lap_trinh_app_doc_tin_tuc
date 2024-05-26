package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.AdminAccountAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import widget.CustomViewPager;

public class UIAdmin extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ImageButton btnBacktoUserAdmin, btnDeleteAllAccountAdmin;
    ImageView img_emptyview;
    TextView textView;

    DBHelper dbHelper;
    ArrayList<String> IDAdmin, UserName, Password, RoleAdmin;
    AdminAccountAdapter adapter;
    Button btnCreateUserAccount, btn_create_news_admin, btnCommentManager, btn_create_pricegold_admin, btn_create_lottery_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiadmin);
        //khai báo
        recyclerView=findViewById(R.id.rcv_UIAdmin);
        floatingActionButton=findViewById(R.id.btn_add_adminaccount);
        btnBacktoUserAdmin=findViewById(R.id.btnBacktoUserAdmin);
        btnCreateUserAccount=findViewById(R.id.btnCreateUserAccount);
        btnDeleteAllAccountAdmin=findViewById(R.id.btnDeleteAllAccountAdmin);
        img_emptyview=findViewById(R.id.img_emptyview);
        btnCommentManager=findViewById(R.id.btnCommentManager);
        textView=findViewById(R.id.textView);
        btn_create_news_admin=findViewById(R.id.btn_create_news_admin);
        btn_create_pricegold_admin=findViewById(R.id.btn_create_pricegold_admin);
        btn_create_lottery_admin=findViewById(R.id.btn_create_lottery_admin);


        //Tạo recyclerview cho admin
        dbHelper = new DBHelper(UIAdmin.this);
        IDAdmin = new ArrayList<>();
        UserName = new ArrayList<>();
        Password = new ArrayList<>();
        RoleAdmin = new ArrayList<>();

        storeDataInArrays();

        adapter = new AdminAccountAdapter(UIAdmin.this, this, IDAdmin, UserName, Password, RoleAdmin);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIAdmin.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);



        //Tạo sự kiện cho các nút
        btnCommentManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIAdmin.this, CommentManager.class);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIAdmin.this, AdminSetting.class);
                startActivity(intent);
            }
        });

        btnBacktoUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UIAdmin.this, "Đã thoát quyền quản trị viên", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UIAdmin.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCreateUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIAdmin.this, AccountUserManager.class);
                startActivity(intent);
            }
        });

        btnDeleteAllAccountAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

        btn_create_news_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIAdmin.this, NewsSetting.class);
                startActivity(intent);
            }
        });

        btn_create_pricegold_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIAdmin.this, CreateGiaVang.class);
                startActivity(intent);
            }
        });

        btn_create_lottery_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIAdmin.this, CreateXoso.class);
                startActivity(intent);
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

     void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataAdminAccount();
        if(cursor.getCount() == 0){
            img_emptyview.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            Toast.makeText(UIAdmin.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                IDAdmin.add(cursor.getString(0));
                UserName.add(cursor.getString(1));
                Password.add(cursor.getString(2));
                RoleAdmin.add(cursor.getString(3));
            }
            img_emptyview.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    //Tạo comfirm dialog để xóa tất cả dữ liệu trong bản
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa tài khoản"+ UserName + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UIAdmin.this);
                dbHelper.deleteAdminAccountAllData();
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