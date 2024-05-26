package fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.projectreaderapp.R;
import com.google.android.material.tabs.TabLayout;

import tablayout.TablayoutViewPagerAdapter;
import widget.CustomViewPager;

public class NewsFragment extends Fragment {

    public static TabLayout tabLayout;
    public static CustomViewPager viewPager;
    public View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        tabLayout=mView.findViewById(R.id.tab_layout);
        viewPager=mView.findViewById(R.id.news_viewpager);

        TablayoutViewPagerAdapter adapter = new TablayoutViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

        tabLayout.setupWithViewPager(viewPager);

        return mView;
    }
}