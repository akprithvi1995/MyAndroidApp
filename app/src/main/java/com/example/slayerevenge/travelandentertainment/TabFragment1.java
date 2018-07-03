package com.example.slayerevenge.travelandentertainment;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import static com.example.slayerevenge.travelandentertainment.MainActivity.latitude;
import static com.example.slayerevenge.travelandentertainment.MainActivity.longitude;

public class TabFragment1 extends Fragment  {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private EditText keyword;
    private EditText radius;
    private Spinner categories;
    private String url="http://1pi13cs203-env.us-east-2.elasticbeanstalk.com/getplaces?";
    private RadioGroup radiolocations;
    private RadioButton rcurlocation;
    private RadioButton rotherlocation;
    private TextView error1;
    private TextView error2;
    private AutoCompleteTextView lKeyword;
    public static int P_no=0;
    public static List<String> alltok=new ArrayList<>();
    private AutoCompleteTextView autoCompleteTextView;
    public static String location_string;
    private Button allclear;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context cont;
        cont=getActivity();
        String[] state = {"default", "airport", "amusement park", "aquarium", "art gallery", "bakery", "bar", "beauty salon", "bowling alley", "bus station", "cafe", "campground", "car rental", "casino", "lodging", "movie theater", "museum", "night club", "park", "parking", "restaurant", "shopping mall", "stadium", "subway station", "taxi stand", "train station", "transit station", "travel agency", "zoo"};
        View v= inflater.inflate(R.layout.tab_fragment_1, container, false);
        categories= v.findViewById(R.id.spcategory);
        final Button btnRequest;
        autoCompleteTextView = v.findViewById(R.id.autoCompleteTextView);
        CustomAutoCompleteAdapter adapter = new CustomAutoCompleteAdapter(this.getContext());
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(this.onItemClickListener);
        autoCompleteTextView.setEnabled(false);


        keyword= v.findViewById(R.id.etkeyword);
        radius=v.findViewById(R.id.etkeyword1);
        radiolocations=v.findViewById(R.id.rgloc);
        rcurlocation=v.findViewById(R.id.cur_loc);
        rotherlocation=v.findViewById(R.id.spe_loc);
        error1=v.findViewById(R.id.errorkeyword);
        error2=v.findViewById(R.id.errorlocation);
        lKeyword=v.findViewById(R.id.autoCompleteTextView);
        radiolocations.check(R.id.cur_loc);
        radiolocations.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.cur_loc){
                    autoCompleteTextView.setEnabled(false);
                    error2.setVisibility(View.GONE);
                }
                else if(checkedId==R.id.spe_loc){
                    autoCompleteTextView.setEnabled(true);
                }
            }
        });



        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, state);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categories.setAdapter(LTRadapter);

        allclear=v.findViewById(R.id.bClear);
        allclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword.setText("");
                radius.setText("");
                rcurlocation.setChecked(true);
                rotherlocation.setChecked(false);
                autoCompleteTextView.setText("");
                autoCompleteTextView.setEnabled(false);
                error1.setVisibility(View.GONE);
                error2.setVisibility(View.GONE);
                categories.setSelection(0);
            }
        });

        btnRequest = v.findViewById(R.id.bSearch);

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){

                                              sendAndRequestResponse();

                                          }
                                      }

        );



        return v;
    }
    private void sendAndRequestResponse() {

        String selected_category = (String) categories.getSelectedItem();
        String str_keyword = keyword.getText().toString();
        String str_radius = radius.getText().toString();
        location_string="";
        boolean flag1 = false, flag2 = false;
        if (str_radius.trim().length() == 0) {
            str_radius = "10";
        }
        if (str_keyword.trim().length() == 0) {
            error1.setVisibility(View.VISIBLE);
            flag1 = false;

        } else {
            error1.setVisibility(View.GONE);
            flag1 = true;
        }
        if (rcurlocation.isChecked()) {
            error2.setVisibility(View.GONE);
            flag2 = true;
            location_string=latitude+","+longitude;
        } else if (rotherlocation.isChecked()) {
            if (lKeyword.getText().toString().trim().length() == 0) {
                error2.setVisibility(View.VISIBLE);
                flag2 = false;
            } else {
                location_string=lKeyword.getText().toString();
                error2.setVisibility(View.GONE);
                flag2 = true;
            }
        }
        if (flag1 && flag2) {
//        &keyword=pizza&type=default&radius=10&location="+latitude+","+longitude;
            final String tosend="&keyword=" + str_keyword + "&type=" + selected_category + "&radius=" + str_radius + "&location=" + location_string;
            final String request_url = url + "&keyword=" + str_keyword + "&type=" + selected_category + "&radius=" + str_radius + "&location=" + location_string;
            //RequestQueue initialized
            mRequestQueue = Volley.newRequestQueue(getActivity());
            final ProgressDialog progress = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            progress.setCancelable(false);
            progress.setMessage("Fetching Results");
            progress.show();

            //String Request initialized
            mStringRequest = new StringRequest(Request.Method.GET, request_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    alltok.add(tosend);

                    progress.dismiss();
                    Log.d("trying", response.toString());
                    Log.d("url", request_url);
//                Toast.makeText(getActivity().getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                    Intent a = new Intent("com.example.slayerevenge.travelandentertainment.List_Places");
                    a.putExtra("message", response.toString());
                    startActivity(a);//display the response on screen

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    Toast.makeText(getContext(),"No Results to be displayed",Toast.LENGTH_SHORT).show();


                }
            });

            mRequestQueue.add(mStringRequest);
        }
        else{
            Toast.makeText(getContext(),"Please fix all fields with errors",Toast.LENGTH_SHORT).show();
        }
    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppCompatTextView tv =(AppCompatTextView) view;
            autoCompleteTextView.setText(tv.getText().toString());
        }
    };

}
