package fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectreaderapp.CreateSourceNews;
import com.example.projectreaderapp.DBHelper;
import com.example.projectreaderapp.MainActivity;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.adapter.SourceAdapter;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    ImageView btnViewNong, btnViewMoi, btnViewSuckhoe, btnViewGiaitri, btnViewTinhyeu, btnViewThegioi, btnViewXe, btnViewCongnghe;
    RecyclerView rcv_category;
    DBHelper dbHelper;
    ArrayList<Bitmap> logo;
    SourceAdapter sourceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        //Khai báo
        btnViewNong = view.findViewById(R.id.btn_ViewNong);
        btnViewMoi = view.findViewById(R.id.btn_ViewMoi);
        btnViewSuckhoe = view.findViewById(R.id.btn_ViewSuckhoe);
        btnViewGiaitri = view.findViewById(R.id.btn_ViewGiaitri);
        btnViewTinhyeu = view.findViewById(R.id.btn_ViewTinhyeu);
        btnViewThegioi = view.findViewById(R.id.btn_ViewThegioi);
        btnViewXe = view.findViewById(R.id.btn_ViewXe);
        btnViewCongnghe = view.findViewById(R.id.btn_ViewCongnghe);
        rcv_category=view.findViewById(R.id.rcv_category);
        dbHelper = new DBHelper(getActivity());
        logo=new ArrayList<>();

        storeDataInArrays();

        sourceAdapter = new SourceAdapter(getActivity(), getContext(), logo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcv_category.setLayoutManager(linearLayoutManager);
        rcv_category.setAdapter(sourceAdapter);

        //Set sự kiện các nút
        btnViewNong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(1);
            }
        });

        btnViewMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(2);
            }
        });

        btnViewSuckhoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(3);
            }
        });

        btnViewGiaitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(4);
            }
        });

        btnViewTinhyeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(5);
            }
        });

        btnViewThegioi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(6);
            }
        });

        btnViewXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(7);
            }
        });

        btnViewCongnghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(8);
            }
        });

        return view;
    }

    //lấy dữ liệu get lên rcv
    public void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllDataSourceNews();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "Không có dữ liệu...", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                byte[] imageByte = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                logo.add(bitmap);
            }
        }
    }
}