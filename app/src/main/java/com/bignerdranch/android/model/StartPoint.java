package com.bignerdranch.android.model;

/**
 * Created by bly on 2016/11/23.
 */
public class StartPoint {
    private double longitude;
    private double latitude;
    private int coord_type;
    private long loc_time;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(int coord_type) {
        this.coord_type = coord_type;
    }

    public long getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(long loc_time) {
        this.loc_time = loc_time;
    }

    @Override
    public String toString() {
        return " "+longitude+" "+latitude+" "+coord_type+" "+loc_time;
    }
}
