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

public class UpdateXoso extends AppCompatActivity {

    ImageButton btnBack_update_xoso;
    EditText Country, GDB, G1, G2, G3, G4 , G5, G6, G7, G8;
    Button btn_update_xoso, btn_delete_xoso;
    String ID, NameCountry, PS, P1, P2, P3, P4, P5, P6, P7, P8;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_xoso);
        //Khai báo
        addControl();


        //Tạo sự kiện cho button
        btnBack_update_xoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_update_xoso.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(UpdateXoso.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateLottery(ID, Name, S, P1, P2, P3, P4, P5, P6, P7, P8);
                    finish();
                    recreate();
                }
            }
        });

        btn_delete_xoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });
    }

    void addControl(){
        btnBack_update_xoso=findViewById(R.id.btnBack_update_xoso);
        Country = findViewById(R.id.edt_update_xoso_country);
        GDB = findViewById(R.id.edt_update_xoso_prizeS);
        G1 = findViewById(R.id.edt_update_xoso_prize1);
        G2 = findViewById(R.id.edt_update_xoso_prize2);
        G3 = findViewById(R.id.edt_update_xoso_prize3);
        G4 = findViewById(R.id.edt_update_xoso_prize4);
        G5 = findViewById(R.id.edt_update_xoso_prize5);
        G6 = findViewById(R.id.edt_update_xoso_prize6);
        G7 = findViewById(R.id.edt_update_xoso_prize7);
        G8 = findViewById(R.id.edt_update_xoso_prize8);
        btn_update_xoso=findViewById(R.id.btn_update_xoso);
        btn_delete_xoso=findViewById(R.id.btn_delete_xoso);
        dbHelper=new DBHelper(this);
        getAndsetIntentData();


    }

    void getAndsetIntentData(){
        if(getIntent().hasExtra("ID") && getIntent().hasExtra("NameCountry") &&
                getIntent().hasExtra("PS") && getIntent().hasExtra("P1")
                && getIntent().hasExtra("P2") && getIntent().hasExtra("P3")
                && getIntent().hasExtra("P4") && getIntent().hasExtra("P5")
                && getIntent().hasExtra("P6") && getIntent().hasExtra("P7")
                && getIntent().hasExtra("P8")){
            ID = getIntent().getStringExtra("ID");
            NameCountry = getIntent().getStringExtra("NameCountry");
            PS = getIntent().getStringExtra("PS");
            P1 = getIntent().getStringExtra("P1");
            P2 = getIntent().getStringExtra("P2");
            P3 = getIntent().getStringExtra("P3");
            P4 = getIntent().getStringExtra("P4");
            P5 = getIntent().getStringExtra("P5");
            P6 = getIntent().getStringExtra("P6");
            P7 = getIntent().getStringExtra("P7");
            P8 = getIntent().getStringExtra("P8");


            //Cài đặt intent data
            Country.setText(NameCountry);
            GDB.setText(PS);
            G1.setText(P1);
            G2.setText(P2);
            G3.setText(P3);
            G4.setText(P4);
            G5.setText(P5);
            G6.setText(P6);
            G7.setText(P7);
            G8.setText(P8);
        }else{
            Toast.makeText(this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }
    }

    //Tạo xác nhận xóa
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa kết quả xổ số với ID "+ ID + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa kết quả xổ số này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateXoso.this);
                dbHelper.deleteLotteryOneRow(Integer.valueOf(ID));
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