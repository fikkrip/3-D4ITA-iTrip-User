package com.ppl.fikkrip.itrip.rest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fikkri Prasetya on 10/12/2017.
 */

public class FavoritRequest extends StringRequest {
    private Map<String, String> params;

    public FavoritRequest(String idUser, String url, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idUser", idUser);
    }
    public FavoritRequest(String idWisata, String idUser, String url, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);

        params = new HashMap<>();
        params.put("idWisata", idWisata);
        params.put("idUser", idUser);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}