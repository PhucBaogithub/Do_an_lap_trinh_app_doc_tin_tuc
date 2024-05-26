package com.example.projectreaderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.projectreaderapp.adapter.NewsAdapter;
import com.example.projectreaderapp.adapter.ViewPagerAdapter;
import com.example.projectreaderapp.model.News;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import fragment.NewsFragment;
import tablayout.MoiFM;
import tablayout.NongFM;
import tablayout.TablayoutViewPagerAdapter;
import tablayout.TonghopFM;

public class MainActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    public static BottomNavigationView bottomNavigationView;
    public static SearchView searchView;

    float x1,x2,y1,y2;
    private Toolbar toolbar;
    ImageButton imageButton;
    Context context;

    ImageView imgbtnlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();

        //Tạo nút bấm Category
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.getMenu().findItem(R.id.ic_news).setChecked(true);
                viewPager.setCurrentItem(0);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void addControl(){
        toolbar=findViewById(R.id.myToolBar);
        imageButton=findViewById(R.id.btnOpenCategory);
        viewPager=findViewById(R.id.view_pager);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        imgbtnlogo=findViewById(R.id.imgbtnlogo);

        //Nút logo
        imgbtnlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(1);
                NewsFragment.viewPager.setCurrentItem(0);
            }
        });

        //Dùng ViewPagerAdapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //Khi chọn vào icon nào thì icon đó sẽ đổi màu
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        searchView.setVisibility(View.GONE);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.ic_news).setChecked(true);
                        searchView.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R .id.ic_trending).setChecked(true);
                        searchView.setVisibility(View.GONE);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.ic_utilities).setChecked(true);
                        searchView.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Dùng sự kiện của bottom NavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.ic_news){
                    viewPager.setCurrentItem(1);
                    searchView.setVisibility(View.VISIBLE);
                    if(!searchView.isIconified()){
                        searchView.setIconified(true);
                    }
                }
                if(item.getItemId()==R.id.ic_trending){
                    viewPager.setCurrentItem(2);
                    searchView.setVisibility(View.GONE);
                    if(!searchView.isIconified()){
                        searchView.setIconified(true);
                    }
                }
                if(item.getItemId()==R.id.ic_utilities){
                    viewPager.setCurrentItem(3);
                    searchView.setVisibility(View.GONE);
                    if(!searchView.isIconified()){
                        searchView.setIconified(true);
                    }
                }
                return true;
            }
        });
    }


    //Tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.ic_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFragment.tabLayout.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
                NewsFragment.viewPager.setCurrentItem(0);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        TonghopFM.newsAdapter.getFilter().filter(query);
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        TonghopFM.newsAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                bottomNavigationView.setVisibility(View.VISIBLE);
                NewsFragment.tabLayout.setVisibility(View.VISIBLE);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            NewsFragment.tabLayout.setVisibility(View.VISIBLE);
        }
        closeApp();
    }

    //Dùng item menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.ic_search){
            bottomNavigationView.setVisibility(View.GONE);
        }
        if(item.getItemId()== R.id.ic_user){
            if(!new PrefManager(MainActivity.this).getUsername().equals("")){
                Intent i = new Intent(MainActivity.this, UserSetting.class);
                startActivity(i);
            }else{
                Intent i = new Intent(MainActivity.this, User.class);
                startActivity(i);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public String getUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", "");
    }

    //Tạo dialog close app
    public void closeApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Đóng ứng dụng");
        builder.setMessage("Bạn chắc chắn muốn đóng ứng dụng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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