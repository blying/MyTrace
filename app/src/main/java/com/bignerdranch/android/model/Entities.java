package com.bignerdranch.android.model;

/**
 * Created by bly on 2016/11/23.
 */
public class Entities {
    private String entity_name;
    private String create_time;
    private String modify_time;
    private RealTimePoint realtime_point;

    public String getEntityName() {
        return entity_name;
    }

    public void setEntityName(String entityName) {
        this.entity_name = entityName;
    }

    public String getCreateTime() {
        return create_time;
    }

    public void setCreateTime(String createTime) {
        this.create_time = createTime;
    }

    public String getModifyTime() {
        return modify_time;
    }

    public void setModifyTime(String modifyTime) {
        this.modify_time = modifyTime;
    }

    public RealTimePoint getRealTimePoints() {
        return realtime_point;
    }

    public void setRealTimePoints(RealTimePoint realTimePoints) {
        this.realtime_point = realTimePoints;
    }

    @Override
    public String toString() {
        return " "+entity_name+" "+create_time+" "+modify_time+" "+realtime_point.toString();
    }
}
