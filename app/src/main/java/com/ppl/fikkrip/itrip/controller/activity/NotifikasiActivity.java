package com.ppl.fikkrip.itrip.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.model.PlanningModel;
import com.ppl.fikkrip.itrip.rest.PlanningRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pinky Cindy on 10/29/17.
 */

public class NotifikasiActivity extends AppCompatActivity {

    String id;
    private JSONArray result;
    private ArrayList<PlanningModel> listPlanning = null;
    TextView tjudul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifikasi_activity);
        id = getIntent().getStringExtra("idPlanning");
        PostIDPlanning(id);

    }

    private void PostIDPlanning(String id) {
        String url = NotifikasiActivity.this.getString(R.string.api) + "getPlanning.php";
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);

                    //Storing the Array of JSON String to our JSON Array
                    result = j.getJSONArray("listGambar");

                    getPlanning(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        PlanningRequest planningRequest = new PlanningRequest(id, url, responseListener2);
        RequestQueue queue = Volley.newRequestQueue(NotifikasiActivity.this);
        queue.add(planningRequest);
    }

    private void getPlanning(JSONArray j) {

        listPlanning = new ArrayList<>();
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                    PlanningModel planning = new PlanningModel();
                    planning.setIdPlanning(json.getInt("idPlanning"));
                    planning.setIdUser(json.getInt("idUser"));
                    planning.setJudul(json.getString("judul"));
                    planning.setStatus(json.getString("status"));
                    planning.setNote(json.getString("note"));
                    listPlanning.add(planning);
            }  catch (JSONException e) {
                Toast.makeText(getBaseContext(), "Data tidak Ada", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}