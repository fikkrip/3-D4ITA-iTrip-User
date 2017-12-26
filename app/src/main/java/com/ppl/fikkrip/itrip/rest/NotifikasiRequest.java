package com.ppl.fikkrip.itrip.rest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pinky Cindy on 11/21/17.
 */

public class NotifikasiRequest  extends StringRequest {
    private Map<String, String> params;
    public NotifikasiRequest(String idUser, String url, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("idUser", idUser);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
