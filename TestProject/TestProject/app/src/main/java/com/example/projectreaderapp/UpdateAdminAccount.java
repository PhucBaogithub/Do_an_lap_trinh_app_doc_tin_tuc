package com.example.projectreaderapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateAdminAccount extends AppCompatActivity {

    EditText edtNameAccountUpdate, edtPassAccountUpdate;
    String RoleAdminChoose;
    String[] item = {"Admin", "Employee"};
    ArrayAdapter<String> adapterItems;
    ImageButton btn_back_update_adminaccount;
    AutoCompleteTextView autoCompleteTextUpdate;
    Button btn_update_adminaccount, btn_delete_adminaccount;
    String IDAdmin, UserName, Password, RoleAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin_account);
        //Khai báo
        btn_back_update_adminaccount=findViewById(R.id.btn_back_update_adminaccount);
        edtNameAccountUpdate=findViewById(R.id.edtNameAccountUpdate);
        edtPassAccountUpdate=findViewById(R.id.edtPassAccountUpdate);
        autoCompleteTextUpdate=findViewById(R.id.autoCompleteTextUpdate);
        btn_update_adminaccount=findViewById(R.id.btn_update_adminaccount);
        btn_delete_adminaccount=findViewById(R.id.btn_delete_adminaccount);

        //Khai báo sự kiện cho nút back
        btn_back_update_adminaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getAndsetIntentData();

        //Set sự kiện cho nút update
        btn_update_adminaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(UpdateAdminAccount.this);
                UserName=edtNameAccountUpdate.getText().toString().trim();
                Password=edtPassAccountUpdate.getText().toString().trim();
                RoleAdmin=RoleAdminChoose;
                if(UserName.equals("")){
                    Toast.makeText(UpdateAdminAccount.this, "Bạn chưa nhập tên tài khoản Admin!", Toast.LENGTH_SHORT).show();
                }if(Password.equals("")){
                    Toast.makeText(UpdateAdminAccount.this, "Bạn chưa nhập mật khẩu tài khoản Admin!", Toast.LENGTH_SHORT).show();
                }if(RoleAdmin.equals("0")){
                    Toast.makeText(UpdateAdminAccount.this, "Bạn chưa chọn quyền cho tài khoản Admin!", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateAdminAccount(IDAdmin, UserName, Password, RoleAdmin);
                    Intent intent = new Intent(UpdateAdminAccount.this, UIAdmin.class);
                    startActivity(intent);
                }
            }
        });

        //set sự kiện cho nút delete
        btn_delete_adminaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });



        //Dropdown Menu Role Admin
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_role_admin, item);
        autoCompleteTextUpdate.setAdapter(adapterItems);

        autoCompleteTextUpdate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                RoleAdminChoose = "";
                RoleAdminChoose = item;
                Toast.makeText(UpdateAdminAccount.this, "Quyền Admin đã chọn: " + item, Toast.LENGTH_SHORT).show();
            }
        });

    }
    void getAndsetIntentData(){
        if(getIntent().hasExtra("IDAdmin") && getIntent().hasExtra("UserName") &&
                getIntent().hasExtra("Password") && getIntent().hasExtra("RoleAdmin")){
            IDAdmin = getIntent().getStringExtra("IDAdmin");
            UserName = getIntent().getStringExtra("UserName");
            Password = getIntent().getStringExtra("Password");
            RoleAdmin = getIntent().getStringExtra("RoleAdmin");

            //Cài đặt intent data
            edtNameAccountUpdate.setText(UserName);
            edtPassAccountUpdate.setText(Password);
            autoCompleteTextUpdate.setText(RoleAdmin);
        }else{
            Toast.makeText(this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }
    }

    //Tạo xác nhận xóa
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa tài khoản"+ UserName + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateAdminAccount.this);
                dbHelper.deleteAdminAccountOneRow(IDAdmin);
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