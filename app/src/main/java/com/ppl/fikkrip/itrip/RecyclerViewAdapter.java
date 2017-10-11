package com.ppl.fikkrip.itrip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
            //Glide.with(context)
            //.load("http://192.168.95.77/app_blogvolley/img/" + list_data.get(position).get("nama"))
            //.crossFade()
            //.placeholder(R.mipmap.ic_launcher)
            //.into(holder.imghape);
            holder.txthape.setText(list_data.get(position).get("nama"));
    }

    @Override
    public int getItemCount() {
            return list_data.size();
            }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txthape;
        ImageView imghape;

        public ViewHolder(View itemView) {
            super(itemView);
            txthape = (TextView) itemView.findViewById(R.id.txthape);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
        }
    }
}