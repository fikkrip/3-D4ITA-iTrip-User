package com.ppl.fikkrip.itrip.controller.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
                Fragment tab1 = new PlanningFragment(); //menginstansiasi objek baru yaitu Fragment1.java
                return tab1;
            case 1:
                Fragment tab2 = new PlanningFragment(); //menginstansiasi objek baru yaitu Fragment2.java
                return tab2;
            case 2:
                Fragment tab3 = new halaman3Fragment(); //menginstansiasi objek baru yaitu Fragment2.java
                return tab3;
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