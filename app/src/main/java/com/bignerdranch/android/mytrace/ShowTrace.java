package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bignerdranch.android.model.Point;

import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_PARENT;
import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.bignerdranch.android.mytrace.R.id.showTraceMap;

/**
 * Created by bly on 2016/11/28.
 */
public class ShowTrace extends Activity {
    private final static String TAG="ShowTrace";
    private Animation myAnimation_Translate;//动画
    private Animation myAnimation;
    private Animation myAnimation1;
    private MapView mMapView;
    private RelativeLayout showLayout;
    private Button closeButton;
    private BaiduMap mBaiduMap;
    int current;
    int size;
    private Marker mMarker[];
    WindowManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_show_trace);
        wm = this.getWindowManager();//获取屏幕高度和宽度
        showLayout=(RelativeLayout)findViewById(R.id.showLayout);
        showLayout.setVisibility(View.GONE);
        closeButton=(Button)findViewById(R.id.closeButton);
        //mBaiduMap=(BaiduMap)findViewById(R.id.showTraceMap)
        mMapView = (MapView) findViewById(showTraceMap);
        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();
        if (getIntent().getExtras().get("number")!=null) {
            current = (int) getIntent().getExtras().get("number");
              MyApplication myApplication=(MyApplication)this.getApplicationContext();
               markPoints(myApplication.getListTrace().get(current).getPoints());
            size=myApplication.getListTrace().get(current).getPoints().size();
        }
        //动画
        myAnimation_Translate = new TranslateAnimation(
                RELATIVE_TO_PARENT, 0,
                RELATIVE_TO_PARENT, 0,
                RELATIVE_TO_SELF, 1,
                RELATIVE_TO_PARENT, 0);
        myAnimation_Translate.setDuration(500);//设置动画持续时间
        myAnimation_Translate.setRepeatMode(Animation.REVERSE);//设置反方向执行
        myAnimation_Translate.setInterpolator(AnimationUtils
                .loadInterpolator(ShowTrace.this,
                        android.R.anim.accelerate_decelerate_interpolator));


        myAnimation= new TranslateAnimation(
                0,
                0,
               - wm.getDefaultDisplay().getHeight()/12*5,
               0 );
        myAnimation.setDuration(500);//设置动画持续时间
        myAnimation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        myAnimation.setInterpolator(AnimationUtils
                .loadInterpolator(ShowTrace.this,
                        android.R.anim.accelerate_decelerate_interpolator));

        myAnimation1= new TranslateAnimation(
                0,
                0,
                0,
                wm.getDefaultDisplay().getHeight()/12*5);
        myAnimation1.setDuration(500);//设置动画持续时间
        myAnimation1.setRepeatMode(Animation.REVERSE);//设置反方向执行
        myAnimation1.setInterpolator(AnimationUtils
                .loadInterpolator(ShowTrace.this,
                        android.R.anim.accelerate_decelerate_interpolator));

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.startAnimation(myAnimation);
               showLayout.startAnimation(myAnimation1);
                showLayout.setVisibility(View.GONE);
            }
        });

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i=0;i<size;i++) {
                    if (marker == mMarker[i])
                        Toast.makeText(ShowTrace.this, "mark" + i + " 被点击！", Toast.LENGTH_LONG).show();

                   showLayout.startAnimation(myAnimation_Translate);
                    showLayout.setVisibility(View.VISIBLE);
                    updataMapView();


                }
                return false;
            }
        });
    }

    /*
     *在地图上标注所有点 并将所有点显示在屏幕中，设置缩放级别
	 */
    public void markPoints(List<Point>points) {//	LatLng point = new LatLng(45.963175, 126.400244);//定义Maker坐标点
        mMarker=new Marker[points.size()];
        //构建Marker图标
        View view= LayoutInflater.from(ShowTrace.this).inflate(R.layout.pin_view,null);
        TextView number=(TextView)view.findViewById(R.id.pinNumber);

        //构建MarkerOption，用于在地图上添加Marker
        for (int i=0;i<points.size();i++) {
            number.setText(i+1+"");
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
            LatLng point = new LatLng(points.get(i).getLatitude(), points.get(i).getLongitude());
           // OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
            MarkerOptions option= new MarkerOptions().position(point).icon(bitmap).zIndex(9).draggable(false);//.extraInfo();
            //在地图上添加Marker，并显示
            //mBaiduMap.addOverlay(option);
            mMarker[i]=(Marker)(mBaiduMap.addOverlay(option));

        }
        Log.d(TAG,"test points "+points.toString());
        setMapView(getMiddle(points),setZoom(points));
    }
  public void updataMapView(){
      final MyApplication myApplication=(MyApplication)this.getApplicationContext();
      setMapView(getMiddle(myApplication.getListTrace().get(current).getPoints()),setZoom(myApplication.getListTrace().get(current).getPoints()));
    }

    /**
     * 计算所有点的中心位置点
     * 求出最大经纬度和最小经纬度 取中
     */
    public LatLng getMiddle(List<Point>points){

        double maxLatitude=0.0;
        double minLatitude=90.0;
        double maxLongitude=0.0;
        double minLongitude=180.0;
        for (int i=0;i<points.size();i++){
            if (points.get(i).getLatitude()>maxLatitude){
                maxLatitude=points.get(i).getLatitude();
            }
            if (points.get(i).getLatitude()<minLatitude){
                minLatitude=points.get(i).getLatitude();
            }
            if (points.get(i).getLongitude()>maxLongitude){
                maxLongitude=points.get(i).getLongitude();
            }
            if (points.get(i).getLongitude()<minLongitude){
                minLongitude=points.get(i).getLongitude();
            }
        }
        Log.d(TAG,"distance max and min "+maxLongitude+" "+minLongitude+" "+maxLatitude+" "+minLatitude);
        Log.d(TAG,"distance middle point "+Double.valueOf((maxLatitude+minLatitude)/2.0)+"  "+Double.valueOf((maxLongitude+minLongitude)/2.0));
        return new LatLng(Double.valueOf((maxLatitude+minLatitude)/2.0),Double.valueOf(maxLongitude+minLongitude)/2.0);
    }

    /**
     * 设置缩放级别
     * 按最远两点的距离
     */
    public int setZoom(List<Point>points ){

        double maxLatitude=0.0;
        double minLatitude=90.0;
        double maxLongitude=0.0;
        double minLongitude=180.0;
        for (int i=0;i<points.size();i++){
            if (points.get(i).getLatitude()>maxLatitude){
                maxLatitude=points.get(i).getLatitude();
            }
            if (points.get(i).getLatitude()<minLatitude){
                minLatitude=points.get(i).getLatitude();
            }
            if (points.get(i).getLongitude()>maxLongitude){
                maxLongitude=points.get(i).getLongitude();
            }
            if (points.get(i).getLongitude()<minLongitude){
                minLongitude=points.get(i).getLongitude();
            }
        }
        int zoom = 0;
        LatLng pointA=new LatLng(maxLatitude,maxLongitude);
        LatLng pointB=new LatLng(minLatitude,minLongitude);
        double distance= DistanceUtil.getDistance(pointA,pointB);
        Log.d(TAG,"distance="+distance);
        if(distance<10)zoom=20;
        if (distance>=10&&distance<20)zoom=20;
        if (distance>=20&&distance<50)zoom=20;
        if (distance>=50&&distance<100)zoom=20;
        if (distance>=100&&distance<200)zoom=20;
        if (distance>=200&&distance<500)zoom=19;
        if (distance>=500&&distance<1000)zoom=18;
        if (distance>=1000&&distance<2000)zoom=17;
        if (distance>=2000&&distance<5000)zoom=16;
        if (distance>=5000&&distance<10000)zoom=15;
        if (distance>=10000&&distance<20000)zoom=14;
        if (distance>=20000&&distance<25000)zoom=13;
        if (distance>=25000&&distance<50000)zoom=12;
        if (distance>=50000&&distance<100000)zoom=11;
        if (distance>=100000&&distance<200000)zoom=10;
        if (distance>=200000&&distance<500000)zoom=9;
        if (distance>=500000&&distance<1000000)zoom=8;
        if (distance>=1000000&&distance<2000000)zoom=7;
        if (distance>=2000000)zoom=6;
        Log.d(TAG,"distance make zoom ="+zoom);
        return zoom;
    }


    /*
    *设定中心点
    */
    public void setMapView(LatLng point,int zoom) {//设定中心点坐标 
//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.mark);
//        OverlayOptions options = new MarkerOptions().icon(icon).position(point);
//        mBaiduMap.addOverlay(options);
        //定义地图状态  
        MapStatus mMapStatus = new MapStatus.Builder().target(point)
                .zoom(zoom)//地图大小
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化  
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态  
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }
}
