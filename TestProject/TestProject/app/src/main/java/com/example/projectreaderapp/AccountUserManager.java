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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.AdminAccountAdapter;
import com.example.projectreaderapp.adapter.UserAccountAdapter;

import java.util.ArrayList;

public class AccountUserManager extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView img_emptyview_useraccount;
    ImageButton btnBacktoUserUser, btnDeleteAllAccountUser;
    TextView textViewuseraccount;
    DBHelper dbHelper;
    UserAccountAdapter adapter;
    ArrayList<String> IDUser, UserName, Fullname, Email, Phonemumber, Password;
    ArrayList<Bitmap> imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_user_manager);
        //Khai báo
        btnBacktoUserUser=findViewById(R.id.btnBacktoUserUser);
        img_emptyview_useraccount=findViewById(R.id.img_emptyview_useraccount);
        btnDeleteAllAccountUser=findViewById(R.id.btnDeleteAllAccountUser);
        recyclerView=findViewById(R.id.rcv_AccountUserManager);
        textViewuseraccount=findViewById(R.id.textViewuseraccount);


        //Tạo recyclerview cho user
        dbHelper = new DBHelper(AccountUserManager.this);
        IDUser = new ArrayList<>();
        UserName = new ArrayList<>();
        Fullname = new ArrayList<>();
        Email = new ArrayList<>();
        Phonemumber = new ArrayList<>();
        Password = new ArrayList<>();
        imgUser = new ArrayList<>();

        storeDataInArrays();

        adapter = new UserAccountAdapter(AccountUserManager.this,this, IDUser, UserName, Fullname, Email, Phonemumber, Password, imgUser);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AccountUserManager.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        //Set sự kiện cho các nút
        btnDeleteAllAccountUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

        btnBacktoUserUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataUserAccount();
        if(cursor.getCount() == 0){
            img_emptyview_useraccount.setVisibility(View.VISIBLE);
            textViewuseraccount.setVisibility(View.VISIBLE);
            Toast.makeText(AccountUserManager.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                IDUser.add(cursor.getString(0));
                UserName.add(cursor.getString(1));
                Fullname.add(cursor.getString(2));
                Email.add(cursor.getString(3));
                Phonemumber.add(cursor.getString(4));
                Password.add(cursor.getString(5));

                byte[] imageByte = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                imgUser.add(bitmap);
            }
            img_emptyview_useraccount.setVisibility(View.GONE);
            textViewuseraccount.setVisibility(View.GONE);
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
                DBHelper dbHelper = new DBHelper(AccountUserManager.this);
                dbHelper.deleteUserAccountAllData();
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

    //Làm mới lại giao diện
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 4){
            recreate();
        }
    }
}