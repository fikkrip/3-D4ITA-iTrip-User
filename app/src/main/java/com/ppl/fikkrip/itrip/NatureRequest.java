package com.ppl.fikkrip.itrip;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pinky Cindy on 10/13/17.
 */

public class NatureRequest extends StringRequest {
    private Map<String, String> params;
    public ProvinsiRequest(String idPulau, String url, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idPulau", idPulau);
    }

    public NatureRequest(String idProvinsi, String idKategori, String url, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idProvinsi", idProvinsi);
        params.put("idKategori", idKategori);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}