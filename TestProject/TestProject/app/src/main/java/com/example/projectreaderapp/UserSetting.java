package com.example.projectreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserSetting extends AppCompatActivity {

    ImageButton btnBackUserSetting;
    ImageView imgv_user_setting;
    TextView txtFullname, txtPhone, txtEmail;
    Button btnBacktoMain, btnLogout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        btnBackUserSetting=findViewById(R.id.btnBackUserSetting);
        imgv_user_setting=findViewById(R.id.imgv_user_setting);
        txtFullname=findViewById(R.id.txtFullname);
        txtPhone=findViewById(R.id.txtPhone);
        txtEmail=findViewById(R.id.txtEmail);
        btnBacktoMain=findViewById(R.id.btnBacktoMain);
        btnLogout=findViewById(R.id.btnLogout);

        DBHelper dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.getUser(new PrefManager(UserSetting.this).getUsername());
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "No entires", Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                txtFullname.setText(""+cursor.getString(2));
                txtEmail.setText(""+cursor.getString(3));
                txtPhone.setText(""+cursor.getString(4));


                byte[] imageByte = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                imgv_user_setting.setImageBitmap(bitmap);
            }
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrefManager(UserSetting.this).saveLoginDetails("");
                Toast.makeText(UserSetting.this, "Đã đăng xuất thành công!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(UserSetting.this, User.class);
                startActivity(i);
            }
        });

        btnBacktoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSetting.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnBackUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSetting.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    public String getUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", "");
    }
}