package com.example.projectreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminSetting extends AppCompatActivity {
    String RoleAdmin;
    EditText edtNameAccount, edtPassAccount;
    Button btn_save_adminaccount;
    String[] item = {"Admin", "Employee"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    ImageButton btnBackAdminSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);
        //Khai báo
        btnBackAdminSetting=findViewById(R.id.btnBackAdminSetting);
        autoCompleteTextView=findViewById(R.id.autoCompleteText);
        edtNameAccount=findViewById(R.id.edtNameAccount);
        edtPassAccount=findViewById(R.id.edtPassAccount);
        btn_save_adminaccount=findViewById(R.id.btn_save_adminaccount);

        //Tạo sự kiện cho nút thêm AccountAdmin
        btn_save_adminaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(AdminSetting.this);
                dbHelper.addAdminAccount(edtNameAccount.getText().toString().trim(),
                        edtPassAccount.getText().toString().trim(), RoleAdmin);
                Intent intent = new Intent(AdminSetting.this, UIAdmin.class);
                startActivity(intent);
            }
        });


        //Dropdown Menu Role Admin
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_role_admin, item);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                RoleAdmin = "";
                RoleAdmin = item;
                Toast.makeText(AdminSetting.this, "Quyền Admin đã chọn: " + item, Toast.LENGTH_SHORT).show();
            }
        });


        //Tạo nút back
        btnBackAdminSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSetting.this, UIAdmin.class);
                startActivity(intent);
            }
        });
    }
}