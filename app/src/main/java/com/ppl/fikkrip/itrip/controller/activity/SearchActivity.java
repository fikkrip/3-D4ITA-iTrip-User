package com.ppl.fikkrip.itrip.controller.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.adapter.ListSearchAdapter;
import com.ppl.fikkrip.itrip.model.DataWisata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView lvhape;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ArrayList<DataWisata> mArrayList;
    private ListSearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        loadJSON();

    }

    private void initViews(){
        lvhape = (RecyclerView) findViewById(R.id.lvhape);
        lvhape.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        lvhape.setLayoutManager(llm);
    }
    private void loadJSON(){
        String url = getString(R.string.api)+"getAllWisata.php";
        requestQueue = Volley.newRequestQueue(this);
        mArrayList = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getAllWisata");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        DataWisata save = new DataWisata();
                        save.setIdWisata(json.getInt("idWisata"));
                        save.setIdProvinsi(0);
                        save.setNama(json.getString("namaWisata"));
                        save.setDeskripsi("xx");
                        save.setKategori("xx");
                        save.setBiayaMasuk(0);
                        save.setLokasi(json.getString("lokasiWisata"));
                        save.setGambar(json.getString("gambarWisata"));
                        mArrayList.add(a, save);
                        mAdapter = new ListSearchAdapter(SearchActivity.this, mArrayList);
                        lvhape.setAdapter(mAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
