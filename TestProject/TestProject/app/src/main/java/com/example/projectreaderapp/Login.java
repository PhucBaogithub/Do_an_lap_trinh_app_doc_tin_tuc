package com.example.projectreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    float x1, x2, y1, y2;

    private TextInputEditText usernameEditText, passwordEditText;
    private Button loginButton, btnSignUp;
    private ImageButton btnBackUserLogin;
    TextView forgetPasswordLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Set sự kiện cho nút Login
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        btnBackUserLogin = findViewById(R.id.btnBackUserLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        forgetPasswordLogin=findViewById(R.id.forgetPasswordLogin);
        dbHelper = new DBHelper(this);


        //Set su kiện cho nút đăng nhập
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icError=getResources().getDrawable(R.drawable.ic_error);
                icError.setBounds(0,0,icError.getIntrinsicWidth(),icError.getIntrinsicHeight());

                String username=usernameEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();
                if(username.isEmpty()){
                    usernameEditText.setCompoundDrawables(null, null, icError, null);
                    usernameEditText.setError("Vui lòng nhập tên đăng nhập!", icError);
                }
                if(password.isEmpty()){
                    passwordEditText.setCompoundDrawables(null, null, icError, null);
                    passwordEditText.setError("Vui lòng nhập mật khẩu!", icError);
                }
                if(!username.isEmpty()){
                    usernameEditText.setCompoundDrawables(null, null, null, null);
                }
                if(!password.isEmpty()){
                    passwordEditText.setCompoundDrawables(null, null, null, null);
                }
                if(!username.isEmpty() && !password.isEmpty()){
                    Boolean checkuserpass = dbHelper.checkusernamepassword(username, password);
                    Boolean checkadminaccount = dbHelper.checkAdminAccount(username, password);
                    Boolean checkemployeeaccount = dbHelper.checkEmployeeAccount(username, password);
                    if(checkuserpass==true){
                        Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, UserSetting.class);
                        new PrefManager(Login.this).saveLoginDetails(username);
                        startActivity(i);
                    }if(checkadminaccount==true){
                        Toast.makeText(Login.this, "Đăng nhập thành công với quyền của quản trị viên", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, UIAdmin.class);
                        startActivity(i);
                    }if(checkemployeeaccount==true){
                        Toast.makeText(Login.this, "Đăng nhập thành công với quyền của nhân viên", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, UIEmployee.class);
                        startActivity(i);
                    }
                }else{
                    Toast.makeText(Login.this, "Tài khoản không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Set sự kiện cho nút quên mật khẩu
        forgetPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ConfirmEmail.class);
                startActivity(i);
            }
        });

        //Set sự kiện cho nút tạo mới tài khoản
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });

        //Set sự kiện cho nút back trong Login form
        btnBackUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, User.class);
                startActivity(i);
            }
        });
    }
    //Set sự kiện quẹt trái để trở về User
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
                    Intent i = new Intent(Login.this, User.class);
                    startActivity(i);
                }
            break;
        }
        return false;
    }
}