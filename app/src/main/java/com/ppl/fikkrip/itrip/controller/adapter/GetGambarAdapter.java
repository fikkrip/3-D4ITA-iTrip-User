package com.ppl.fikkrip.itrip.controller.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Fikkri Prasetya on 10/28/2017.
 */

public class GetGambarAdapter {
    ArrayList<String> gambarList;

    private void getGambar(JSONArray j) {
        ArrayList<String> hasil;
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                gambarList.add(json.getString("namaGambar"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}