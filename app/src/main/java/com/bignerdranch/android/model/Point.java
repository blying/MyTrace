package com.bignerdranch.android.model;

import java.util.List;

/**
 * Created by bly on 2016/11/23.
 */
public class Point {
    private String traceId;
    private double latitude;//纬度
    private double longitude;//经度
    private String textString;
    private List<String>photos;
    private String voice;
    public Point(double latitude , double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Point(String traceId,double latitude, double longitude, String textString, List<String> photos, String voice) {
        this.traceId=traceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.textString = textString;
        this.photos = photos;
        this.voice = voice;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
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
