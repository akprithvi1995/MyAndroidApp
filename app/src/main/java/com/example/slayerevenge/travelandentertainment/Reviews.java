package com.example.slayerevenge.travelandentertainment;



import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.example.slayerevenge.travelandentertainment.details.information;
import static com.example.slayerevenge.travelandentertainment.details.lat;
import static com.example.slayerevenge.travelandentertainment.details.lng;

public class Reviews extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONObject reader;
    private JSONObject results;
    private JSONArray reviews;
    private String url="http://1pi13cs203-env.us-east-2.elasticbeanstalk.com/getyelp?calling=yelp-data";
    private JSONArray address;
    private JSONArray types;
    private JSONObject obj;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private JSONObject yelpObject;
    private JSONArray yelpreviews;
    private Spinner reviewcat;
    private Spinner sort;
    private String recycle_type="";
    private TextView noreviews;
    private List<ReviewData> reviewList= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String [] type={"Google Reviews","Yelp Reviews"};

        String [] order={"Default Order","Highest Rating","Lowest Rating","Most Recent","Least Recent"};

        View v=inflater.inflate(R.layout.activity_reviews, container, false);
        reviewcat=v.findViewById(R.id.sprevcat);
        noreviews=v.findViewById(R.id.tvnoresults);
        sort=v.findViewById(R.id.spsortorder);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, type);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        reviewcat.setAdapter(LTRadapter);
        LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, order);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sort.setAdapter(LTRadapter);

        mRecyclerView = v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyAdapter(getContext(),authorName,reviewRating,time,reviewText,authorUrl,profileUrl);
//        mRecyclerView.setAdapter(mAdapter);
        try {
            reader = new JSONObject(information);
            results= reader.getJSONObject("result");
            reviews = results.getJSONArray("reviews");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(reader.getString("status").equals("OK"))
            {

                url += "&name=" + URLEncoder.encode(results.getString("name"));
                address = results.getJSONArray("address_components");

                String form_req;
                Log.d("Review size",String.valueOf(address.length()));

                for(int j = 0; j < address.length(); j++) {
                    obj = address.getJSONObject(j);
                    types = obj.getJSONArray("types");
                    form_req = types.getString(0);
                    Log.d("forloop",form_req);

                    if(form_req.equals("street_number")) {
                        String street = obj.getString("long_name");
                        url += "&street=" + URLEncoder.encode(street);
                    }
                    if(form_req.equals("route")) {
                        String route = obj.getString("long_name");
                        url += "&address1=" + URLEncoder.encode(route);
                    }
                    if(form_req.equals("neighborhood")) {
                        String neighborhood = obj.getString("short_name");
                        url += "&address2=" + URLEncoder.encode(neighborhood);
                    }
                    if(form_req.equals("locality")) {
                        String locality = obj.getString("long_name");
                        url += "&city=" + URLEncoder.encode(locality);
                    }
                    if(form_req.equals("administrative_area_level_1")) {
                        String state = obj.getString("short_name");
                        url += "&state=" + URLEncoder.encode(state);
                    }
                    if(form_req.equals("country")) {
                        String country = obj.getString("short_name");
                        url += "&country=" + URLEncoder.encode(country);
                    }
                    if(form_req.equals("postal_code")) {
                        String postalCode = obj.getString("long_name");
                        url += "&postal_code=" + URLEncoder.encode(postalCode);
                    }
                }

                if(results.getString("formatted_phone_number") != null) {
                    url += "&phone=" + URLEncoder.encode(results.getString("formatted_phone_number"));
                }
                Log.d("yelprurl",url);





        }
        } catch (JSONException e) {
            Log.d("caught",e.toString());

            e.printStackTrace();
        }
        mRequestQueue = Volley.newRequestQueue(getContext());

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    yelpObject=new JSONObject(response.toString());
                    yelpreviews=yelpObject.getJSONArray("reviews");
                    Log.d("jlt",url);
                    Log.d("yelpme",yelpObject.toString());
                    Log.d("yelpreviews",yelpreviews.toString());
//                    getYelpReviews();

//                    getYelpReviews();

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

