package fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.projectreaderapp.Giavang;
import com.example.projectreaderapp.Login;
import com.example.projectreaderapp.MainActivity;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.TranslateAnimationUtil;
import com.example.projectreaderapp.Weather;
import com.example.projectreaderapp.Xoso;

public class UtilitiesFragment extends Fragment {

    ImageView btnGiavang, btnThoitiet, btnXoso;
    ScrollView srollViewutilities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_utilities, container, false);
        btnGiavang=view.findViewById(R.id.btnGiavang);
        btnThoitiet=view.findViewById(R.id.btnThoitiet);
        srollViewutilities=view.findViewById(R.id.srollViewutilities);
        btnXoso=view.findViewById(R.id.btnXoso);

        srollViewutilities.setOnTouchListener(new TranslateAnimationUtil(getContext(), MainActivity.bottomNavigationView));

        btnGiavang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Giavang.class);
                startActivity(i);
            }
        });

        btnThoitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Weather.class);
                startActivity(i);
            }
        });

        btnXoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Xoso.class);
                startActivity(i);
            }
        });

        return view;
    }
}