package com.ppl.fikkrip.itrip.rest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fikkri Prasetya on 10/27/2017.
 */

public class GambarRequest extends StringRequest {
    private Map<String, String> params;
    public GambarRequest(String idWisata, String url, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idWisata", idWisata);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
