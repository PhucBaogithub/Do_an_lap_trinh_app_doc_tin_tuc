package com.example.projectreaderapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectreaderapp.model.ModelClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    float x1, x2, y1, y2;
    DBHelper dbHelper;
    private int count = 0;
    private ImageButton btnBackUserSignUp;
    private Button btnuserSignUp, btnbacktoLogin;
    private ImageView btnimgv_user;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri imagePath;
    private Bitmap imageToStore;
    private TextInputEditText edttextusernameSignUp, edttextemailSignUp, edttextpasswordSignUp, edttextphonenumberlSignUp, edttextfullnameSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edttextusernameSignUp=findViewById(R.id.edttextusernameSignUp);
        edttextfullnameSignUp=findViewById(R.id.edttextfullnameSignUp);
        edttextemailSignUp=findViewById(R.id.edttextemailSignUp);
        edttextpasswordSignUp=findViewById(R.id.edttextpasswordSignUp);
        edttextphonenumberlSignUp=findViewById(R.id.edttextphonenumberlSignUp);
        btnBackUserSignUp=findViewById(R.id.btnBackUserSignUp);
        btnuserSignUp=findViewById(R.id.btnuserSignUp);
        btnbacktoLogin=findViewById(R.id.btnbacktoLogin);
        btnimgv_user=findViewById(R.id.btnimgv_user);
        dbHelper = new DBHelper(this);


        //Set sự kiện cho nút btnimgv_user
        btnimgv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        //Set sự kiện cho nút Sign Up
        btnuserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icError=getResources().getDrawable(R.drawable.ic_error);
                icError.setBounds(0,0,icError.getIntrinsicWidth(),icError.getIntrinsicHeight());

                String username = edttextusernameSignUp.getText().toString().trim();
                String fullname = edttextfullnameSignUp.getText().toString().trim();
                String phonenumber = edttextphonenumberlSignUp.getText().toString().trim();
                String email = edttextemailSignUp.getText().toString().trim();
                String password = edttextpasswordSignUp.getText().toString().trim();
                if(btnimgv_user.getDrawable() == null){
                    Toast.makeText(SignUp.this, "Bạn chưa tải ảnh lên!", Toast.LENGTH_LONG).show();
                }
                if(imageToStore == null){
                    Toast.makeText(SignUp.this, "Bạn chưa tải ảnh lên!", Toast.LENGTH_LONG).show();
                }
                if(username.isEmpty()){
                    edttextusernameSignUp.setCompoundDrawables(null, null, icError, null);
                    edttextusernameSignUp.setError("Vui lòng nhập tên đăng nhập!", icError);
                }
                if(fullname.isEmpty()){
                    edttextfullnameSignUp.setCompoundDrawables(null, null, icError, null);
                    edttextfullnameSignUp.setError("Vui lòng nhập đầy đủ họ tên!", icError);
                }
                if(phonenumber.isEmpty()){
                    edttextphonenumberlSignUp.setCompoundDrawables(null, null , icError, null);
                    edttextphonenumberlSignUp.setError("Vui lòng nhập số điện thoại!", icError);
                }
                if(email.isEmpty()){
                    edttextemailSignUp.setCompoundDrawables(null, null, icError, null);
                    edttextemailSignUp.setError("Vui lòng nhập Email!", icError);
                }
                if(password.isEmpty()){
                    edttextpasswordSignUp.setCompoundDrawables(null, null, icError, null);
                    edttextpasswordSignUp.setError("Vui lòng nhập mật khẩu!", icError);
                }
                if(!username.isEmpty()){
                    edttextusernameSignUp.setCompoundDrawables(null, null, null, null);
                if(!fullname.isEmpty())
                    edttextfullnameSignUp.setCompoundDrawables(null, null, null, null);
                }if (!phonenumber.isEmpty()) {
                    edttextphonenumberlSignUp.setCompoundDrawables(null, null, null, null);
                }if (!email.isEmpty()) {
                    String[] gmail = email.split("");
                    for(int i = 0; i < gmail.length; i++){
                        if(gmail[i].equals("@")){
                            count=count+1;
                        }else{
                            edttextemailSignUp.setCompoundDrawables(null, null, icError, null);
                            edttextemailSignUp.setError("Vui lòng nhập đúng định dạng Email(@gmail.com)!", icError);
                        }
                    }
                }if (!password.isEmpty()) {
                    edttextpasswordSignUp.setCompoundDrawables(null, null, null, null);
                }if(count==1){
                    String[] getmail = email.split("@");
                    if (getmail[1].equals("gmail.com")){
                        edttextemailSignUp.setCompoundDrawables(null, null, null, null);
                        edttextemailSignUp.setError(null);
                        count=0;
                        Boolean checkusername = dbHelper.checkusername(username);
                        if(checkusername==false){
                            edttextusernameSignUp.setCompoundDrawables(null, null, null, null);
                            edttextphonenumberlSignUp.setCompoundDrawables(null, null, null, null);
                            edttextemailSignUp.setCompoundDrawables(null, null, null, null);
                            edttextpasswordSignUp.setCompoundDrawables(null, null, null, null);
                            dbHelper.storeData(new ModelClass(username, fullname, email, phonenumber, password,imageToStore));
                            Toast.makeText(SignUp.this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUp.this, Login.class);
                            startActivityForResult(i,3);
                        }else{
                            Toast.makeText(SignUp.this, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        edttextemailSignUp.setCompoundDrawables(null, null, icError, null);
                        edttextemailSignUp.setError("Vui lòng nhập đúng định dạng Email(@gmail.com)!", icError);
                    }
                }
            }
        });

        //Set sự kiện cho nút back Sign Up
        btnBackUserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, User.class);
                startActivity(i);
            }
        });

        //Set sự kiện cho nút trở lại Login
        btnbacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
            }
        });
    }

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
                    btnimgv_user.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Intent i = new Intent(SignUp.this, User.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}