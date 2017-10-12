package com.ppl.fikkrip.itrip;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NatureActivity extends AppCompatActivity{

    private Spinner spinner;
    private String idSumatera, idKalimantan, idJawa, idSulawesi, idPapua, idPulau;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ArrayList<String> provinsiList;
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

        Toolbar ToolBarAtas = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);
        idSumatera = getIntent().getStringExtra("idSumatera");
        idKalimantan = getIntent().getStringExtra("idKalimantan");
        idJawa = getIntent().getStringExtra("idJawa");
        idSulawesi = getIntent().getStringExtra("idSulawesi");
        idPapua = getIntent().getStringExtra("idPapua");

        provinsiList = new ArrayList<String>();

        if(idSumatera!=null){
            idPulau = idSumatera;
        }else if(idKalimantan!=null){
            idPulau = idKalimantan;
        }else if(idJawa!=null){
            idPulau = idJawa;
        }else if(idSulawesi!=null){
            idPulau = idSulawesi;
        }else if(idPapua!=null){
            idPulau = idPapua;
        }

        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        spinner.setOnItemSelectedListener(new ItemSelectedListener());
        getData(idPulau);
    }

    private void getData(String idPulau){
        String url = getString(R.string.api)+"getProvinsi.php";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
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
        NatureRequest natureRequest = new NatureRequest(idPulau, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(NatureActivity.this);
        queue.add(natureRequest);
    }

    private void getProvinsi(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
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
        spinner.setAdapter(new ArrayAdapter<String>(NatureActivity.this, android.R.layout.simple_spinner_dropdown_item, provinsiList));
    }



    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                Toast.makeText(parent.getContext(),
                        "Kamu telah memilih : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
                // Todo when item is selected by the user
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }
}
