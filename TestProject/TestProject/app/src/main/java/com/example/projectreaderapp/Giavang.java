package com.example.projectreaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.AdminGiaVangAdapter;
import com.example.projectreaderapp.adapter.GiaVangAdapter;
import com.example.projectreaderapp.adapter.SanPhamAdapter;
import com.example.projectreaderapp.model.SanPham;

import java.util.ArrayList;

public class Giavang extends AppCompatActivity {

    float x1, x2, y1, y2;
    private ImageButton btnBackUtilities;
    private RecyclerView recyclerView;
    DBHelper dbHelper;
    ArrayList<String> NameCountry, Buy, Sold;
    ArrayList<Bitmap> Logo;
    GiaVangAdapter giaVangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giavang);
        addControls();

        //Set sự kiện cho nút back trong Login form
        btnBackUtilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void addControls(){
        btnBackUtilities=findViewById(R.id.btnBackUtilities);
        recyclerView=findViewById(R.id.rcvSanPham);
        dbHelper=new DBHelper(this);
        NameCountry=new ArrayList<>();
        Buy=new ArrayList<>();
        Sold=new ArrayList<>();
        Logo=new ArrayList<>();

        storeDataInArrays();

        giaVangAdapter=new GiaVangAdapter(Giavang.this,this, NameCountry, Buy, Sold, Logo);
        recyclerView.setLayoutManager(new LinearLayoutManager(Giavang.this));
        recyclerView.setAdapter(giaVangAdapter);

    }

    //Lấy dữ liệu từ database gán cho arraylist
    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataGiaVangAdmin();
        if(cursor.getCount() == 0){
            Toast.makeText(Giavang.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                NameCountry.add(cursor.getString(1));
                Buy.add(cursor.getString(2));
                Sold.add(cursor.getString(3));

                byte[] imageByte = cursor.getBlob(4);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                Logo.add(bitmap);
            }
        }
    }


    //Set sự kiện quẹt trái để trở về User Fragment
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2)
                {
                    finish();
                }
                break;
        }
        return false;
    }
}