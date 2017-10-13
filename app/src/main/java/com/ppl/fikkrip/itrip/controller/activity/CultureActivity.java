package com.ppl.fikkrip.itrip.controller.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.controller.adapter.ListWisataAdapter;
import com.ppl.fikkrip.itrip.rest.ProvinsiRequest;
import com.ppl.fikkrip.itrip.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CultureActivity extends AppCompatActivity {

    private ListWisataAdapter adapter;
    private Spinner spinner;
    private String idSumatera, idKalimantan, idJawa, idSulawesi, idPapua, idPulau, idKategori;
    private ArrayList<String> provinsiList;
    private JSONArray result;
    private RecyclerView lvhape;
    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture);

        list_data = new ArrayList<HashMap<String, String>>();
        Toolbar ToolBarAtas = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);
        idSumatera = getIntent().getStringExtra("idSumatera");
        idKalimantan = getIntent().getStringExtra("idKalimantan");
        idJawa = getIntent().getStringExtra("idJawa");
        idSulawesi = getIntent().getStringExtra("idSulawesi");
        idPapua = getIntent().getStringExtra("idPapua");
        idKategori = getIntent().getStringExtra("idKategori");

        provinsiList = new ArrayList<String>();

        if (idSumatera != null) {
            idPulau = idSumatera;
        } else if (idKalimantan != null) {
            idPulau = idKalimantan;
        } else if (idJawa != null) {
            idPulau = idJawa;
        } else if (idSulawesi != null) {
            idPulau = idSulawesi;
        } else if (idPapua != null) {
            idPulau = idPapua;
        }

        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        spinner.setOnItemSelectedListener(new ItemSelectedListener());
        getData(idPulau);

        lvhape = (RecyclerView) findViewById(R.id.listwisata);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvhape.setLayoutManager(llm);
    }

    private void getData(String idPulau) {
        String url = getString(R.string.api) + "getProvinsi.php";
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);

                    //Storing the Array of JSON String to our JSON Array
                    result = j.getJSONArray("getProvinsi");

                    //Calling method getStudents to get the students from the JSON Array
                    getProvinsi(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ProvinsiRequest provinsiRequest = new ProvinsiRequest(idPulau, url, responseListener2);
        RequestQueue queue = Volley.newRequestQueue(CultureActivity.this);
        queue.add(provinsiRequest);
    }

    private void getProvinsi(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                provinsiList.add(json.getString("namaProvinsi"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(CultureActivity.this, android.R.layout.simple_spinner_dropdown_item, provinsiList));
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String pilih = null;
            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                pilih = parent.getItemAtPosition(pos).toString();
                list_data.clear();
                getDataWisata(pilih, idKategori);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }

    private void getDataWisata(String idProvinsi, String idKategori) {
        String url = getString(R.string.api) + "getDataWisata.php";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("getWisata");
                    getWisata(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter.notifyDataSetChanged();
                }
            }
        };
        ProvinsiRequest provinsiRequest = new ProvinsiRequest(idProvinsi, idKategori, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CultureActivity.this);
        queue.add(provinsiRequest);
    }

    private void getWisata(JSONArray j) {

        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("namaWisata", json.getString("namaWisata"));
                map.put("lokasiWisata", json.getString("lokasiWisata"));
                map.put("biayaMasuk", json.getString("biayaMasuk"));
                map.put("deskripsiWisata", json.getString("deskripsiWisata"));
                map.put("namaProvinsi", json.getString("namaProvinsi"));
                map.put("gambarWisata", json.getString("gambarWisata"));
                list_data.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (adapter == null ) {
            adapter = new ListWisataAdapter(this, list_data);
            lvhape.setAdapter(adapter);
        }else
            adapter.notifyDataSetChanged();
    }
}
