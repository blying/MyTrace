package com.bignerdranch.android.model;

import java.util.List;

/**
 * Created by bly on 2016/11/23.
 */
public class LocationResult {
    private  int status;
    private int size;
    private int total;
    private String entity_name;
    private double distance;
    private double toll_distance;
    private StartPoint start_point;
    private EndPoint end_point;
    private List<List<Double>>points;

    public LocationResult(int status, int size, int total, String entity_name, double distance, double toll_distance, StartPoint start_point, EndPoint end_point, List<List<Double>> points) {
        this.status = status;
        this.size = size;
        this.total = total;
        this.entity_name = entity_name;
        this.distance = distance;
        this.toll_distance = toll_distance;
        this.start_point = start_point;
        this.end_point = end_point;
        this.points = points;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public EndPoint getEnd_point() {
        return end_point;
    }

    public void setEnd_point(EndPoint end_point) {
        this.end_point = end_point;
    }

    public StartPoint getStart_point() {
        return start_point;
    }

    public void setStart_point(StartPoint start_point) {
        this.start_point = start_point;
    }

    public   List<List<Double>> getPoints() {
        return points;
    }

    public void setPoints( List<List<Double>> points) {
        this.points = points;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(byte total) {
        this.total = total;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getToll_distance() {
        return toll_distance;
    }

    public void setToll_distance(double toll_distance) {
        this.toll_distance = toll_distance;
    }



    @Override
    public String toString() {
        return " "+ status+" "+size+" "+total+" "+start_point.toString()+" "+points.toString();
    }

}
