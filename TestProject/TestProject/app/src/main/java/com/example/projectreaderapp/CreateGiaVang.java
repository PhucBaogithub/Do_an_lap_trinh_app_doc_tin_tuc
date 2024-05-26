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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.AdminGiaVangAdapter;

import java.util.ArrayList;

public class CreateGiaVang extends AppCompatActivity {

    ImageButton btnBack_create_gold_price, btnDeleteAllGiaVang;
    ImageView img_gold_price, img_emptyview_price_gold;
    EditText edt_create_gold_price_namecountry, edt_create_gold_price_buy, edt_create_gold_price_sold;
    TextView textViewPriceGold;
    Button btn_create_price_gold;
    private Uri imagePath;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Bitmap imageToStore;
    DBHelper dbHelper;
    ArrayList<String> ID, NameCountry, Buy, Sold;
    ArrayList<Bitmap> Logo;
    AdminGiaVangAdapter adminGiaVangAdapter;
    RecyclerView rcv_gold_price_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_gia_vang);
        //Khai báo
        addControl();
        //Tạo sự kiện
        btnBack_create_gold_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        img_gold_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        btn_create_price_gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameCountry = edt_create_gold_price_namecountry.getText().toString().trim();
                String buy = edt_create_gold_price_buy.getText().toString().trim();
                String sold = edt_create_gold_price_sold.getText().toString().trim();
                if(NameCountry.isEmpty() || buy.isEmpty() || sold.isEmpty() || imageToStore == null){
                    Toast.makeText(CreateGiaVang.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.addPriceGold(NameCountry, Integer.valueOf(buy), Integer.valueOf(sold), imageToStore);
                    finish();
                    recreate();
                }
            }
        });

        btnDeleteAllGiaVang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

    }

    void addControl(){
        btnBack_create_gold_price=findViewById(R.id.btnBack_create_gold_price);
        img_gold_price=findViewById(R.id.img_gold_price);
        edt_create_gold_price_namecountry=findViewById(R.id.edt_create_gold_price_namecountry);
        edt_create_gold_price_buy=findViewById(R.id.edt_create_gold_price_buy);
        edt_create_gold_price_sold=findViewById(R.id.edt_create_gold_price_sold);
        btn_create_price_gold=findViewById(R.id.btn_create_price_gold);
        img_emptyview_price_gold=findViewById(R.id.img_emptyview_price_gold);
        textViewPriceGold=findViewById(R.id.textViewPriceGold);
        rcv_gold_price_admin=findViewById(R.id.rcv_gold_price_admin);
        btnDeleteAllGiaVang=findViewById(R.id.btnDeleteAllGiaVang);
        dbHelper=new DBHelper(CreateGiaVang.this);
        ID=new ArrayList<>();
        NameCountry=new ArrayList<>();
        Buy=new ArrayList<>();
        Sold=new ArrayList<>();
        Logo=new ArrayList<>();


        storeDataInArrays();


        adminGiaVangAdapter=new AdminGiaVangAdapter(CreateGiaVang.this,this, ID, NameCountry, Buy, Sold, Logo);
        rcv_gold_price_admin.setLayoutManager(new LinearLayoutManager(CreateGiaVang.this));
        rcv_gold_price_admin.setAdapter(adminGiaVangAdapter);
    }

    //Lấy dữ liệu từ database gán cho arraylist
    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataGiaVangAdmin();
        if(cursor.getCount() == 0){
            Toast.makeText(CreateGiaVang.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
            img_emptyview_price_gold.setVisibility(View.VISIBLE);
            textViewPriceGold.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                ID.add(cursor.getString(0));
                NameCountry.add(cursor.getString(1));
                Buy.add(cursor.getString(2));
                Sold.add(cursor.getString(3));

                byte[] imageByte = cursor.getBlob(4);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                Logo.add(bitmap);
            }
            img_emptyview_price_gold.setVisibility(View.GONE);
            textViewPriceGold.setVisibility(View.GONE);
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
                img_gold_price.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Tạo comfirm dialog để xóa tất cả dữ liệu trong bản
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa TẤT CẢ giá vàng "+ ID + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tất cả giá vàng này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(CreateGiaVang.this);
                dbHelper.deleteGiaVangAllData();
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