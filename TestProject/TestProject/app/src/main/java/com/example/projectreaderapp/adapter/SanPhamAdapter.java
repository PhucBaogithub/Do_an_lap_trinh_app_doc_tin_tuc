package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectreaderapp.R;
import com.example.projectreaderapp.model.SanPham;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    Activity context;
    int resource;

    public SanPhamAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View customView=layoutInflater.inflate(this.resource,null);
        ImageView ivCoQuocGia=customView.findViewById(R.id.ivCoQuocGia);
        TextView gvQuocGia=customView.findViewById(R.id.gvQuocGia);
        TextView txtGiamua=customView.findViewById(R.id.txtGiamua);
        TextView txtGiaban=customView.findViewById(R.id.txtGiaban);
        SanPham sp= getItem(position);
        ivCoQuocGia.setImageResource(sp.getHinh());
        txtGiamua.setText(sp.getDongia()+ "");
        gvQuocGia.setText(sp.getTensp());
        txtGiaban.setText(sp.getSoluong()+"");
        return customView;
    }
}
