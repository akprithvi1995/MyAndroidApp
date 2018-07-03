package com.example.slayerevenge.travelandentertainment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table favourites " +
                        "(id integer primary key, name text,address text,icon text, placeId text)"
        );
    }

    private static final String DATABASE_NAME = "DBName";

    private static final int DATABASE_VERSION = 2;

    public boolean appendfavourites (String name, String address, String icon, String placeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("icon", icon);
        contentValues.put("placeId", placeId);
//        contentValues.put("Lat", Lat);
//        contentValues.put("Lng", Lng);
        db.insert("favourites", null, contentValues);

        return true;
    }


    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public ArrayList<PlaceData> getAllFavs() {
        ArrayList<PlaceData> array_list = new ArrayList<>();
        PlaceData myPlaces;
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from favourites", null );
        res.moveToFirst();
//        Log.d(TAG,"No of favs: "+res.getCount());
        while(res.isAfterLast() == false){
            myPlaces = new PlaceData();
            myPlaces.setName(res.getString(res.getColumnIndex("name")));
            myPlaces.setPlaceid(res.getString(res.getColumnIndex("placeId")));
            myPlaces.setVicinity(res.getString(res.getColumnIndex("address")));
            myPlaces.setIcon(res.getString(res.getColumnIndex("icon")));
//            myPlaces.setLat(res.getDouble(res.getColumnIndex("Lat")));
//            myPlaces.setLng(res.getDouble(res.getColumnIndex("Lng")));
            array_list.add(myPlaces);
            res.moveToNext();
        }
        return array_list;
    }
//    public ArrayList<ArrayList<String>> getAllFavs() {
//        ArrayList<ArrayList<String>> array_list = new ArrayList();
//        ArrayList<String> name = new ArrayList<>();
//        ArrayList<String> placeid = new ArrayList<>();
//        ArrayList<String> address = new ArrayList<>();
//        ArrayList<String> icons = new ArrayList<>();
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from favourites", null );
//        res.moveToFirst();
//        while(res.isAfterLast() == false){
////            myPlaces = new MyPlaces();
////            myPlaces.setName(res.getString(res.getColumnIndex("name")));
////            myPlaces.setPlaceID(res.getString(res.getColumnIndex("placeId")));
////            myPlaces.setAddress(res.getString(res.getColumnIndex("address")));
////            myPlaces.setIcon(res.getString(res.getColumnIndex("icon")));
////            array_list.add(myPlaces);
//            name.add(res.getString(res.getColumnIndex("name")));
//            placeid.add(res.getString(res.getColumnIndex("placeId")));
//            address.add(res.getString(res.getColumnIndex("address")));
//            icons.add(res.getString(res.getColumnIndex("icons")));
//            res.moveToNext();
//        }
//        array_list.add(name);
//        array_list.add(placeid);
//        array_list.add(address);
//        array_list.add(icons);
//        return array_list;
//    }
    // Method is called during creation of the database
    public Boolean isFav(String Id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from favourites where placeId='"+Id+"'", null );
        res.moveToFirst();

            if (res.getCount()>0)
                return true;
            else
                return false;
    }
    public Integer deleteFav (String placeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("favourites",
                "placeId = ? ",
                new String[] { placeId });
    }


    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(MyDataBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS MyEmployees");
        onCreate(database);
    }
}