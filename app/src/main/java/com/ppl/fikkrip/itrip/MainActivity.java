package com.ppl.fikkrip.itrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");


        TextView tvNama = (TextView) findViewById(R.id.nama);
        TextView tvUsername = (TextView) findViewById(R.id.username);
        TextView tvEmail = (TextView) findViewById(R.id.email);

        // Display user details
        String message = nama + " welcome to MainActivity";
        tvNama.setText(message);
        tvUsername.setText(username);
        tvEmail.setText(email);
    }
}