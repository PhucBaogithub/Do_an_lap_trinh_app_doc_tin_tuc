package com.example.projectreaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.XosoAdapter;

import java.util.ArrayList;

public class Xoso extends AppCompatActivity {

    ImageButton btnBackXoso;
    RecyclerView rcvXoso;
    DBHelper dbHelper;
    ArrayList<String> NameCountry, PS, P1, P2, P3, P4, P5, P6, P7, P8;
    XosoAdapter xosoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xoso);
        //Khai báo
        addControl();



        //Tạo sự kiện cho các nút bấm
        btnBackXoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void addControl(){
        btnBackXoso=findViewById(R.id.btnBackXoso);
        rcvXoso=findViewById(R.id.rcvXoso);
        dbHelper=new DBHelper(this);
        NameCountry=new ArrayList<>();
        PS=new ArrayList<>();
        P1=new ArrayList<>();
        P2=new ArrayList<>();
        P3=new ArrayList<>();
        P4=new ArrayList<>();
        P5=new ArrayList<>();
        P6=new ArrayList<>();
        P7=new ArrayList<>();
        P8=new ArrayList<>();

        storeDataInArrays();
        xosoAdapter=new XosoAdapter(Xoso.this, this, NameCountry, PS, P1, P2, P3, P4, P5, P6, P7, P8);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Xoso.this, RecyclerView.HORIZONTAL, false);
        rcvXoso.setLayoutManager(linearLayoutManager);
        rcvXoso.setAdapter(xosoAdapter);
    }

    //lấy dữ liệu get lên rcv
    private void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataXosoAdmin();
        if(cursor.getCount() == 0){
            Toast.makeText(Xoso.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                NameCountry.add(cursor.getString(1));
                P1.add(cursor.getString(2));
                P2.add(cursor.getString(3));
                P3.add(cursor.getString(4));
                P4.add(cursor.getString(5));
                P5.add(cursor.getString(6));
                P6.add(cursor.getString(7));
                P7.add(cursor.getString(8));
                P8.add(cursor.getString(9));
                PS.add(cursor.getString(10));
            }
        }
    }
}