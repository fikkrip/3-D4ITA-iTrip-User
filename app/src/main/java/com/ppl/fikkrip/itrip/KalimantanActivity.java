package com.ppl.fikkrip.itrip;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

public class KalimantanActivity extends AppCompatActivity {

    SliderLayout sliderLayout;
    private Button bNature, bModern, bCulture, bCulinary, bEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalimantan);

        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        Toolbar ToolBarAtas = (Toolbar)findViewById(R.id.toolbar);
        bNature = (Button) findViewById(R.id.nature);
        bModern = (Button) findViewById(R.id.modern);
        bCulture = (Button) findViewById(R.id.culture);
        bCulinary = (Button) findViewById(R.id.culinary);
        bEvent = (Button) findViewById(R.id.event);

        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        HashMap<String,Integer> file_maps = new HashMap<>();
        file_maps.put("Pantai Kijing",R.drawable.pantaikijing);
        file_maps.put("Soto Banjar, Makanan Khas Kalimantan",R.drawable.sotobanjar);
        file_maps.put("Dayak, Suku Asli Kalimantan",R.drawable.sukudayak);
        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);

        bNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalimantanActivity.this, NatureActivity.class);
                String idKalimantan = "idKalimantan";
                String idKategori = "alam";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idKalimantan", idKalimantan);
                startActivity(intent);
            }
        });

        bModern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalimantanActivity.this, ModernActivity.class);
                String idKalimantan = "idKalimantan";
                String idKategori = "modern";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idKalimantan", idKalimantan);
                startActivity(intent);
            }
        });

        bCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalimantanActivity.this, CultureActivity.class);
                String idKalimantan = "idKalimantan";
                String idKategori = "budaya";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idKalimantan", idKalimantan);
                startActivity(intent);
            }
        });

        bCulinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalimantanActivity.this, CulinaryActivity.class);
                String idKalimantan = "idKalimantan";
                String idKategori = "kuliner";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idKalimantan", idKalimantan);
                startActivity(intent);
            }
        });

        bEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalimantanActivity.this, EventActivity.class);
                String idKalimantan = "idKalimantan";
                String idKategori = "event";
                intent.putExtra("idKategori",idKategori);
                intent.putExtra("idKalimantan", idKalimantan);
                startActivity(intent);
            }
        });
    }
}