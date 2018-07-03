package com.example.slayerevenge.travelandentertainment;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.slayerevenge.travelandentertainment.TabFragment1.P_no;
import static com.example.slayerevenge.travelandentertainment.TabFragment1.alltok;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class List_Places extends AppCompatActivity {
    private JSONObject reader;
    private String message;
    private JSONArray results;
    private TextView noresults;
    private JSONObject changed_Reader;
    private JSONArray changed_results;
    private String next;
    private List<PlaceData> placeslist= new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    ListView list;

    private Button Prev;
    private Button Next;
    private String current_token="first";
    private String next_token;
    private String check;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private Toolbar toolbar;

    private String url="http://1pi13cs203-env.us-east-2.elasticbeanstalk.com/getplaces?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__places);

        noresults=findViewById(R.id.tvnolist);
        noresults.setVisibility(View.GONE);
        Prev=findViewById(R.id.btnprev);
        Prev.setEnabled(false);
        Next=findViewById(R.id.btnnext);
        Next.setEnabled(false);
        toolbar = (Toolbar) findViewById(R.id.tbsearch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search results");
        mRecyclerView = findViewById(R.id.my_recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);




        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");

        try {
            reader = new JSONObject(message);
            if(reader.getString("status")=="ZERO RESULTS"||reader.getString("status")=="EMPTY RESULTS"||reader.getJSONArray("results").length()==0){
                noresults.setVisibility(View.VISIBLE);
            }
            else{noresults.setVisibility(View.GONE);};
            results= reader.getJSONArray("results");
            Log.d("latitudesearch",results.toString());
            check=reader.getString("next_page_token");
            if(check.trim().length()!=0){
                Next.setEnabled(true);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Prev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Next.setEnabled(true);
                P_no = P_no- 1;

                volleyIt(alltok.get(P_no),"prev");
                if(P_no==0){
                    Prev.setEnabled(false);
                }

            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        String value = "";
                        Prev.setEnabled(true);
                        if ((P_no + 1) < alltok.size()) {
                        value = alltok.get(P_no + 1);
                        } else {
                        value = next;
                        alltok.add(value);
                        }
                        P_no = P_no + 1;

//                        Log.d("Monkey", String.valueOf(PAGE_NO));
                        volleyIt(value,"next");
                        }



        });
        try {

            Call_List(reader);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void volleyIt(String s,String from) {

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        final ProgressDialog progress = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progress.setCancelable(false);
        if(from.equals("next")){
        progress.setMessage("Fetching Next Page");}
        else if (from.equals("prev")){
            progress.setMessage("Fetching Previous Page");
        }
        progress.show();

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET,url+s , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                JSONObject newmessage;
                try {
                    newmessage = new JSONObject(response.toString());

                    Call_List(newmessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        mRequestQueue.add(mStringRequest);

    }

    private void Call_List(JSONObject reader) throws JSONException {
        placeslist.clear();

        JSONArray results=reader.getJSONArray("results");


            if (reader.has("next_page_token")) {
                next = "&pagetoken=" + reader.getString("next_page_token");

            } else {
                Next.setEnabled(false);
            }
            String[] allNames = new String[results.length()];
            String[] allicons = new String[results.length()];
            String[] allvicinity = new String[results.length()];
            String[] allplaceid = new String[results.length()];
            Double[] lat = new Double[results.length()];
            Double[] lng = new Double[results.length()];

            for (int i = 0; i < results.length(); i++) {

                JSONObject each_result = null;
                try {
                    each_result = results.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String name = null;
                String icon = null;
                String vicinity = null;
                String placeid = null;
                Double latitude = null;
                Double longitude = null;

                try {
                    name = each_result.getString("name");
                    icon = each_result.getString("icon");
                    placeid = each_result.getString("place_id");

                    vicinity = each_result.getString("vicinity");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    latitude = each_result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    longitude = each_result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                    Log.d("lat", String.valueOf(each_result.getJSONObject("geometry").getJSONObject("location").getDouble("lat")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PlaceData data = new PlaceData(name, icon, vicinity, placeid);
                placeslist.add(data);

                allNames[i] = name;
                allicons[i] = icon;
                allvicinity[i] = vicinity;
                allplaceid[i] = placeid;
                lat[i] = latitude;
                lng[i] = longitude;

            }
            mAdapter = new PlacesListAdapter(this, placeslist);
            mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Call_List(reader);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
    }
}
