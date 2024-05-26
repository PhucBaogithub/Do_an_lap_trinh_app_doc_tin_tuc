package fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectreaderapp.DBHelper;
import com.example.projectreaderapp.model.Category;
import com.example.projectreaderapp.adapter.CategoryAdapter;
import com.example.projectreaderapp.MainActivity;
import com.example.projectreaderapp.model.News;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.TranslateAnimationUtil;

import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment {

    private RecyclerView rcvNewsTrending;
    private CategoryAdapter categoryAdapter;
    public DBHelper dbHelper;

    List<Category> categoryList;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        //Tạo recyclerview
        rcvNewsTrending = view.findViewById(R.id.rcv_newsTrending);
        dbHelper = new DBHelper(getContext());
        categoryAdapter = new CategoryAdapter(getContext(),getActivity());
        categoryList = new ArrayList<>();

        categoryList.add(new Category("Đang trên xu hướng", getListNews()));
        categoryList.add(new Category("Được quan tâm nhiều nhất", getListNews1()));

        categoryAdapter.setData(categoryList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvNewsTrending.setLayoutManager(linearLayoutManager);
        rcvNewsTrending.setAdapter(categoryAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        rcvNewsTrending.addItemDecoration(itemDecoration);
        rcvNewsTrending.setOnTouchListener(new TranslateAnimationUtil(getContext(), MainActivity.bottomNavigationView));
        return view;
    }
    private List<News> getListNews() {
        List<News> list = new ArrayList<>();

        Cursor cursor = dbHelper.readAllDataNewsTrending();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                String newsid =  cursor.getString(0);
                String newsname = cursor.getString(1);

                byte[] imageByte = cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                Bitmap imagenews = bitmap;

                byte[] imageByte1 = cursor.getBlob(10);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageByte1, 0, imageByte1.length);
                Bitmap imagenewslogo = bitmap1;

                String newsauthor = cursor.getString(9);

                News news = new News(imagenews, imagenewslogo, newsname, newsid, newsauthor);
                list.add(news);
            }
        }
        return list;
    }

    private List<News> getListNews1() {
        List<News> list = new ArrayList<>();

        Cursor cursor = dbHelper.readAllDataNewsTrending1();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                String newsid =  cursor.getString(0);
                String newsname = cursor.getString(1);

                byte[] imageByte = cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                Bitmap imagenews = bitmap;

                byte[] imageByte1 = cursor.getBlob(10);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageByte1, 0, imageByte1.length);
                Bitmap imagenewslogo = bitmap1;

                String newsauthor = cursor.getString(9);

                News news = new News(imagenews, imagenewslogo, newsname, newsid, newsauthor);
                list.add(news);
            }
        }
        return list;
    }

    //Lam moi lai giao dien
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 5){
            activity.recreate();
        }
    }
}