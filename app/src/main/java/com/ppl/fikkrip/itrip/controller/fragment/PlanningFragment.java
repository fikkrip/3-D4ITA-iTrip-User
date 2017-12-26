package com.ppl.fikkrip.itrip.controller.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.activity.Notifikasireceiver;
import com.ppl.fikkrip.itrip.controller.activity.SearchActivity;
import com.ppl.fikkrip.itrip.model.DataWisata;
import com.ppl.fikkrip.itrip.rest.PlanningRequest;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Pinky Cindy on 10/27/17.
 */

public class PlanningFragment extends Fragment {

    private EditText txtDate, txtJudul;
    Button btnCariWisata, btnSave;
    private ImageButton btnDate;
    private int mYear, mMonth, mDay;
    public static ArrayList<DataWisata> mSelectedList;
    private List<String> listPlanning = new ArrayList<String>();
    private List<Integer> listIdWisata = new ArrayList<Integer>();
    public ArrayAdapter<String> adapter;
    public String idUser, idPlanning, judul, tglPlanning;
    SessionManager session;
    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    int tgl, bln, thn;

    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;

//    @Override
//    public void onResume() {
//        super.onResume();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == 1122 ) && (resultCode == Activity.RESULT_OK)) {
            Bundle myResults = data.getExtras();
            DataWisata save = new DataWisata();
            save.setIdWisata(myResults.getInt("idWisata"));
            save.setIdProvinsi(myResults.getInt("idProvinsi"));
            save.setNama(myResults.getString("namaWisata"));
            save.setDeskripsi(myResults.getString("deskripsiWisata"));
            save.setKategori(myResults.getString("kategori"));
            save.setBiayaMasuk(myResults.getInt("biayaMasuk"));
            save.setLokasi(myResults.getString("lokasiWisata"));
            save.setGambar(myResults.getString("gambarWisata"));

            listPlanning.add(save.getNama());
            listIdWisata.add(save.getIdWisata());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.planning_fragment, container, false);
        txtDate = (EditText) view.findViewById(R.id.tgl);
        txtJudul = (EditText) view.findViewById(R.id.jdl);
        btnDate = (ImageButton) view.findViewById(R.id.ImgBtn_date);
        btnCariWisata = (Button) view.findViewById(R.id.cariWisata);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        ListView listView = (ListView) view.findViewById(R.id.lvhape);

        Calendar calNow = Calendar.getInstance();
        final Calendar calSet = (Calendar) calNow.clone();
        session = new SessionManager(getContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        idUser = user.get(SessionManager.KEY_ID);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                        calSet.set(Calendar.DATE, dayOfMonth);
                        calSet.set(Calendar.MONTH, monthOfYear);
                        calSet.set(Calendar.YEAR, year);
                        calSet.set(Calendar.HOUR_OF_DAY, 6);
                        calSet.set(Calendar.MINUTE, 35);
                        calSet.set(Calendar.SECOND, 0);
                        calSet.set(Calendar.MILLISECOND, 0);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnCariWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivityForResult(intent, 1122);
            }
        });

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listPlanning);
        listView.setAdapter(adapter);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Please Wait");
                progressDialog.show();
                progressDialog.setMessage("Processing...");
                Toast.makeText(getContext().getApplicationContext(), idUser, Toast.LENGTH_SHORT).show();
                idPlanning = " ";
                judul = txtJudul.getText().toString();
                tglPlanning = txtDate.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext().getApplicationContext(), "Data Disimpan", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), Notifikasireceiver.class);
                                intent.putExtra("idPlanning", "2");
                                intent.putExtra("judul", "Lamonngan Trip^^");
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), RQS_1, intent, 0);
                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),pendingIntent);


                            } else {
                                progressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Gagal menyimpan Planning!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                PlanningRequest planningRequest = new PlanningRequest(idPlanning, idUser, judul, tglPlanning, getString(R.string.api)+"SavePlanning.php", responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(planningRequest);
            }
        });

        return view;
    }
}