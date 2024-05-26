
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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.SourceNewsAdapter;
import com.example.projectreaderapp.model.SourceNews;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CreateSourceNews extends AppCompatActivity {

    ImageView btnimgv_logo_sourcenews, img_emptyview_source_news;
    ImageButton btnBacksrnews, btnReloadsrnews, btnDeleteAllSourceNews;
    EditText edt_create_source_news;
    TextView textViewSourceNews;
    Button btn_source_cate_news;
    private Uri imagePath;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Bitmap imageToStore;
    DBHelper dbHelper;
    ArrayList<String> name, id;
    ArrayList<Bitmap> logo;
    RecyclerView rcv_display_source_news;
    SourceNewsAdapter sourceNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_source_news);
        //Khai báo
        btnimgv_logo_sourcenews=findViewById(R.id.btnimgv_logo_sourcenews);
        btnBacksrnews=findViewById(R.id.btnBacksrnews);
        edt_create_source_news=findViewById(R.id.edt_create_source_news);
        btn_source_cate_news=findViewById(R.id.btn_source_cate_news);
        rcv_display_source_news=findViewById(R.id.rcv_display_source_news);
        btnReloadsrnews=findViewById(R.id.btnReloadsrnews);
        btnDeleteAllSourceNews=findViewById(R.id.btnDeleteAllSourceNews);
        img_emptyview_source_news=findViewById(R.id.img_emptyview_source_news);
        textViewSourceNews=findViewById(R.id.textViewSourceNews);
        dbHelper = new DBHelper(CreateSourceNews.this);
        id=new ArrayList<>();
        name=new ArrayList<>();
        logo=new ArrayList<>();

        storeDataInArrays();

        sourceNewsAdapter = new SourceNewsAdapter(CreateSourceNews.this ,this, id, name, logo);
        rcv_display_source_news.setLayoutManager(new LinearLayoutManager(this));
        rcv_display_source_news.setAdapter(sourceNewsAdapter);


        //Set sự kiện cho các nút
        btnDeleteAllSourceNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirmDialog();
            }
        });

        btnReloadsrnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateSourceNews.this.recreate();
            }
        });

        btnimgv_logo_sourcenews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        btnBacksrnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_source_cate_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameSourseNews = edt_create_source_news.getText().toString().trim();
                if(btnimgv_logo_sourcenews.getDrawable() == null){
                    Toast.makeText(CreateSourceNews.this, "Bạn chưa tải ảnh lên!", Toast.LENGTH_LONG).show();
                }if(imageToStore == null){
                    Toast.makeText(CreateSourceNews.this, "Bạn chưa tải ảnh lên!", Toast.LENGTH_LONG).show();
                }if(NameSourseNews.isEmpty()){
                    Toast.makeText(CreateSourceNews.this, "Bạn chưa điền tên nguồn báo!", Toast.LENGTH_LONG).show();
                }else{
                    DBHelper dbHelper = new DBHelper(CreateSourceNews.this);
                    dbHelper.addSourceNews(new SourceNews(NameSourseNews, imageToStore));
                    finish();
                    recreate();
                }
            }
        });
    }

    //lấy dữ liệu get lên rcv
    private void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataSourceNews();
        if(cursor.getCount() == 0){
            Toast.makeText(CreateSourceNews.this, "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
            img_emptyview_source_news.setVisibility(View.VISIBLE);
            textViewSourceNews.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));

                byte[] imageByte = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                logo.add(bitmap);
            }
            img_emptyview_source_news.setVisibility(View.GONE);
            textViewSourceNews.setVisibility(View.GONE);
        }
    }

    //Lấy ảnh từ ImageView
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
                btnimgv_logo_sourcenews.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Tạo comfirm dialog để xóa tất cả dữ liệu trong bản
    void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa tài khoản"+ id + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(CreateSourceNews.this);
                dbHelper.deleteSourceNewsAllData();
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
}