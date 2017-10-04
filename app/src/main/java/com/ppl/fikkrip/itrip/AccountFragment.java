package com.ppl.fikkrip.itrip;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

        EditText etNama = (EditText) view.findViewById(R.id.nama);
        EditText etUsername = (EditText) view.findViewById(R.id.username);
        EditText etEmail = (EditText) view.findViewById(R.id.email);
        Button btnLogout = (Button) view.findViewById(R.id.buttonLogout);

        // Display user details
        etNama.setText(nama);
        etNama.setEnabled(false);
        etUsername.setText(username);
        etUsername.setEnabled(false);
        etEmail.setText(email);
        etEmail.setEnabled(false);

        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Logout")
                        .setMessage("Ingin Keluar Dari Aplikasi Ini?")
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getActivity(), "Kamu Memilih YES", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                                session.logoutUser();
                                getActivity().finish();
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
        });

        return view;
    }
}