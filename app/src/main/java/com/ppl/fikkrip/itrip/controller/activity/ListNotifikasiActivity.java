package com.ppl.fikkrip.itrip.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.adapter.ListNotifikasiAdapter;
import com.ppl.fikkrip.itrip.model.PlanningModel;
import com.ppl.fikkrip.itrip.rest.NotifikasiRequest;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListNotifikasiActivity extends AppCompatActivity {

    private RecyclerView lvhape;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ArrayList<PlanningModel> mArrayList;
    private ListNotifikasiAdapter adapter;
    private JSONArray result;
    SessionManager session;
    String idUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notifikasi);

        ImageButton btnBack = (ImageButton) findViewById(R.id.toolbar_back);
        mArrayList = new ArrayList<PlanningModel>();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListNotifikasiActivity.this, MainActivity.class));
                finish();
            }
        });
        initViews();

        session = new SessionManager(this);
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        idUser = user.get(SessionManager.KEY_ID);

        loadJSON(idUser);
    }

    private void initViews(){
        lvhape = (RecyclerView) findViewById(R.id.lvhape);
        lvhape.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        lvhape.setLayoutManager(llm);
    }
    private void loadJSON(String idUser){
        String url = getString(R.string.api) + "getListNotifikasi.php";
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);

                    //Storing the Array of JSON String to our JSON Array
                    result = j.getJSONArray("getListNotifikasi");

                    //Calling method getStudents to get the students from the JSON Array
                    getListNotifikasi(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        NotifikasiRequest notifikasiRequest = new NotifikasiRequest(idUser, url, responseListener2);
        RequestQueue queue = Volley.newRequestQueue(ListNotifikasiActivity.this);
        queue.add(notifikasiRequest);
    }
    private void getListNotifikasi(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                PlanningModel save = new PlanningModel();
                save.setIdPlanning(json.getInt("idPlanning"));
                save.setIdUser(json.getInt("idUser"));
                save.setJudul(json.getString("judul"));
                save.setStatus(json.getString("status"));
                save.setTglPlanning(json.getString("tglPlanning"));
                save.setNote(json.getString("note"));
                mArrayList.add(i, save);
                adapter = new ListNotifikasiAdapter(ListNotifikasiActivity.this, mArrayList);
                lvhape.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
