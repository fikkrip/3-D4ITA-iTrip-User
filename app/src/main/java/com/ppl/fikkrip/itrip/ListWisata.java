package com.ppl.fikkrip.itrip;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.name;

public class ListWisata extends RecyclerView.Adapter<ListWisata.ViewHolder>{

    Context c;
    ArrayList<HashMap<String, String>> list_data;
    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtWisata;
        TextView txtLokasi;
        ImageView imghape;
        CardView cvMain;

        public ViewHolder(View itemView) {
            super(itemView);
            txtWisata = (TextView) itemView.findViewById(R.id.txtWisata);
            txtLokasi = (TextView) itemView.findViewById(R.id.txtLokasi);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
            cvMain = (CardView) itemView.findViewById(R.id.Cardpopular);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.Ex);
        }
    }
    public ListWisata(Context con, ArrayList<HashMap<String, String>> list_data) {
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
                .load("http://192.168.43.191/i-Trip/img/" + list_data.get(position).get("gambarWisata")).crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imghape);
        holder.txtWisata.setText(list_data.get(position).get("namaWisata"));
        holder.txtLokasi.setText(list_data.get(position).get("lokasiWisata"));
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }


}