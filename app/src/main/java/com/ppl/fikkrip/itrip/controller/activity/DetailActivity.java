package com.ppl.fikkrip.itrip.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ppl.fikkrip.itrip.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent myIntent = getIntent();
        final Bundle myData = myIntent.getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(myData.getString("namaWisata"));

        TextView tvBiaya = (TextView) findViewById(R.id.tvBiaya);
        final TextView tvLokasi = (TextView) findViewById(R.id.tvLokasi);
        TextView tvDeskripsi = (TextView) findViewById(R.id.tvDeskripsi);
        ImageButton toolbarBack = (ImageButton) findViewById(R.id.toolbar_back);
        FloatingActionButton btns = (FloatingActionButton) findViewById(R.id.fab);
        ImageView imageView = (ImageView) findViewById(R.id.image);

        tvBiaya.setText(Integer.toString(myData.getInt("biayaMasuk")));
        tvLokasi.setText(myData.getString("lokasiWisata"));
        tvDeskripsi.setText(myData.getString("deskripsiWisata"));
        Glide.with(this).load(getString(R.string.img) + myData.getString("gambarWisata")).into(imageView);

        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "I-Trip");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Wisata : "+myData.getString("namaWisata")+" Lokasi : "+myData.getString("lokasiWisata"));
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
