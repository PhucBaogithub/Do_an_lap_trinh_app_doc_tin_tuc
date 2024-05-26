package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DeleteUser extends AppCompatActivity {

    ImageButton btnBackDeleteUser;
    Button btn_delete_user_account;
    String idUser, nameUser;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        //Khai báo
        btnBackDeleteUser=findViewById(R.id.btnBackDeleteUser);
        btn_delete_user_account=findViewById(R.id.btn_delete_user_account);
        dbHelper=new DBHelper(DeleteUser.this);



        //Set sự kiện cho các nút
        btnBackDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_delete_user_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idUser=getIntent().getStringExtra("IDUser");
                nameUser=getIntent().getStringExtra("NameUser");
                ConfirmDialog();
            }
        });

    }

    void ConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa tài khoản" + nameUser + "?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản" + nameUser + "?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteUserAccountOneRow(idUser);
                finish();
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