package com.bignerdranch.android.model;

/**
 * Created by bly on 2016/11/23.
 */
public class Points {
    private double longitude;//经度
    private double latitude;//纬度

    public Points(double latitude , double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

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
}
