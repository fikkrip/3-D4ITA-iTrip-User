package com.ppl.fikkrip.itrip.controller.activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.adapter.ListWisataAdapter;
import com.ppl.fikkrip.itrip.model.FavoritModel;
import com.ppl.fikkrip.itrip.rest.ProvinsiRequest;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pinky Cindy on 10/25/17.
 */

public class WisataActivity extends AppCompatActivity {

    private ListWisataAdapter adapter;
    private Spinner spinner;
    private String idPulau, idKategori;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ArrayList<String> provinsiList;
    private JSONArray result;
    private RecyclerView lvhape;
    private ArrayList<FavoritModel> list_data = null;
    private TextView txtTittle;
    private ImageButton btnBack;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);

        list_data  = new ArrayList<>();
        Toolbar ToolBarAtas = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);
        idPulau = getIntent().getStringExtra("idPulau");
        idKategori = getIntent().getStringExtra("idKategori");
        txtTittle  = (TextView) findViewById(R.id.toolbar_title);
        btnBack = (ImageButton) findViewById(R.id.toolbar_back);
        provinsiList = new ArrayList<String>();

        if(idKategori.equals("alam")){
            txtTittle.setText("NATURE");
        }
        else if(idKategori.equals("modern")){
            txtTittle.setText("MODERN");
        }
        else if(idKategori.equals("budaya")){
            txtTittle.setText("CULTURE");
        }
        else if(idKategori.equals("kuliner")){
            txtTittle.setText("CULINARY");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.left_to_right2);
            }
        });
        spinner.setOnItemSelectedListener(new ItemSelectedListener());
        getData(idPulau);

        lvhape = (RecyclerView) findViewById(R.id.listwisata);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        lvhape.setLayoutManager(mLayoutManager);
        lvhape.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        lvhape.setItemAnimator(new DefaultItemAnimator());
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
        RequestQueue queue = Volley.newRequestQueue(WisataActivity.this);
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
        spinner.setAdapter(new ArrayAdapter<String>(WisataActivity.this, android.R.layout.simple_spinner_dropdown_item, provinsiList));
    }


    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String pilih = null;
            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                list_data.clear();
                pilih = parent.getItemAtPosition(pos).toString();
                getDataWisata(pilih, idKategori);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }

    private void getDataWisata(String idProvinsi, String idKategori) {
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        String idUser = user.get(SessionManager.KEY_ID);

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

        System.out.println(idProvinsi);
        ProvinsiRequest provinsiRequest = new ProvinsiRequest(idProvinsi, idKategori, idUser, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(WisataActivity.this);
        queue.add(provinsiRequest);
    }

    private void getWisata(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = result.getJSONObject(i);
                FavoritModel favoritModel = new FavoritModel();
                favoritModel.setIdWisata(json.getInt("idWisata"));
                favoritModel.setIdProvinsi(json.getInt("idProvinsi"));
                favoritModel.setNamaProvinsi(json.getString("namaProvinsi"));
                favoritModel.setNamaWisata(json.getString("namaWisata"));
                favoritModel.setDeskripsiWisata(json.getString("deskripsiWisata"));
                favoritModel.setKategori(json.getString("kategori"));
                favoritModel.setBiayaMasuk(json.getInt("biayaMasuk"));
                favoritModel.setLongtitude(json.getString("longtitude"));
                favoritModel.setLatitude(json.getString("latitude"));
                favoritModel.setLokasi(json.getString("lokasiWisata"));
                favoritModel.setGambar(json.getString("gambarWisata"));
                favoritModel.setStatus(json.getString("status"));
                favoritModel.setIdUser(json.getString("idUser"));
                list_data.add(favoritModel);
                adapter = new ListWisataAdapter(this,list_data);
                lvhape.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (adapter == null ) {
            adapter = new ListWisataAdapter(this, list_data);
            lvhape.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
