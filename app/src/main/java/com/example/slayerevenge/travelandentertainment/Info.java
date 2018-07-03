package com.example.slayerevenge.travelandentertainment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.example.slayerevenge.travelandentertainment.details.information;

public class Info extends Fragment {
    private JSONObject reader;
    private JSONObject result;
    private LinearLayout layout_address;
    private LinearLayout layout_number;
    private LinearLayout layout_price;
    private LinearLayout layout_rating;
    private LinearLayout layout_gpage;
    private LinearLayout layout_website;
    private TextView text_address;
    private TextView text_number;
    private TextView text_price;

    private TextView text_gpage;
    private TextView text_website;
    private RatingBar star_ratings;

    private String info=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        info = ((details)getActivity()).getResponse();
        View v=inflater.inflate(R.layout.activity_info, container, false);
        layout_address = v.findViewById(R.id.lladdress);
        layout_number = v.findViewById(R.id.llnumber);
        layout_price = v.findViewById(R.id.llpricelevel);
        layout_rating = v.findViewById(R.id.llrating);
        layout_gpage = v.findViewById(R.id.llgooglepage);
        layout_website = v.findViewById(R.id.llwebsite);
        text_address= v.findViewById(R.id.tvaddress);
        text_number= v.findViewById(R.id.tvnumber);
        text_price= v.findViewById(R.id.tvprice);

        text_gpage= v.findViewById(R.id.tvgooglepage);
        text_website= v.findViewById(R.id.tvwebsite);
        star_ratings=v.findViewById(R.id.ratingstar);
        info=information;
        try {
            reader = new JSONObject(info);
            result=reader.getJSONObject("result");
            Log.d("ADDRESS",result.getString("formatted_address"));
            Log.d("add","i have reached here");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(result.getString("formatted_address").trim().length()!=0){
            layout_address.setVisibility(View.VISIBLE);
            text_address.setText(result.getString("formatted_address"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(result.getString("international_phone_number").trim().length()!=0){
                layout_number.setVisibility(View.VISIBLE);
                text_number.setText(result.getString("international_phone_number"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(result.getString("price_level").trim().length()!=0){
                layout_price.setVisibility(View.VISIBLE);
                int price=Integer.parseInt(result.getString("price_level").trim());
                String str = "$";
                String repeated = new String(new char[price]).replace("\0", str);
                text_price.setText(repeated);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(result.getString("rating").trim().length()!=0){
                layout_rating.setVisibility(View.VISIBLE);
                Float rate=Float.parseFloat(result.getString("rating"));

                star_ratings.setRating(rate);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(result.getString("url").trim().length()!=0){
                layout_gpage.setVisibility(View.VISIBLE);
                text_gpage.setText(result.getString("url"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(result.getString("website").trim().length()!=0){
                layout_website.setVisibility(View.VISIBLE);
                text_website.setText(result.getString("website"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }
}