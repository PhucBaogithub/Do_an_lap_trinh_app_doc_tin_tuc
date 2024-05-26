package com.example.projectreaderapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.AdminXosoAdapter;

import java.util.ArrayList;

public class CreateXoso extends AppCompatActivity {

    ImageButton btnBack_create_xoso, btnDeleteAllXoso, btnReloadLottery;
    ImageView img_emptyview_xoso;
    TextView textViewXoso;
    EditText Country, GDB, G1, G2, G3, G4 , G5, G6, G7, G8;
    Button btn_create_xoso;
    DBHelper dbHelper;
    ArrayList<String> ID, NameCountry, PS, P1, P2, P3, P4, P5, P6, P7, P8;
    AdminXosoAdapter adminXosoAdapter;
    RecyclerView rcv_xoso_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_xoso);
        //Khai báo
        addControl();


        //Tạo sự kiện cho các nút
        btnBack_create_xoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_create_xoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = Country.getText().toString().trim();
                String S = GDB.getText().toString().trim();
                String P1 = G1.getText().toString().trim();
                String P2 = G2.getText().toString().trim();
                String P3 = G3.getText().toString().trim();
                String P4 = G4.getText().toString().trim();
                String P5 = G5.getText().toString().trim();
                String P6 = G6.getText().toString().trim();
                String P7 = G7.getText().toString().trim();
                String P8 = G8.getText().toString().trim();
                if(Name.isEmpty() || S.isEmpty() || P1.isEmpty() || P2.isEmpty() || P3.isEmpty() || P4.isEmpty() || P5.isEmpty() || P6.isEmpty() || P7.isEmpty() || P8.isEmpty()){
                    Toast.makeText(CreateXoso.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.addLottery(Name, S, P1, P2, P3, P4, P5, P6, P7, P8);
                    finish();
                    recreate();
                }
            }
        });

        btnDeleteAllXoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

        btnReloadLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
    }


    void addControl(){
        btnBack_create_xoso=findViewById(R.id.btnBack_create_xoso);
        btnDeleteAllXoso=findViewById(R.id.btnDeleteAllLottery);
        Country = findViewById(R.id.edt_create_xoso_country);
        GDB = findViewById(R.id.edt_create_xoso_prizeS);
        G1 = findViewById(R.id.edt_create_xoso_prize1);
        G2 = findViewById(R.id.edt_create_xoso_prize2);
        G3 = findViewById(R.id.edt_create_xoso_prize3);
        G4 = findViewById(R.id.edt_create_xoso_prize4);
        G5 = findViewById(R.id.edt_create_xoso_prize5);
        G6 = findViewById(R.id.edt_create_xoso_prize6);
        G7 = findViewById(R.id.edt_create_xoso_prize7);
        G8 = findViewById(R.id.edt_create_xoso_prize8);
        btnReloadLottery=findViewById(R.id.btnReloadLottery);
        img_emptyview_xoso=findViewById(R.id.img_emptyview_xoso);
        textViewXoso=findViewById(R.id.textViewXoso);
        btn_create_xoso=findViewById(R.id.btn_create_xoso);
        rcv_xoso_admin=findViewById(R.id.rcv_xoso_admin);

        dbHelper=new DBHelper(this);
        ID=new ArrayList<>();
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

        adminXosoAdapter=new AdminXosoAdapter(CreateXoso.this, this, ID, NameCountry, PS, P1, P2, P3, P4, P5, P6, P7, P8);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateXoso.this, RecyclerView.HORIZONTAL, false);
        rcv_xoso_admin.setLayoutManager(linearLayoutManager);
        rcv_xoso_admin.setAdapter(adminXosoAdapter);
    }

    //lấy dữ liệu get lên rcv
    private void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataXosoAdmin();
        if(cursor.getCount() == 0){
            Toast.makeText(CreateXoso.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
            img_emptyview_xoso.setVisibility(View.VISIBLE);
            textViewXoso.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                ID.add(cursor.getString(0));
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
            img_emptyview_xoso.setVisibility(View.GONE);
            textViewXoso.setVisibility(View.GONE);
        }
    }

    //Tạo comfirm dialog để xóa tất cả dữ liệu trong bản
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa kết quả xổ số "+ ID + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tất cả kết quả xổ số này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(CreateXoso.this);
                dbHelper.deleteLotteryAllData();
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