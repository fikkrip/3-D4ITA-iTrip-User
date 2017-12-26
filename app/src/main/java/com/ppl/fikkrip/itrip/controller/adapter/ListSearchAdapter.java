package com.ppl.fikkrip.itrip.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.activity.DetailActivity;
import com.ppl.fikkrip.itrip.model.DataWisata;

import java.util.ArrayList;

/**
 * Created by Pinky Cindy on 10/29/17.
 */

public class ListSearchAdapter extends RecyclerView.Adapter<ListSearchAdapter.ViewHolder> implements Filterable {

    Context c;
    private ArrayList<DataWisata> mArrayList;
    private ArrayList<DataWisata> mFilteredList;

    public ListSearchAdapter(Context con, ArrayList<DataWisata> arrayList) {
        this.c = con;
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public ListSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListSearchAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.txtWisata.setText(mFilteredList.get(i).getNama());
        viewHolder.txtLokasi.setText(mFilteredList.get(i).getLokasi());
        Glide.with(c)
                .load(c.getString(R.string.img)+mFilteredList.get(i).getGambar()).asBitmap().centerCrop().into(new BitmapImageViewTarget(viewHolder.imghape) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(c.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                viewHolder.imghape.setImageDrawable(circularBitmapDrawable);
            }
        });

        viewHolder.cvPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("namaWisata", mFilteredList.get(i).getNama());
                bundle.putInt("biayaMasuk", mFilteredList.get(i).getBiayaMasuk());
                bundle.putString("lokasiWisata", mFilteredList.get(i).getLokasi());
                bundle.putString("deskripsiWisata", mFilteredList.get(i).getDeskripsi());
                bundle.putString("gambarWisata", mFilteredList.get(i).getGambar());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });

        viewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "I-Trip");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Wisata :"+mFilteredList.get(i).getNama()+" Lokasi : "+mFilteredList.get(i).getLokasi());
                c.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<DataWisata> filteredList = new ArrayList<>();

                    for (DataWisata androidVersion : mArrayList) {

                        if (androidVersion.getNama().toLowerCase().contains(charString) || androidVersion.getLokasi().toLowerCase().contains(charString) || androidVersion.getGambar().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<DataWisata>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtWisata;
        TextView txtLokasi;
        ImageView imghape;
        CardView cvPopular;
        Button btnShare;

        public ViewHolder(View itemView) {
            super(itemView);
            txtWisata = (TextView) itemView.findViewById(R.id.txtWisata);
            txtLokasi = (TextView) itemView.findViewById(R.id.txtLokasi);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
            cvPopular = (CardView) itemView.findViewById(R.id.cvPopular);
            btnShare = (Button) itemView.findViewById(R.id.btn_share);
        }
    }

}
