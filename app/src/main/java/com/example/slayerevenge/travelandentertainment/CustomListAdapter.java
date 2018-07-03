//package com.example.slayerevenge.travelandentertainment;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.os.Bundle;
//
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.squareup.picasso.Picasso;
//
//import java.io.InputStream;
//
//public class CustomListAdapter extends ArrayAdapter<String> {
//
//    private final Activity context;
//    private final String[] itemname;
//    private final String[] imgid;
//    private static ImageView imageView;
//    private final String[] vicinity;
//    private final String[] placeid;
//    private final Double[] latitude;
//    private final Double[] longitude;
//    private static ImageView fav;
//    private MyDataBaseHelper dataBaseHelper;
//
//    public CustomListAdapter(Activity context, String[] itemname, String[] imgid,String[] vicinity,String placeid[],Double[] latitude,Double[] longitude) {
//        super(context, R.layout.mylist, itemname);
//        // TODO Auto-generated constructor stub
//        this.latitude=latitude;
//        this.longitude=longitude;
//        this.context=context;
//        this.itemname=itemname;
//        this.imgid=imgid;
//        this.vicinity=vicinity;
//        this.placeid=placeid;
//        this.dataBaseHelper= new MyDataBaseHelper(getContext());
//    }
//
//    public View getView(final int position, View view, ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.mylist, null,true);
//
//        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
//        imageView = (ImageView) rowView.findViewById(R.id.icon);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
//        fav=rowView.findViewById(R.id.fav);
//        fav.setImageResource(R.drawable.ic_heart_black);
//        fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dataBaseHelper.isFav(placeid[position])){
//                    dataBaseHelper.deleteFav(placeid[position]);
//                    fav.setImageResource(R.drawable.ic_heart_black);
//                    Toast.makeText(getContext(),"is it here",Toast.LENGTH_LONG).show();
//
//                }
//                else{
//                    dataBaseHelper.appendfavourites(itemname[position],vicinity[position],imgid[position],placeid[position]);
//                    fav.setImageResource(R.drawable.heart_red);
//                    Toast.makeText(getContext(),"is it face",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        txtTitle.setText(itemname[position]);
//        Picasso.with(context).load(imgid[position]).into(imageView);
//        txtTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View viewIn) {
//                Intent a = new Intent("com.example.slayerevenge.travelandentertainment.details");
//                a.putExtra("placeid", placeid[position]);
//                a.putExtra("name",itemname[position]);
//                a.putExtra("lat",latitude[position]);
//                a.putExtra("lng",longitude[position]);
//                a.putExtra("placeid",placeid[position]);
//                Log.d("pid",placeid[position]);
//                context.startActivity(a);
//
//            }
//        });
//        extratxt.setText(vicinity[position]);
//        return rowView;
//
//    };
//
//
//}
