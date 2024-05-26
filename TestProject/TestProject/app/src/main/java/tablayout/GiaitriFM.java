package tablayout;

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
import com.example.projectreaderapp.MainActivity;
import com.example.projectreaderapp.adapter.NewsDisplayAdapter;
import com.example.projectreaderapp.model.News;
import com.example.projectreaderapp.adapter.NewsAdapter;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.TranslateAnimationUtil;

import java.util.ArrayList;
import java.util.List;

public class GiaitriFM extends Fragment {

    private RecyclerView rcvNewsGiaitri;
    private NewsAdapter newsAdapter;
    public DBHelper dbHelper;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_giaitri_f_m, container, false);
        rcvNewsGiaitri=view.findViewById(R.id.rcv_newsGiaitri);
        dbHelper = new DBHelper(getContext());
        newsAdapter = new NewsAdapter(getContext(),getActivity());
        newsAdapter.setData(getListNews());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        rcvNewsGiaitri.setLayoutManager(linearLayoutManager);
        rcvNewsGiaitri.setAdapter(newsAdapter);

        //set sự kiện ẩn nav
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcvNewsGiaitri.addItemDecoration(itemDecoration);
        rcvNewsGiaitri.setOnTouchListener(new TranslateAnimationUtil(getContext(), MainActivity.bottomNavigationView));
        return view;
    }

    private List<News> getListNews() {
        List<News> list = new ArrayList<>();

        Cursor cursor = dbHelper.readAllDataNewsEntertainment();
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
        if(requestCode == 2){
            activity.recreate();
        }
    }
}