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

import com.bumptech.glide.Glide;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.activity.DetailActivity;
import com.ppl.fikkrip.itrip.model.PopularModel;

import org.json.JSONArray;

import java.util.ArrayList;

public class ListPopularAdapter extends RecyclerView.Adapter<ListPopularAdapter.ViewHolder> {

    Context c;
    ArrayList<PopularModel> listPopular;
    private JSONArray result;
    ArrayList<String> gambarList = new ArrayList<>();

    public ListPopularAdapter(Context con, ArrayList<PopularModel> list_data) {
        this.c = con;
        this.listPopular = list_data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtWisata;
        ImageView imghape;
        CardView cvPopular;

        public ViewHolder(View itemView) {
            super(itemView);
            txtWisata = (TextView) itemView.findViewById(R.id.txtWisata);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
            cvPopular = (CardView) itemView.findViewById(R.id.cvPopular);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listPopular.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(c)
                .load(c.getString(R.string.img) + listPopular.get(position).getGambarWisata()).crossFade()
                .placeholder(R.drawable.ic_nature)
                .into(holder.imghape);
        holder.txtWisata.setText(listPopular.get(position).getNamaWisata());
        holder.cvPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("namaWisata", listPopular.get(position).getNamaWisata());
                bundle.putInt("biayaMasuk", listPopular.get(position).getBiayaMasuk());
                bundle.putString("lokasiWisata", listPopular.get(position).getLokasiWisata());
                bundle.putString("deskripsiWisata", listPopular.get(position).getDeskripsiWisata());
                bundle.putString("gambarWisata", listPopular.get(position).getGambarWisata());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
    }
}