package com.ppl.fikkrip.itrip.rest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fikkri Prasetya on 10/12/2017.
 */

public class ProvinsiRequest extends StringRequest {
    private Map<String, String> params;
    public ProvinsiRequest(String idPulau, String url, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idPulau", idPulau);
    }

    public ProvinsiRequest(String idProvinsi, String idKategori, String idUser, String url, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idProvinsi", idProvinsi);
        params.put("idKategori", idKategori);
        params.put("idUser", idUser);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}