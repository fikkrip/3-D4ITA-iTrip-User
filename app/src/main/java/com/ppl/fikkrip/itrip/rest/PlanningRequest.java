package com.ppl.fikkrip.itrip.rest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fikkri Prasetya on 10/27/2017.
 */

public class PlanningRequest extends StringRequest {
    private Map<String, String> params;
    public PlanningRequest(String idPlanning, String url, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idPlanning", idPlanning);
    }

    public PlanningRequest(String idPlanning, String idUser, String judul, String tanggal, String url, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idPlanning", idPlanning);
        params.put("idUser", idUser);
        params.put("judul", judul);
        params.put("tglPlanning", tanggal);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
