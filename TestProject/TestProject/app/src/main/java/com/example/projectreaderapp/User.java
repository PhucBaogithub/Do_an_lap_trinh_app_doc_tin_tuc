package com.example.projectreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class User extends AppCompatActivity {
    float x1, x2, y1, y2;
    private Button btnLogin, btnSignUp;
    private ImageButton btnBackUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        btnLogin = findViewById(R.id.btnSignIn);
        btnSignUp= findViewById(R.id.btnSignUp);
        btnBackUser= findViewById(R.id.btnBackUser);
        //Set sự kiện cho nút đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User.this, Login.class);
                startActivity(i);
            }
        });
        //Set sự kiện cho nút đăng ký
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User.this, SignUp.class);
                startActivity(i);
            }
        });
        //Set sự kiện cho nút back User
        btnBackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    //Set sự kiện quẹt trái để trở về Main
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
                    Intent i = new Intent(User.this, MainActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}