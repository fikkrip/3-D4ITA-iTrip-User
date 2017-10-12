package com.ppl.fikkrip.itrip;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fikkri Prasetya on 9/26/2017.
 */

public class RegisterRequest extends StringRequest {
    private Map<String, String> params;


    public RegisterRequest(String nama, String username, String email, String password, String url, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("nama", nama);
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}