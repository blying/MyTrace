package com.bignerdranch.android.model;

import java.util.List;

/**
 * Created by bly on 2016/11/23.
 */
public class RealTimePoint {
    private long loc_time;
    private List<Double> location;
    private int floor;
    private double radius;
    private double speed;
    private int direction;

    public long getLocTime() {
        return loc_time;
    }

    public void setLocTime(long locTime) {
        this.loc_time = locTime;
    }

    public List<Double> getLocationPoint() {
        return location;
    }

    public void setLocationPoint( List<Double> locationPoint) {
        this.location = locationPoint;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return " "+loc_time+" "+location.toString()+" "+floor+" "+radius+" "+ speed+" "+direction;
    }
}
