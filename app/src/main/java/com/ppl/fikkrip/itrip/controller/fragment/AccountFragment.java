package com.ppl.fikkrip.itrip.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

import java.util.HashMap;

public class AccountFragment extends Fragment {

    SessionManager session;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        session = new SessionManager(getContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        String nama = user.get(SessionManager.KEY_NAMA);
        String username = user.get(SessionManager.KEY_USERNAME);
        String email = user.get(SessionManager.KEY_EMAIL);

        TextView etNama = (TextView) view.findViewById(R.id.nama);
        TextView etUsername = (TextView) view.findViewById(R.id.username);
        TextView etEmail = (TextView) view.findViewById(R.id.email);

        // Display user details
        etNama.setText(nama);
        etNama.setEnabled(false);
        etUsername.setText(username);
        etUsername.setEnabled(false);
        etEmail.setText(email);
        etEmail.setEnabled(false);

        return view;
    }
}