//        getGoogleReviews();

        reviewcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (reviewcat.getSelectedItem().equals("Google Reviews")){
                    getGoogleReviews();
                }
                else {
                    getYelpReviews();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        String [] order={"Default Order","Highest Rating","Lowest Rating","Most Recent","Least Recent"};
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sort.getSelectedItem()=="Default Order"){
                    Collections.sort(reviewList, new Comparator<ReviewData>() {
                        @Override
                        public int compare(ReviewData lhs, ReviewData rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return lhs.getDeford() < rhs.getDeford()? -1 :  0;
                        }
                    });
                }
                if (sort.getSelectedItem()=="Highest Rating"){
                    Collections.sort(reviewList, new Comparator<ReviewData>() {
                        @Override
                        public int compare(ReviewData lhs, ReviewData rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return Float.parseFloat(lhs.getReviewRating()) > Float.parseFloat(rhs.getReviewRating())? -1 :  0;
                        }
                    });

                }
                if (sort.getSelectedItem()=="Lowest Rating"){
                    Collections.sort(reviewList, new Comparator<ReviewData>() {
                        @Override
                        public int compare(ReviewData lhs, ReviewData rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return Float.parseFloat(lhs.getReviewRating()) < Float.parseFloat(rhs.getReviewRating())? -1 :  0;
                        }
                    });

                }
                if (sort.getSelectedItem()=="Most Recent"){
                    Collections.sort(reviewList, new Comparator<ReviewData>() {
                        @Override
                        public int compare(ReviewData lhs, ReviewData rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return lhs.getEpochtime() > rhs.getEpochtime()? -1 :  0;
                        }
                    });

                }
                if (sort.getSelectedItem()=="Least Recent"){
                    Collections.sort(reviewList, new Comparator<ReviewData>() {
                        @Override
                        public int compare(ReviewData lhs, ReviewData rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return lhs.getEpochtime() < rhs.getEpochtime()? -1 :  0;
                        }
                    });

                }

                mAdapter = new MyAdapter(getContext(),reviewList,recycle_type=reviewcat.getSelectedItem().toString());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mAdapter = new MyAdapter(getContext(),reviewList,recycle_type="no-data");
        mRecyclerView.setAdapter(mAdapter);
        getGoogleReviews();
        return v;
    }
        public void getGoogleReviews(){
            sort.setSelection(0);
            reviewList.clear();

            if(reviews!=null){
                noreviews.setVisibility(View.GONE);

                     for (int i=0; i<reviews.length(); i++) {

                         String aname,aurl,rrating,purl,rtext,t;
                        Log.d("enter","hi");
                        long epoch;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        JSONObject each_result = null;
                        try {
                            each_result = reviews.getJSONObject(i);
                            Log.d("eachresult",each_result.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            aname= each_result.getString("author_name");
                            aurl= each_result.getString("author_url");
                            rrating= each_result.getString("rating");
                            purl= each_result.getString("profile_photo_url");
                            rtext= each_result.getString("text");
                            epoch=each_result.getLong("time");

                            t=simpleDateFormat.format(epoch*1000);
                            ReviewData data= new ReviewData(rtext,aname,rrating,aurl,purl,t,i);
                            data.setEpochtime(epoch);
                            reviewList.add(data);



                        } catch (JSONException e) {
                            Log.d("error",e.toString());
                            e.printStackTrace();
                        }
                    }

                mAdapter = new MyAdapter(getContext(),reviewList,recycle_type="Google Reviews");
                mRecyclerView.setAdapter(mAdapter);
                }
            else{   noreviews.setVisibility(View.VISIBLE);
                    mAdapter = new MyAdapter(getContext(),reviewList,recycle_type="no-data");
                    mRecyclerView.setAdapter(mAdapter);
                }
        }
    public void getYelpReviews(){
        reviewList.clear();
        sort.setSelection(0);
         if(yelpreviews!=null) {
             noreviews.setVisibility(View.GONE);
            String reviewRating,reviewText,time,profileUrl,url,authorName;

            for (int i = 0; i < yelpreviews.length(); i++) {
                JSONObject each_result = null;
                try {
                    each_result = yelpreviews.getJSONObject(i);
                    reviewRating = each_result.getString("rating");
                    reviewText = each_result.getString("text");
                    time = each_result.getString("time_created");
                    profileUrl = each_result.getJSONObject("user").getString("image_url");
                    authorName = each_result.getJSONObject("user").getString("name");
                    url=each_result.getString("url");

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = df.parse(time);
                    long epoch = date.getTime();
                    ReviewData data= new ReviewData(reviewText,authorName,reviewRating,url,profileUrl,time,i);
                    data.setEpochtime(epoch);
                    reviewList.add(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                mAdapter = new MyAdapter(getContext(),reviewList,recycle_type="Yelp Reviews");
                mRecyclerView.setAdapter(mAdapter);
            }
        }
        else{
            recycle_type="no-data";
            noreviews.setVisibility(View.VISIBLE);
             mAdapter = new MyAdapter(getContext(),reviewList,recycle_type="no-data");
             mRecyclerView.setAdapter(mAdapter);
        }


    }
}