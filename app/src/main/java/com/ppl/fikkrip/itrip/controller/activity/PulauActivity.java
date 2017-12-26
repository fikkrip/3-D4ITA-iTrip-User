package com.ppl.fikkrip.itrip.controller.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ppl.fikkrip.itrip.R;

/**
 * Created by Pinky Cindy on 10/23/17.
 */

public class PulauActivity extends AppCompatActivity {

    private Button bNature, bModern, bCulture, bCulinary, bEvent;
    private String idPulau;
    private TextView txtTittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idPulau = getIntent().getStringExtra("idPulau");
        setContentView(R.layout.activity_pulau);
        Toolbar ToolBarAtas = (Toolbar) findViewById(R.id.toolbar);
        bNature = (Button) findViewById(R.id.nature);
        bModern = (Button) findViewById(R.id.modern);
        bCulture = (Button) findViewById(R.id.culture);
        bCulinary = (Button) findViewById(R.id.culinary);
        bEvent = (Button) findViewById(R.id.event);
        txtTittle  = (TextView) findViewById(R.id.toolbar_title);

        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        if(idPulau.equals("idSumatera")){
            txtTittle.setText("SUMATERA");
        }
        else if(idPulau.equals("idKalimantan")){
            txtTittle.setText("KALIMANTAN");
        }
        else if(idPulau.equals("idSulawesi")){
            txtTittle.setText("SULAWESI");
        }
        else if(idPulau.equals("idJawa")){
            txtTittle.setText("JAWA");
        }
        else if(idPulau.equals("idPapua")){
            txtTittle.setText("PAPUA");
        }

        bNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PulauActivity.this, WisataActivity.class);
                String idP = idPulau;
                String idKategori = "alam";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idPulau", idP);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left2);

            }
        });

        bModern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PulauActivity.this, WisataActivity.class);
                String idP = idPulau;
                String idKategori = "modern";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idPulau", idP);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left2);
            }
        });

        bCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PulauActivity.this, WisataActivity.class);
                String idP = idPulau;
                String idKategori = "budaya";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idPulau", idP);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left2);
            }
        });

        bCulinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PulauActivity.this, WisataActivity.class);
                String idP = idPulau;
                String idKategori = "kuliner";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idPulau", idP);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left2);
            }
        });

        bEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PulauActivity.this, EventActivity.class);
                String idP = idPulau;
                intent.putExtra("idPulau", idP);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left2);
            }
        });
    }
}
