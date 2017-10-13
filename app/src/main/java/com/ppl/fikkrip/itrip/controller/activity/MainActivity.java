package com.ppl.fikkrip.itrip.controller.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.fragment.AccountFragment;
import com.ppl.fikkrip.itrip.controller.fragment.ExploreFragment;
import com.ppl.fikkrip.itrip.controller.fragment.MyTripFragment;
import com.ppl.fikkrip.itrip.controller.fragment.NearMeFragment;

public class MainActivity extends AppCompatActivity{
    private VPMainAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menerapkan TabLayout dan ViewPager pada Activity
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_main);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);

        //Memanggil dan Memasukan Value pada Class PagerAdapter(FragmentManager dan JumlahTab)
        fragmentPagerAdapter = new VPMainAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Memasang Adapter pada ViewPager
        viewPager.setAdapter(fragmentPagerAdapter);

        /*
         Menambahkan Listener yang akan dipanggil kapan pun halaman berubah atau
         bergulir secara bertahap, sehingga posisi tab tetap singkron
         */
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Callback Interface dipanggil saat status pilihan tab berubah.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Dipanggil ketika tab memasuki state/keadaan yang dipilih.
                viewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.parseColor("#2B9FDC"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                //Dipanggil saat tab keluar dari keadaan yang dipilih.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#2B9FDC"), PorterDuff.Mode.SRC_IN);
                //Dipanggil ketika tab yang sudah dipilih, dipilih lagi oleh user.
            }
        });
    }

    private class VPMainAdapter extends FragmentPagerAdapter {

        private int number_tabs;

        public VPMainAdapter(FragmentManager fm, int number_tabs) {
            super(fm);
            this.number_tabs = number_tabs;
        }

        //Mengembalikan Fragment yang terkait dengan posisi tertentu
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ExploreFragment();
                case 1:
                    return new NearMeFragment();
                case 2:
                    return new MyTripFragment();
                case 3:
                    return new AccountFragment();
                default:
                    return null;
            }
        }

        //Mengembalikan jumlah tampilan yang tersedia.
        @Override
        public int getCount() {
            return number_tabs;
        }
    }
}