package com.ppl.fikkrip.itrip.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.adapter.ListNotifikasiAdapter;
import com.ppl.fikkrip.itrip.model.PlanningModel;
import com.ppl.fikkrip.itrip.rest.HistoryRequest;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pinky Cindy on 10/27/17.
 */

public class HistoryFragment extends Fragment {

    private RecyclerView listHistory;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private JSONArray result;
    private String id;
    SessionManager session;
    ArrayList<HashMap<String, String>> list_data;
    public static ArrayList<PlanningModel> list;
    public HistoryFragment() {
        list_data = new ArrayList<HashMap<String, String>>();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        listHistory = (RecyclerView) view.findViewById(R.id.listHistory);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listHistory.setLayoutManager(llm);

        list = new ArrayList<PlanningModel>();

        session = new SessionManager(getContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        String id = user.get(SessionManager.KEY_ID);
       PostData(id);

        return view;
    }
    private void PostData(String idUser) {
        String url = getString(R.string.api) + "listPlanning.php";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("listplanning");
                    getWisata(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        HistoryRequest dataRequest = new HistoryRequest(idUser, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(dataRequest);
    }


    private void getWisata(JSONArray j) {

        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                PlanningModel dataPlanning = new PlanningModel();
                dataPlanning.setIdPlanning(json.getInt("idPlanning"));
                dataPlanning.setJudul(json.getString("judul"));
                dataPlanning.setStatus(json.getString("status"));
                dataPlanning.setTglPlanning(json.getString("tglPlanning"));
                dataPlanning.setNote(json.getString("note"));
                dataPlanning.setIdUser(json.getInt("idUser"));
                list.add(dataPlanning);
                ListNotifikasiAdapter adapter = new ListNotifikasiAdapter(getContext(), list);
                listHistory.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}