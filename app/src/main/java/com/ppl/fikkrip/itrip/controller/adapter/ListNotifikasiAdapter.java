package com.ppl.fikkrip.itrip.controller.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.activity.ListNotifikasiActivity;
import com.ppl.fikkrip.itrip.model.PlanningModel;
import com.ppl.fikkrip.itrip.rest.PlanningRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pinky Cindy on 11/21/17.
 */

public class ListNotifikasiAdapter extends RecyclerView.Adapter<ListNotifikasiAdapter.ViewHolder>{

    Context c;
    private ArrayList<PlanningModel> mFilteredList;

    public ListNotifikasiAdapter(Context con, ArrayList<PlanningModel> arrayList) {
        this.c = con;
        mFilteredList = arrayList;
    }

    @Override
    public ListNotifikasiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notifikasi, null);
        return new ListNotifikasiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListNotifikasiAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.txtWisata.setText(mFilteredList.get(i).getJudul());
        viewHolder.txtLokasi.setText(mFilteredList.get(i).getTglPlanning());
        viewHolder.cvPopular.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                alertDialogBuilder.setTitle("Delete Notification");

                alertDialogBuilder
                        .setMessage("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String idPlanning = Integer.toString(mFilteredList.get(i).getIdPlanning());
                                String url = c.getString(R.string.api) + "updateListNotifikasi.php";

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                Toast.makeText(c.getApplicationContext(), "Deleted notification!!!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(c, ListNotifikasiActivity.class);
                                                c.startActivity(intent);
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(c);
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

                                PlanningRequest planningRequest = new PlanningRequest(idPlanning, url, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(c);
                                queue.add(planningRequest);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtWisata;
        TextView txtLokasi;
        CardView cvPopular;

        public ViewHolder(View itemView) {
            super(itemView);
            txtWisata = (TextView) itemView.findViewById(R.id.txtWisata);
            txtLokasi = (TextView) itemView.findViewById(R.id.txtLokasi);
            cvPopular = (CardView) itemView.findViewById(R.id.cvPopular);
        }
    }

}