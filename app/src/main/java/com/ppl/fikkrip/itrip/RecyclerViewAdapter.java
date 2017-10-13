package com.ppl.fikkrip.itrip;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context c;
    ArrayList<HashMap<String, String>> list_data;

    public RecyclerViewAdapter(Context con, ArrayList<HashMap<String, String>> list_data) {
            this.c = con;
            this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, null);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(c)
                .load(c.getString(R.string.img)+list_data.get(position).get("gambarWisata")).crossFade()
                .placeholder(R.drawable.ic_nature)
                .into(holder.imghape);
        holder.txtWisata.setText(list_data.get(position).get("namaWisata"));
        holder.txtLokasi.setText(list_data.get(position).get("lokasiWisata"));
        holder.cvPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                LayoutInflater inflater = LayoutInflater.from(c);
                View view = inflater.inflate(R.layout.detail_wisata, null);

                builder.setView(view);
                TextView tvBiaya = (TextView) view.findViewById(R.id.tvBiaya);
                TextView tvLokasi = (TextView) view.findViewById(R.id.tvLokasi);
                TextView tvDeskripsi = (TextView) view.findViewById(R.id.tvDeskripsi);
                ImageButton ibGambar = (ImageButton) view.findViewById(R.id.ibGambar);

                Glide.with(c).load(c.getString(R.string.img)+list_data.get(position).get("gambarWisata")).crossFade()
                        .placeholder(R.drawable.ic_nature)
                        .into(ibGambar);
                tvBiaya.setText(("Biaya : " + (list_data.get(position).get("biayaMasuk"))));
                tvLokasi.setText(("Lokasi : " + (list_data.get(position).get("lokasiWisata"))));
                tvDeskripsi.setText(list_data.get(position).get("deskripsiWisata"));

                AlertDialog ad = builder.create();
                ad.setTitle(list_data.get(position).get("namaWisata"));
                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "CLOSE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                ad.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtWisata;
        TextView txtLokasi;
        ImageView imghape;
        CardView cvPopular;

        public ViewHolder(View itemView) {
            super(itemView);
            txtWisata = (TextView) itemView.findViewById(R.id.txtWisata);
            txtLokasi = (TextView) itemView.findViewById(R.id.txtLokasi);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
            cvPopular = (CardView) itemView.findViewById(R.id.cvPopular);
        }
    }
}