package com.ppl.fikkrip.itrip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ExploreFragment extends Fragment {

    ImageButton sumatera, kalimantan, jawa, sulawesi, papua;
    private RecyclerView lvhape;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private Button mButton;
    private PopupWindow mPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        String url = getString(R.string.api)+"ListPopular.php";



        sumatera = (ImageButton) view.findViewById(R.id.imageSumatra);
        kalimantan = (ImageButton) view.findViewById(R.id.imageKalimantan);
        sulawesi = (ImageButton) view.findViewById(R.id.imageSulawesi);
        jawa = (ImageButton) view.findViewById(R.id.imageJawa);
        papua = (ImageButton) view.findViewById(R.id.imagePapua);

        mButton = (Button) view.findViewById(R.id.btn);
        mContext = getContext();
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.Ex);
      mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.detail_popular,null);

                mPopupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT


                );

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();

                    }
                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);

            }
        });
        sumatera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SumateraActivity.class);
                getActivity().startActivity(intent);
            }
        });

        kalimantan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KalimantanActivity.class);
                getActivity().startActivity(intent);
            }
        });
        sulawesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SulawesiActivity.class);
                getActivity().startActivity(intent);
            }
        });
        jawa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JawaActivity.class);
                getActivity().startActivity(intent);
            }
        });
        papua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PapuaActivity.class);
                getActivity().startActivity(intent);
            }
        });

        lvhape = (RecyclerView) view.findViewById(R.id.lvhape);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvhape.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(getActivity());

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("listpopular");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", json.getString("idWisata"));
                        map.put("namaProvinsi", json.getString("namaProvinsi"));
                        map.put("namaWisata", json.getString("namaWisata"));
                        map.put("deskripsiWisata", json.getString("deskripsiWisata"));
                        map.put("kategori", json.getString("kategori"));
                        map.put("lokasiWisata", json.getString("lokasiWisata"));
                        map.put("gambarWisata", json.getString("gambarWisata"));
                        list_data.add(map);
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), list_data);
                        lvhape.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);


        return view;
    }
}