package com.example.slayerevenge.travelandentertainment;

public class PlaceData {
    private String name, icon, vicinity,placeid;


    public PlaceData() {
    }

    public PlaceData(String name, String icon, String vicinity,String placeid) {
        this.name=name;
        this.icon=icon;
        this.vicinity=vicinity;
        this.placeid=placeid;



    }



    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getPlaceid() {
        return placeid;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}


