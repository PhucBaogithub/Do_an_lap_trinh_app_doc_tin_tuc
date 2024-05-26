package com.example.projectreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class UIEmployee extends AppCompatActivity {

    ImageButton btnBacktoUserEmployee;
    Button btn_create_news_employee,manager_account_user, manager_comments_employee, btn_create_pricegold_employee, btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiemployee);
        //Khai báo
        btnBacktoUserEmployee=findViewById(R.id.btnBacktoUserEmployee);
        btn_create_news_employee=findViewById(R.id.btn_create_news_employee);
        manager_account_user=findViewById(R.id.manager_account_user);
        manager_comments_employee=findViewById(R.id.manager_comments_employee);
        btn_create_pricegold_employee=findViewById(R.id.btn_create_pricegold_employee);
        btn1=findViewById(R.id.btn1);



        //Tạo sự kiện cho các nút
        manager_comments_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIEmployee.this, CommentManager.class);
                startActivity(intent);
            }
        });

        btnBacktoUserEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UIEmployee.this, "Đã thoát quyền nhân viên", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UIEmployee.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_create_news_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIEmployee.this, NewsSetting.class);
                startActivity(intent);
            }
        });

        manager_account_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIEmployee.this, AccountUserManager.class);
                startActivity(intent);
            }
        });

        btn_create_pricegold_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIEmployee.this, CreateGiaVang.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIEmployee.this, CreateXoso.class);
                startActivity(intent);
            }
        });
    }
}