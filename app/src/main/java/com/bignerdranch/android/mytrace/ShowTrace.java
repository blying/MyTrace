package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;

/**
 * Created by bly on 2016/11/28.
 */
public class ShowTrace extends Activity {
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trace);
        //mBaiduMap=(BaiduMap)findViewById(R.id.showTraceMap)
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }
}
