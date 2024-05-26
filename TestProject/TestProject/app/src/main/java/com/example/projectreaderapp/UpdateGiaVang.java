package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateGiaVang extends AppCompatActivity {

    ImageButton btnBack_update_gold_price;
    Button btn_update_price_gold, btn_delete_price_gold;
    ImageView img_gold_price_update;
    EditText edt_update_gold_price_namecountry, edt_update_gold_price_buy, edt_update_gold_price_sold;
    private Uri imagePath;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Bitmap imageToStore;
    String ID, NameCountry, Buy, Sold;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gia_vang);
        //Khai báo
        addControl();

        //Set sự kiện
        btnBack_update_gold_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_gold_price_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        btn_update_price_gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = edt_update_gold_price_namecountry.getText().toString().trim();
                String B = edt_update_gold_price_buy.getText().toString().trim();
                String S = edt_update_gold_price_sold.getText().toString().trim();
                if(Name.isEmpty() || B.isEmpty() || S.isEmpty() || imageToStore==null){
                    Toast.makeText(UpdateGiaVang.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateGiaVang(ID, Name, B, S, imageToStore);
                    finish();
                }
            }
        });

        btn_delete_price_gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });
    }

    void addControl(){
        btnBack_update_gold_price=findViewById(R.id.btnBack_update_gold_price);
        btn_update_price_gold=findViewById(R.id.btn_update_price_gold);
        img_gold_price_update=findViewById(R.id.img_gold_price_update);
        edt_update_gold_price_namecountry=findViewById(R.id.edt_update_gold_price_namecountry);
        edt_update_gold_price_buy=findViewById(R.id.edt_update_gold_price_buy);
        edt_update_gold_price_sold=findViewById(R.id.edt_update_gold_price_sold);
        btn_delete_price_gold=findViewById(R.id.btn_delete_price_gold);
        dbHelper=new DBHelper(this);
        getAndsetIntentData();
    }

    //Tạo xác nhận xóa
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa Giá Vàng"+ ID + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa Giá Vàng này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateGiaVang.this);
                dbHelper.deleteGiaVangOneRow(ID);
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

    void getAndsetIntentData(){
        if(getIntent().hasExtra("ID") && getIntent().hasExtra("NameCountry") &&
                getIntent().hasExtra("Buy") && getIntent().hasExtra("Sold")){
            ID = getIntent().getStringExtra("ID");
            NameCountry = getIntent().getStringExtra("NameCountry");
            Buy = getIntent().getStringExtra("Buy");
            Sold = getIntent().getStringExtra("Sold");

            //Cài đặt intent data
            edt_update_gold_price_namecountry.setText(NameCountry);
            edt_update_gold_price_buy.setText(Buy);
            edt_update_gold_price_sold.setText(Sold);
        }else{
            Toast.makeText(this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }
    }

    //Lấy ảnh từ ImageView
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
                img_gold_price_update.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}