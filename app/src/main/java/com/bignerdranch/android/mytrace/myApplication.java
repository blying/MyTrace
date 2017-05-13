package com.bignerdranch.android.mytrace;

import android.app.Application;

import com.bignerdranch.android.model.Point;
import com.bignerdranch.android.model.Trace;
import com.bignerdranch.android.model.Traces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bly on 2016/12/9.
 */

public class MyApplication extends Application {
    private Traces mTraces;
    private Trace mTrace;
    private List<Trace> listTrace=new ArrayList<>();
    private Point mPoint;
    private List<Point>points;

    public Traces getTraces() {
        return mTraces;
    }

    public void setTraces(Traces traces) {
        mTraces = traces;
    }

    public List<Trace> getListTrace() {
        return listTrace;
    }

    public void setListTrace(List<Trace> listTrace) {
        this.listTrace = listTrace;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       /*第一组*/
        points=new ArrayList<>();
        mPoint=new Point(46.1123,126.78654123);
        points.add(mPoint);
        mPoint=new Point(46.03040,126.784363);
        points.add(mPoint);
        mPoint=new Point(46.334544,126.650);
        points.add(mPoint);
        mPoint=new Point(46.5623,126.8612);
        points.add(mPoint);
        mPoint=new Point(46.78512,126.38562);
        points.add(mPoint);
        mTrace=new Trace("哈尔滨一日游",points,"129");
        listTrace.add(mTrace);
        /*第二组*/
        points=new ArrayList<>();
        mPoint=new Point(39,116);
        points.add(mPoint);
        mPoint=new Point(39.5,116);
        points.add(mPoint);
        mPoint=new Point(39.7,116);
        points.add(mPoint);
        mPoint=new Point(39.9,116);
        points.add(mPoint);
        mTrace=new Trace("北京一日游",points,"12");
        listTrace.add(mTrace);
       /*第三组*/
        points=new ArrayList<>();
        mPoint=new Point(22,114);
        points.add(mPoint);
        mPoint=new Point(22.5,114);
        points.add(mPoint);
        mPoint=new Point(22.7,114);
        points.add(mPoint);
        mPoint=new Point(22.9,114);
        points.add(mPoint);
        mTrace=new Trace("香港一日游",points,"29");
        listTrace.add(mTrace);
        /*第四组*/
        points=new ArrayList<>();
        mPoint=new Point(34,121);
        points.add(mPoint);
        mPoint=new Point(34.5,121);
        points.add(mPoint);
        mPoint=new Point(34.7,121);
        points.add(mPoint);
        mPoint=new Point(34.9,121);
        points.add(mPoint);
        mTrace=new Trace("上海一日游",points,"300");
        listTrace.add(mTrace);
        /*第五组*/
        points=new ArrayList<>();
        mPoint=new Point(28,113);
        points.add(mPoint);
        mPoint=new Point(28.2,113);
        points.add(mPoint);
        mPoint=new Point(28.7,113);
        points.add(mPoint);
        mPoint=new Point(28.9,113);
        points.add(mPoint);
        mPoint=new Point(28.5,113);
        points.add(mPoint);
        mTrace=new Trace("长沙一日游",points,"129");
        listTrace.add(mTrace);
        /*第六组*/
        points=new ArrayList<>();
        mPoint=new Point(45,126);
        points.add(mPoint);
        mPoint=new Point(45.5,126);
        points.add(mPoint);
        mPoint=new Point(45.7,126);
        points.add(mPoint);
        mPoint=new Point(45.9,126);
        points.add(mPoint);
        mPoint=new Point(46,126);
        points.add(mPoint);
        mTrace=new Trace("哈尔滨一日游",points,"129");
        listTrace.add(mTrace);
        /*第七组*/
        points=new ArrayList<>();
        mPoint=new Point(45,126);
        points.add(mPoint);
        mPoint=new Point(45.5,126);
        points.add(mPoint);
        mPoint=new Point(45.7,126);
        points.add(mPoint);
        mPoint=new Point(45.9,126);
        points.add(mPoint);
        mPoint=new Point(46,126);
        points.add(mPoint);
        mTrace=new Trace("哈尔滨二日游",points,"129");
        listTrace.add(mTrace);
        //添加到轨迹列表中
        mTraces=new Traces(listTrace);
    }
}
