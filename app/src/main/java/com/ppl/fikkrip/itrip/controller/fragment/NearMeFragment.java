package com.ppl.fikkrip.itrip.controller.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.model.WisataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NearMeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Location mLastLocation;
    private Button btLocation;
    private GoogleApiClient mGoogleApiClient;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ArrayList<WisataModel> list = null;

    MapView mMapView;
    private GoogleMap googleMap;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGoogleAPI();
        if (mLastLocation != null) {

//            Toast.makeText(getActivity(), " Get Location \n " +
//                    "Latitude : " + mLastLocation.getLatitude() +
//                    "\nLongitude : " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_near_me, container, false);


        if (mLastLocation != null) {

            list = new ArrayList<>();
            list = getLokasiWisata(mLastLocation.getLatitude(),mLastLocation.getLongitude());
//            Toast.makeText(getActivity(), " Get Location \n " +
//                    "Latitude : " + mLastLocation.getLatitude() +
//                    "\nLongitude : " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();

            mMapView = (MapView) view.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume(); // needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }


            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    // For showing a move to my location button
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    googleMap.setMyLocationEnabled(true);


                    // For dropping a marker at a point on the Map
                    for (int i = 0; i < list.size(); i++) {
                        LatLng syd = new LatLng(list.get(i).getLongtitude(), list.get(i).getLatitude());

                        googleMap.addMarker(new MarkerOptions()
                                .position(syd)
                                .title(list.get(i).getNama())
                                .snippet(list.get(i).getLokasi())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                    LatLng sydney = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    googleMap.addMarker(new MarkerOptions()
                            .position(sydney)
                            .title("Your Location")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                    // CameraPosition cameraPosition2 = new CameraPosition.Builder().target(syd).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition2));

                   // getCompleteAddressString(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                }
            });

        }
//
//        // initialize button
//        btLocation = (Button) view.findViewById(R.id.bt_getLocation);
//        btLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mLastLocation != null) {
//                    Toast.makeText(getActivity()," Get Location \n " +
//                            "Latitude : "+ mLastLocation.getLatitude()+
//                            "\nLongitude : "+mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        return view;
    }

    private void setupGoogleAPI() {
        // initialize Google API Client
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        // get last location ketika berhasil connect
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
//            Toast.makeText(getActivity()," Connected to Google Location API", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    @Override
    public void onResume() {
        super.onResume();
     //   mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public ArrayList<WisataModel> getLokasiWisata(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                final Address returnedAddress = addresses.get(0);
//                Toast.makeText(getActivity(), returnedAddress.getAdminArea(), Toast.LENGTH_SHORT).show();


                String url = getString(R.string.api) + "listWisata.php";
                requestQueue = Volley.newRequestQueue(getActivity());
                stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject json = jsonArray.getJSONObject(a);
                                WisataModel wisataModel = new WisataModel();
                                wisataModel.setIdWisata(json.getInt("idWisata"));
                                wisataModel.setNama(json.getString("namaWisata"));
                                wisataModel.setBiayaMasuk(json.getInt("biayaMasuk"));
                                wisataModel.setKategori(json.getString("kategori"));
                                wisataModel.setLokasi(json.getString("lokasiWisata"));
                                wisataModel.setGambar(json.getString("gambarWisata"));
                                wisataModel.setLatitude(json.getDouble("latitude"));
                                wisataModel.setLongtitude(json.getDouble("longtitude"));
                                list.add(wisataModel);
                            }

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
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("provinsi", returnedAddress.getAdminArea());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Canont get Address", Toast.LENGTH_SHORT).show();
            Log.w("My Current loction address", "Canont get Address!");
        }

        return list;


    }



}