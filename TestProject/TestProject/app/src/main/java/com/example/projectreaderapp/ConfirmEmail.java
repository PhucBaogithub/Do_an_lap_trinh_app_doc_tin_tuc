package com.example.projectreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

public class ConfirmEmail extends AppCompatActivity {
    DBHelper dbHelper;
    TextInputEditText edtConfirmEmail;
    Button btnConfirmEmail;
    ImageButton btnBackConfirmEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);
        //Khai báo
        edtConfirmEmail=findViewById(R.id.edtConfirmEmail);
        btnConfirmEmail=findViewById(R.id.btnConfirmEmail);
        btnBackConfirmEmail=findViewById(R.id.btnBackConfirmEmail);
        Random r = new Random();
        int[] fiveRandomNumbers = r.ints(6, 0, 11).toArray();
        dbHelper = new DBHelper(ConfirmEmail.this);


        //Set sự kiện cho các nút
        btnBackConfirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmEmail.this, Login.class);
                startActivity(i);
            }
        });

        btnConfirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = edtConfirmEmail.getText().toString();
                Boolean getemail = dbHelper.checkEmail(Email);

                if(getemail==true){
                    //
                    sendEmail("baominecraft12344@gmail.com","ugpskmmwqvfktif",Email, "Hello Word","559595");
                }else{
                    Toast.makeText(ConfirmEmail.this, "Bạn nhập sai Email mời nhập lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendEmail(final String Sender,final String Password,final String Receiver,final String Title,final String Message)
    {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Sender,Password);
                    sender.sendMail(Title, "<b>"+Message+"</b>", Sender, Receiver);
                    makeAlert();

                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }
    private void makeAlert(){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ConfirmEmail.this, "Mail Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }
}