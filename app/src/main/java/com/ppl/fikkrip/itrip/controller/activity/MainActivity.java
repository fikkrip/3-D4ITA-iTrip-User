package com.ppl.fikkrip.itrip.controller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.adapter.CustomViewPager;
import com.ppl.fikkrip.itrip.controller.fragment.AccountFragment;
import com.ppl.fikkrip.itrip.controller.fragment.ExploreFragment;
import com.ppl.fikkrip.itrip.controller.fragment.FavoritFragment;
import com.ppl.fikkrip.itrip.controller.fragment.NearMeFragment;
import com.ppl.fikkrip.itrip.controller.fragment.SearchFragment;
import com.ppl.fikkrip.itrip.sharedpreference.PrefManager;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class MainActivity extends AppCompatActivity {
    private VPMainAdapter fragmentPagerAdapter;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(MainActivity.this);
        session.checkLogin();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Menerapkan TabLayout dan ViewPager pada Activity
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_main);
        final CustomViewPager cViewPager = (CustomViewPager) findViewById(R.id.vp_main);
        cViewPager.setPagingEnabled(false);

        //Memanggil dan Memasukan Value pada Class PagerAdapter(FragmentManager dan JumlahTab)
        fragmentPagerAdapter = new VPMainAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        KeyboardVisibilityEvent.setEventListener(this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        tabLayout.setVisibility(isOpen ? View.GONE : View.VISIBLE);
                    }
                });

        //Memasang Adapter pada ViewPager
        cViewPager.setAdapter(fragmentPagerAdapter);
        /*
         Menambahkan Listener yang akan dipanggil kapan pun halaman berubah atau
         bergulir secara bertahap, sehingga posisi tab tetap singkron
         */
        cViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Callback Interface dipanggil saat status pilihan tab berubah.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Dipanggil ketika tab memasuki state/keadaan yang dipilih.
                cViewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                //Dipanggil saat tab keluar dari keadaan yang dipilih.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
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
                    return new SearchFragment();
                case 2:
                    return new FavoritFragment();
                case 3:
                    return new NearMeFragment();
                case 4:
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

    public void Logout(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Logout")
                .setMessage("Ingin Keluar Dari Aplikasi Ini?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "Kamu Memilih YES", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                        session.logoutUser();
                        finish();
                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "Kamu Memilih TIDAK Ingin Keluar", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                }).show();
    }

    public void Help(){
        PrefManager prefManager = new PrefManager(MainActivity.this);

        prefManager.setFirstTimeLaunch(true);

        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buttonHelp:
                Help();
                return true;
            case R.id.buttonLogout:
                Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}