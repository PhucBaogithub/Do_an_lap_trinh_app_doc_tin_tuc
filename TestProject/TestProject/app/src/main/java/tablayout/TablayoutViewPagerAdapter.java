package tablayout;

import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TablayoutViewPagerAdapter extends FragmentStatePagerAdapter {
    public TablayoutViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TonghopFM();
            case 1:
                return new NongFM();
            case 2:
                return new MoiFM();
            case 3:
                return new SuckhoeFM();
            case 4:
                return new GiaitriFM();
            case 5:
                return new TinhyeuFM();
            case 6:
                return new ThegioiFM();
            case 7:
                return new XeFM();
            case 8:
                return new CongngheFM();
            default:
                return new TonghopFM();
        }
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Tổng hợp";
            case 1:
                return "Nóng";
            case 2:
                return "Mới";
            case 3:
                return "Sức khỏe";
            case 4:
                return "Giải trí";
            case 5:
                return "Tình yêu";
            case 6:
                return "Thế giới";
            case 7:
                return "Xe";
            case 8:
                return "Công nghệ";
            default:
                return "Tổng hợp";
        }
    }
}
