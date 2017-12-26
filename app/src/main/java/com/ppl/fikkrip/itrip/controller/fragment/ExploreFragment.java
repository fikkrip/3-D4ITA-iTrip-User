package com.ppl.fikkrip.itrip.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.controller.activity.PulauActivity;
import com.ppl.fikkrip.itrip.controller.adapter.ListPopularAdapter;
import com.ppl.fikkrip.itrip.model.PopularModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ExploreFragment extends Fragment {

    private ListPopularAdapter adapter;
    private ArrayList<PopularModel> listPopular = null;
    private RecyclerView lvhape;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private SliderLayout sliderLayout;

    public ExploreFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPopular = new ArrayList<>();
        getListPopular();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        lvhape = (RecyclerView) view.findViewById(R.id.lvhape);

        //POPULER
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        lvhape.setLayoutManager(llm);
        adapter = new ListPopularAdapter(getContext(), listPopular);
        lvhape.setAdapter(adapter);

        //SLIDER
        HashMap<String,Integer> file_maps = new HashMap<>();
        file_maps.put("Pulau Jawa", R.drawable.javanew);
        file_maps.put("Pulau Sumatera", R.drawable.sumateranew);
        file_maps.put("Pulau Kalimantan", R.drawable.kalimantannew);
        file_maps.put("Pulau Sulawesi", R.drawable.sulawesinew);
        file_maps.put("Pulau Papua", R.drawable.papuanew);

        for(final String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            if(name.equals("Pulau Jawa")){
                                Intent intent = new Intent(getActivity(), PulauActivity.class);
                                String idPulau = "idJawa";
                                intent.putExtra("idPulau", idPulau);
                                getActivity().startActivity(intent);
                            } else if(name.equals("Pulau Sumatera")){
                                Intent intent = new Intent(getActivity(), PulauActivity.class);
                                String idPulau = "idSumatera";
                                intent.putExtra("idPulau", idPulau);
                                getActivity().startActivity(intent);
                            } else if(name.equals("Pulau Kalimantan")){
                                Intent intent = new Intent(getActivity(), PulauActivity.class);
                                String idPulau = "idKalimantan";
                                intent.putExtra("idPulau", idPulau);
                                getActivity().startActivity(intent);
                            } else if(name.equals("Pulau Sulawesi")){
                                Intent intent = new Intent(getActivity(), PulauActivity.class);
                                String idPulau = "idSulawesi";
                                intent.putExtra("idPulau", idPulau);
                                getActivity().startActivity(intent);
                            } else if(name.equals("Pulau Papua")) {
                                Intent intent = new Intent(getActivity(), PulauActivity.class);
                                String idPulau = "idPapua";
                                intent.putExtra("idPulau", idPulau);
                                getActivity().startActivity(intent);
                            } else{
                                Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(6000);

        return view;
    }

    public void getListPopular() {
        String url = getString(R.string.api) + "ListPopular.php";
        requestQueue = Volley.newRequestQueue(getActivity());
                stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("listpopular");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        PopularModel popularModel = new PopularModel();
                            popularModel.setIdWisata(json.getInt("idWisata"));
                            popularModel.setNamaProvinsi(json.getString("namaProvinsi"));
                            popularModel.setNamaWisata(json.getString("namaWisata"));
                            popularModel.setBiayaMasuk(json.getInt("biayaMasuk"));
                            popularModel.setDeskripsiWisata(json.getString("deskripsiWisata"));
                            popularModel.setKategori(json.getString("kategori"));
                            popularModel.setLokasiWisata(json.getString("lokasiWisata"));
                            popularModel.setGambarWisata(json.getString("gambarWisata"));
                            popularModel.setRating(json.getInt("status"));
                        listPopular.add(popularModel);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Data tidak Ada", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

}