package com.mob.schneiderpersson.friendstracker;

public class UserLocation {
    public Double lat, lon;

    public UserLocation(){}

    public UserLocation(Double lat, Double lon)
    {
        lat = lat;
        lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

}

