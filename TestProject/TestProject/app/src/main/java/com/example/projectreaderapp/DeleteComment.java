package com.example.projectreaderapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DeleteComment extends AppCompatActivity {

    ImageButton btnBackDeleteComment;
    Button btn_delete_comment_news;
    DBHelper dbHelper;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_comment);
        //Khai báo
        btnBackDeleteComment=findViewById(R.id.btnBackDeleteComment);
        btn_delete_comment_news=findViewById(R.id.btn_delete_comment_news);
        dbHelper=new DBHelper(DeleteComment.this);


        //Tạo sự kiện cho các nút
        btnBackDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_delete_comment_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID=getIntent().getStringExtra("IDComment");
                ConfirmDialog();
            }
        });
    }

    void ConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa bình luận" + " " + ID + " " + "?");
        builder.setMessage("Bạn có chắc chắn muốn xóa bình luận" + " " + ID + " " + "?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteCommentOneRow(ID);
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