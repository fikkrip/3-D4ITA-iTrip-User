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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListFavoritAdapter extends RecyclerView.Adapter<ListFavoritAdapter.ViewHolder> {

    Context c;
    ArrayList<FavoritModel> listFavorit;
    private JSONArray result;

    public ListFavoritAdapter(Context con, ArrayList<FavoritModel> list_data) {
        this.c = con;
        this.listFavorit = list_data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtWisata;
        TextView txtLokasi;
        ImageView imghape, favorit, btnShare;
        CardView cardFavorit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtWisata = (TextView) itemView.findViewById(R.id.txt_nama);
            txtLokasi = (TextView) itemView.findViewById(R.id.txt_lokasi);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
            cardFavorit = (CardView) itemView.findViewById(R.id.card_view);
            favorit = (ImageView) itemView.findViewById(R.id.btn_favorit);
            btnShare = (ImageView) itemView.findViewById(R.id.btn_share);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_favorit, null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listFavorit.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(c)
                .load(c.getString(R.string.img) + listFavorit.get(position).getGambar()).crossFade()
                .placeholder(R.drawable.ic_nature)
                .into(holder.imghape);
        holder.txtWisata.setText(listFavorit.get(position).getNamaWisata());
        holder.txtLokasi.setText(listFavorit.get(position).getLokasi());

        if(listFavorit.get(position).getStatus().equals("")){
            holder.favorit.setImageResource(R.drawable.ic_star_2);
            holder.favorit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    SessionManager session = new SessionManager(c);
                    session.checkLogin();
                    HashMap<String, String> user = session.getUserDetails();
                    String idUser = user.get(SessionManager.KEY_ID);
                    saveFavorit(listFavorit.get(position).getIdWisata(), idUser);
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
                    deleteFavorit(listFavorit.get(position).getIdWisata(), idUser);
                }
            });
        }
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "I-Trip");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Wisata :"+listFavorit.get(position).getNamaWisata()+" Lokasi : "+listFavorit.get(position).getLokasi());
                c.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        holder.cardFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("namaWisata", listFavorit.get(position).getNamaWisata());
                bundle.putInt("biayaMasuk", listFavorit.get(position).getBiayaMasuk());
                bundle.putString("lokasiWisata", listFavorit.get(position).getLokasi());
                bundle.putString("deskripsiWisata", listFavorit.get(position).getDeskripsiWisata());
                bundle.putString("gambarWisata", listFavorit.get(position).getGambar());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
        holder.imghape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("namaWisata", listFavorit.get(position).getNamaWisata());
                bundle.putInt("biayaMasuk", listFavorit.get(position).getBiayaMasuk());
                bundle.putString("lokasiWisata", listFavorit.get(position).getLokasi());
                bundle.putString("deskripsiWisata", listFavorit.get(position).getDeskripsiWisata());
                bundle.putString("gambarWisata", listFavorit.get(position).getGambar());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
    }
    public void saveFavorit(int idWisata, String idUser){
        String url = c.getString(R.string.api) + "saveFavorit.php";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    JSONObject j = null;

                    if (success) {
                        Toast.makeText(c, "SIMPAN!", Toast.LENGTH_SHORT).show();
                        listFavorit.clear();
                        j = new JSONObject(response);
                        result = j.getJSONArray("listfavorit");
                        showListFavorit(result);
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
                    JSONObject j = null;

                    if (success) {
                        Toast.makeText(c, "BERHASIL!", Toast.LENGTH_SHORT).show();
                        listFavorit.clear();
                        j = new JSONObject(response);
                        result = j.getJSONArray("listfavorit");
                        showListFavorit(result);
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

    public void showListFavorit(JSONArray result){
        for (int i = 0; i < result.length(); i++) {
            try {
                //Getting json object
                JSONObject json = result.getJSONObject(i);
                FavoritModel favoritModel = new FavoritModel();
                favoritModel.setIdWisata(json.getInt("idWisata"));
                favoritModel.setIdProvinsi(json.getInt("idProvinsi"));
                favoritModel.setNamaProvinsi(json.getString("namaProvinsi"));
                favoritModel.setNamaWisata(json.getString("namaWisata"));
                favoritModel.setDeskripsiWisata(json.getString("deskripsiWisata"));
                favoritModel.setKategori(json.getString("kategori"));
                favoritModel.setBiayaMasuk(json.getInt("biayaMasuk"));
                favoritModel.setLongtitude(json.getString("longtitude"));
                favoritModel.setLatitude(json.getString("latitude"));
                favoritModel.setLokasi(json.getString("lokasiWisata"));
                favoritModel.setGambar(json.getString("gambarWisata"));
                favoritModel.setStatus(json.getString("status"));
                favoritModel.setIdUser(json.getString("idUser"));
                listFavorit.add(favoritModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}