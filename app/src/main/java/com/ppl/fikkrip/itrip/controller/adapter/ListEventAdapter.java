package com.ppl.fikkrip.itrip.controller.adapter;

/**
 * Created by Fikkri Prasetya on 19/12/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.model.EventModel;

import java.util.ArrayList;

public class ListEventAdapter extends RecyclerView.Adapter<ListEventAdapter.ViewHolder>{

    Context c;
    private ArrayList<EventModel> mArrayList;

    public ListEventAdapter(Context con, ArrayList<EventModel> arrayList) {
        this.c = con;
        mArrayList = arrayList;
    }

    @Override
    public ListEventAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListEventAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.txtEvent.setText(mArrayList.get(i).getNamaEvent());
        viewHolder.txtLokasi.setText(mArrayList.get(i).getLokasiEvent());
        viewHolder.txtBulan.setText(mArrayList.get(i).getTglEvent());
        viewHolder.cvEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(c)
                        .setTitle(mArrayList.get(i).getNamaEvent())
                        .setMessage(mArrayList.get(i).getDeskripsiEvent())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEvent;
        TextView txtLokasi;
        TextView txtBulan;
        CardView cvEvent;

        public ViewHolder(View itemView) {
            super(itemView);
            txtEvent = (TextView) itemView.findViewById(R.id.txt_event);
            txtLokasi = (TextView) itemView.findViewById(R.id.txt_lokasi);
            txtBulan = (TextView) itemView.findViewById(R.id.txt_bulan);
            cvEvent = (CardView) itemView.findViewById(R.id.cvEvent);
        }
    }
}
