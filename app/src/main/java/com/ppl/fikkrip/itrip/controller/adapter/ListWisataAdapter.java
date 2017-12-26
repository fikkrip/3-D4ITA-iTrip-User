package com.ppl.fikkrip.itrip.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.activity.DetailActivity;
import com.ppl.fikkrip.itrip.model.FavoritModel;
import com.ppl.fikkrip.itrip.rest.FavoritRequest;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListWisataAdapter extends RecyclerView.Adapter<ListWisataAdapter.ViewHolder>{

    Context c;
    ArrayList<FavoritModel>list_data;

    public ListWisataAdapter(Context con, ArrayList<FavoritModel> list_data) {
        this.c = con;
        this.list_data = list_data;
    }

    @Override
    public ListWisataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wisata, null);
        return new ListWisataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListWisataAdapter.ViewHolder holder, final int position) {
        Glide.with(c)
                .load(c.getString(R.string.img)+list_data.get(position).getGambar()).fitCenter()
                .placeholder(R.drawable.ic_nature)
                .into(holder.imghape);

        holder.txtWisata.setText(list_data.get(position).getNamaWisata());
        holder.txtLokasi.setText(list_data.get(position).getLokasi());
        if(list_data.get(position).getStatus().equals("")){
            holder.favorit.setImageResource(R.drawable.ic_star_2);
            holder.favorit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    SessionManager session = new SessionManager(c);
                    session.checkLogin();
                    HashMap<String, String> user = session.getUserDetails();
                    String idUser = user.get(SessionManager.KEY_ID);
                    saveFavorit(list_data.get(position).getIdWisata(), idUser);
                }
            });
        }
        else{
            holder.favorit.setImageResource(R.drawable.ic_star_1);
            holder.favorit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    SessionManager session = new SessionManager(c);
                    session.checkLogin();
                    HashMap<String, String> user = session.getUserDetails();
                    String idUser = user.get(SessionManager.KEY_ID);
                    deleteFavorit(list_data.get(position).getIdWisata(), idUser);
                }
            });
        }

        holder.imghape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("namaWisata", list_data.get(position).getNamaWisata());
                bundle.putInt("biayaMasuk", list_data.get(position).getBiayaMasuk());
                bundle.putString("lokasiWisata", list_data.get(position).getLokasi());
                bundle.putString("deskripsiWisata", list_data.get(position).getDeskripsiWisata());
                bundle.putString("gambarWisata", list_data.get(position).getGambar());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });

        holder.cvPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(c, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("namaWisata", list_data.get(position).getNamaWisata());
                bundle.putInt("biayaMasuk", list_data.get(position).getBiayaMasuk());
                bundle.putString("lokasiWisata", list_data.get(position).getLokasi());
                bundle.putString("deskripsiWisata", list_data.get(position).getDeskripsiWisata());
                bundle.putString("gambarWisata", list_data.get(position).getGambar());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "I-Trip");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Wisata :"+list_data.get(position).getNamaWisata()+" Lokasi : "+list_data.get(position).getLokasi());
                c.startActivity(Intent.createChooser(sharingIntent, "Share via"));
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
        ImageView imghape, favorit;
        CardView cvPopular;
        ImageView btnShare;

        public ViewHolder(View itemView) {
            super(itemView);
            txtWisata = (TextView) itemView.findViewById(R.id.txt_nama);
            txtLokasi = (TextView) itemView.findViewById(R.id.txt_lokasi);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
            cvPopular = (CardView) itemView.findViewById(R.id.card_view);
            favorit = (ImageView) itemView.findViewById(R.id.btn_favorit);
            btnShare = (ImageView) itemView.findViewById(R.id.btn_share);
        }
    }

    public void saveFavorit(int idWisata, String idUser){
        String url = c.getString(R.string.api) + "saveFavorit.php";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(c, "SIMPAN!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(c, "GAGAL!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String idW = Integer.toString(idWisata);

        FavoritRequest addRequest = new FavoritRequest(idW, idUser, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(addRequest);
    }
    public void deleteFavorit(int idWisata, String idUser){
        String url = c.getString(R.string.api) + "deleteFavorit.php";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(c, "BERHASIL!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(c, "GAGAL!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String idW = Integer.toString(idWisata);

        FavoritRequest addRequest = new FavoritRequest(idW, idUser, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(addRequest);
    }
}