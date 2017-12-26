package com.ppl.fikkrip.itrip.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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

/**
 * Created by Pinky Cindy on 12/06/17.
 */

public class SearchFragment  extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView lvhape;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ArrayList<DataWisata> mArrayList;
    private ListSearchAdapter mAdapter;
    SearchView search_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_trip, container, false);

        search_view = (SearchView) view.findViewById(R.id.search_view);
        lvhape = (RecyclerView) view.findViewById(R.id.lvhape);
        lvhape.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        lvhape.setLayoutManager(llm);

        loadJSON();
        search_view.setOnQueryTextListener(this);

        return view;
    }

    private void loadJSON(){
        String url = getString(R.string.api)+"getAllWisata.php";
        requestQueue = Volley.newRequestQueue(getContext());
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
                        save.setIdProvinsi(json.getInt("idProvinsi"));
                        save.setNama(json.getString("namaWisata"));
                        save.setDeskripsi(json.getString("deskripsiWisata"));
                        save.setKategori(json.getString("kategori"));
                        save.setBiayaMasuk(json.getInt("biayaMasuk"));
                        save.setLokasi(json.getString("lokasiWisata"));
                        save.setGambar(json.getString("gambarWisata"));
                        mArrayList.add(a, save);
                        mAdapter = new ListSearchAdapter(getContext(), mArrayList);
                        lvhape.setAdapter(mAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);//memfilter adapter pada textbaru
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false; //submit query
    }

}
