package com.ppl.fikkrip.itrip.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Fikkri Prasetya on 21/11/2017.
 */

public class SessionPlanning {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "PlanningPref";

    public static final String KEY_ID = "ID";

    public static final String KEY_NAMA = "nama";

    public static final String KEY_LOKASI = "lokasi";

    // Constructor
    public SessionPlanning(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createPlanningSession(String id, String nama, String lokasi){

        editor.putString(KEY_ID, id);

        editor.putString(KEY_NAMA, nama);

        editor.putString(KEY_LOKASI, lokasi);

        editor.commit();
    }

    public HashMap<String, String> getPlanningDetails(){
        HashMap<String, String> planning = new HashMap<String, String>();

        planning.put(KEY_ID, pref.getString(KEY_ID, null));

        planning.put(KEY_NAMA, pref.getString(KEY_NAMA, null));

        planning.put(KEY_LOKASI, pref.getString(KEY_LOKASI, null));

        return planning;
    }

    public void clearSession(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }
}