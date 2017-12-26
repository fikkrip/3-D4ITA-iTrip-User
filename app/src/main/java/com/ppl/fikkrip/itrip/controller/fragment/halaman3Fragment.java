package com.ppl.fikkrip.itrip.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ppl.fikkrip.itrip.R;

/**
 * Created by Pinky Cindy on 10/27/17.
 */

public class halaman3Fragment extends Fragment {

    public halaman3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.galery_fragment, container, false);
    }

}