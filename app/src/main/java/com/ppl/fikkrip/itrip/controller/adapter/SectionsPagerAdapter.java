package com.ppl.fikkrip.itrip.controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ppl.fikkrip.itrip.controller.fragment.HistoryFragment;
import com.ppl.fikkrip.itrip.controller.fragment.PlanningFragment;

/**
 * Created by Pinky Cindy on 10/27/17.
 */

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    //deklarasi variabel untuk menampung jumlah tab menu yang ada
    int tabCount;

    //konstruktor
    public SectionsPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //menginisialisasi tabcount
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PlanningFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new HistoryFragment();
            default:
                return null;
        }
    }

    //Overriden method getCount untuk mengambil jumlah dari tab menu
    @Override
    public int getCount() {
        return tabCount;
    }

}