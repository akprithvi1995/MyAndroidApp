package com.example.slayerevenge.travelandentertainment;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.slayerevenge.travelandentertainment.details.lat;
import static com.example.slayerevenge.travelandentertainment.details.lng;
import static com.example.slayerevenge.travelandentertainment.details.nametab;

public class Maps extends Fragment implements OnMapReadyCallback {
    GoogleMap googleMap;
    MapView mapView;
    View v;
    Spinner modes;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String routes;
    private JSONObject result;
    String request_url;
    private AutoCompleteTextView from;
    private Polyline mypolyline;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("cray","cray");
        v=inflater.inflate(R.layout.activity_map, container, false);
        modes = v.findViewById(R.id.modes);
        from = v.findViewById(R.id.etfrom);
        CustomAutoCompleteAdapter adapter = new CustomAutoCompleteAdapter(this.getContext());
        from.setAdapter(adapter);
        from.setOnItemClickListener(this.onItemClickListener);




                // your code here



        String[] state = {"Driving", "Bicycling", "Transit", "Walking"};
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, state);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        modes.setAdapter(LTRadapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView=v.findViewById(R.id.map);
        if (mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);

        }

    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppCompatTextView tv =(AppCompatTextView) view;
            from.setText(tv.getText().toString());
            alterMap();
        }
    };
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Marker marker=this.googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(nametab));
        marker.showInfoWindow();
        CameraPosition Liberty= CameraPosition.builder().target(new LatLng(lat,lng)).zoom(10).bearing(0).build();
        this.googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));



        modes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,    int position, long id) {
                alterMap();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });




    }
    public void alterMap(){
        if (from.getText().toString().trim().length()!=0) {
            request_url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + from.getText().toString() + "&destination=" + lat + "," + lng +"&mode="+modes.getSelectedItem().toString().toLowerCase()+ "&key=AIzaSyDHa-bDKF0yvKl4xHoJ8fomulvGqgp4rwA";
            Log.d("url",request_url);
            googleMap.clear();

            mRequestQueue = Volley.newRequestQueue(getContext());
            mStringRequest = new StringRequest(Request.Method.GET, request_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        if(mypolyline!=null){mypolyline.remove();}
                        result = new JSONObject(response.toString());
                        routes = result.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points");
                        Log.d("route", routes);
                        List<LatLng> route = PolyUtil.decode(routes);
                        mypolyline=googleMap.addPolyline(new PolylineOptions().addAll(route).color(Color.BLUE).width(5));
                        Marker marker=googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(nametab));
                        marker.showInfoWindow();
                        googleMap.addMarker(new MarkerOptions().position(route.get(0)));
                        LatLngBounds.Builder b = new LatLngBounds.Builder();
                        b.include(route.get(route.size()-1)).include(route.get(0));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(b.build(), 40));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("urlpoo", request_url);
//                Toast.makeText(getActivity().getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("new", "new");


                }
            });
            mRequestQueue.add(mStringRequest);
        }

    }
}