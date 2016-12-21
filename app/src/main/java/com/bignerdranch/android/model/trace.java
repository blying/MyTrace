package com.bignerdranch.android.model;

import java.util.List;

/**
 * Created by bly on 2016/12/9.
 */

public class Trace {
    private String traceId;
    private String traceName;
    private String createTime;
    private String personName;
    private String likeNumber;
    private String traceImage;
    private String headPortrait;
    private int type;
    private List<Point> points;

    public Trace(String traceName, List<Point> points, String likeNumber) {
        this.traceName = traceName;
        this.points = points;
        this.likeNumber = likeNumber;
    }

    public Trace(String traceId, String traceName, String createTime, String personName, String likeNumber, String headPortrait, String traceImage, int type, List<Point> points) {
        this.traceId = traceId;
        this.traceName = traceName;
        this.createTime = createTime;
        this.personName = personName;
        this.likeNumber = likeNumber;
        this.headPortrait = headPortrait;
        this.traceImage = traceImage;
        this.type = type;
        this.points = points;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceName() {
        return traceName;
    }

    public void setTraceName(String traceName) {
        this.traceName = traceName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(String likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getTraceImage() {
        return traceImage;
    }

    public void setTraceImage(String traceImage) {
        this.traceImage = traceImage;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
