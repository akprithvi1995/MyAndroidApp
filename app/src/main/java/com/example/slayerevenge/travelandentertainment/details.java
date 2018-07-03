package com.example.slayerevenge.travelandentertainment;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class details extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static  Double lat,lng;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String request_url;
    public static String place_id;
    public static String information;
    private JSONObject reader;
    private JSONObject result;
    public static String nametab;
    private String vicinity;
    private String icon;
    private MenuItem favourites;
    private MenuItem twitter;
    private MyDataBaseHelper myDataBaseHelper;
    private Menu menu;
    private String twitterinfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myDataBaseHelper=new MyDataBaseHelper(this);
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getDouble("lat");
        lng = bundle.getDouble("lng");
        nametab=bundle.getString("name");
        icon=bundle.getString("icon");
        vicinity=bundle.getString("vicinity");
        place_id=bundle.getString("placeid");

//                window.open("https://twitter.com/share?url=https%3A%2F%2Fdev.twitter.com%2Fweb%2Ftweet-button&via=twitterdev&related=twitterapi%2Ctwitter&text="+twitter,'_blank','width=500,height=500');

        Log.d("craylat",lat.toString());
        Log.d("craylng",lng.toString());

        Log.d("here","did it load");
        toolbar = (Toolbar) findViewById(R.id.secondtoolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(nametab);



        request_url="http://1pi13cs203-env.us-east-2.elasticbeanstalk.com/getdetails?placeId="+place_id;
        Log.d("ass",request_url);
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, request_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name="";
                information=response.toString();
                Log.d("info",information);
                try {
                    reader = new JSONObject(information);
                    result=reader.getJSONObject("result");
                    lat=result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    lng=result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                    name=result.getString("name");
                    twitterinfo="Check out "+name+" located at "+result.getString("formatted_address")+" .Website: "+result.getString("website");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                createinfo();

            }

            private void createinfo() {
                viewPager = (ViewPager) findViewById(R.id.secondviewpager);
                DetailsPagerAdapter adapter = new DetailsPagerAdapter(getSupportFragmentManager(),details.this);
                viewPager.setAdapter(adapter);

                tabLayout = (TabLayout) findViewById(R.id.secondtabs);
                tabLayout.setupWithViewPager(viewPager);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        mRequestQueue.add(mStringRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)

    {
        getMenuInflater().inflate(R.menu.menu_details,menu);
        this.menu = menu;
        MenuItem item =menu.findItem(R.id.heart);
        if(myDataBaseHelper.isFav(place_id)){
            item.setIcon(R.drawable.ic_heart_filled);
        }
        else{
            item.setIcon(R.drawable.heart_outline);
        }

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }
    private void updateMenuItems(Menu menu){

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        MyDataBaseHelper myDataBaseHelper=new MyDataBaseHelper(this);
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if(item.getItemId()==R.id.heart)
        {

            if (myDataBaseHelper.isFav(place_id)){
                myDataBaseHelper.deleteFav(place_id);

                item.setIcon(R.drawable.heart_outline);
                Toast.makeText(this,nametab+"was removed from favourites",Toast.LENGTH_SHORT).show();
//

            }
            else{
                myDataBaseHelper.appendfavourites(nametab,vicinity,icon,place_id);
                item.setIcon(R.drawable.ic_heart_filled);
                Toast.makeText(this,nametab+"was added to favourites",Toast.LENGTH_SHORT).show();
            }

        }
        if(item.getItemId()==R.id.share)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/share?url=https%3A%2F%2Fdev.twitter.com%2Fweb%2Ftweet-button&via=twitterdev&related=twitterapi%2Ctwitter&text="+twitterinfo));
            startActivity(browserIntent);


        }
        return super.onOptionsItemSelected(item);
    }

//    public String getResponse()
////    {
////        return info;
////    }
}
