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

import com.example.projectreaderapp.adapter.AdminCommentsAdapter;

import java.util.ArrayList;
import java.util.BitSet;

public class CommentManager extends AppCompatActivity {

    ImageButton btnBackCommentAdmin, btnDeleteAllComments;
    ImageView img_emptyview_comment_admin;
    TextView textViewcommentadmin;
    RecyclerView rcv_CommentAdmin;
    DBHelper dbHelper;
    ArrayList<String> ID, UserName, Comment, NameNews;
    ArrayList<Bitmap> ima_ava_user;
    AdminCommentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_manager);
        //Khai báo
        btnBackCommentAdmin=findViewById(R.id.btnBackCommentAdmin);
        btnDeleteAllComments=findViewById(R.id.btnDeleteAllComments);
        rcv_CommentAdmin=findViewById(R.id.rcv_CommentAdmin);
        img_emptyview_comment_admin=findViewById(R.id.img_emptyview_comment_admin);
        textViewcommentadmin=findViewById(R.id.textViewcommentadmin);
        dbHelper = new DBHelper(CommentManager.this);
        ID=new ArrayList<>();
        UserName=new ArrayList<>();
        Comment=new ArrayList<>();
        NameNews=new ArrayList<>();
        ima_ava_user=new ArrayList<>();

        storeDataInArrays();

        adapter = new AdminCommentsAdapter(CommentManager.this, this, ID, UserName, Comment, NameNews, ima_ava_user);
        rcv_CommentAdmin.setLayoutManager(new LinearLayoutManager(this));
        rcv_CommentAdmin.setAdapter(adapter);

        //Tạo sự kiện cho các nút
        btnBackCommentAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDeleteAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });
    }

    //Tạo comfirm dialog để xóa tất cả dữ liệu trong bản
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa bình luận"+ ID);
        builder.setMessage("Bạn có chắc chắn muốn xóa " + ID + "này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(CommentManager.this);
                dbHelper.deleteCommentAllData();
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
        if(requestCode == 7){
            recreate();
        }
    }


    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataComments();
        if(cursor.getCount() == 0){
            img_emptyview_comment_admin.setVisibility(View.VISIBLE);
            textViewcommentadmin.setVisibility(View.VISIBLE);
            Toast.makeText(CommentManager.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                ID.add(cursor.getString(0));
                Comment.add(cursor.getString(1));
                UserName.add(cursor.getString(12));
                NameNews.add(cursor.getString(5));

                byte[] imageByte = cursor.getBlob(16);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                ima_ava_user.add(bitmap);
            }
            img_emptyview_comment_admin.setVisibility(View.GONE);
            textViewcommentadmin.setVisibility(View.GONE);
        }
    }
}