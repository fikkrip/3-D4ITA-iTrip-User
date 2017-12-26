package com.ppl.fikkrip.itrip.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.adapter.ListEventAdapter;
import com.ppl.fikkrip.itrip.model.EventModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventActivity extends AppCompatActivity {

    private RecyclerView lvhape;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ArrayList<EventModel> mArrayList;
    private ListEventAdapter mAdapter;
    private String sendIdPulau;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        btnBack = (ImageButton) findViewById(R.id.toolbar_back);

        sendIdPulau = getIntent().getStringExtra("idPulau");

        initViews();
        loadJSON();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.left_to_right2);
            }
        });
    }

    private void initViews() {
        lvhape = (RecyclerView) findViewById(R.id.lvhape);
        lvhape.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        lvhape.setLayoutManager(llm);
    }

    private void loadJSON() {
        String url = getString(R.string.api) + "getEvent.php";
        requestQueue = Volley.newRequestQueue(this);
        mArrayList = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getEvent");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        EventModel save = new EventModel();
                        save.setIdEvent(json.getString("idEvent"));
                        save.setIdProvinsi(json.getString("idProvinsi"));
                        save.setNamaEvent(json.getString("namaEvent"));
                        save.setTglEvent(json.getString("tglEvent"));
                        save.setDeskripsiEvent(json.getString("deskripsiEvent"));
                        save.setLokasiEvent(json.getString("lokasiEvent"));
                        mArrayList.add(a, save);
                        mAdapter = new ListEventAdapter(EventActivity.this, mArrayList);
                        lvhape.setAdapter(mAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EventActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idPulau", sendIdPulau);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}