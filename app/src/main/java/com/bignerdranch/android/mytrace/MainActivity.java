package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.OnTrackListener;
import com.bignerdranch.android.model.LocationResult;
import com.bignerdranch.android.model.Result;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
	private static final String TAG="MainActivity";

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private Polyline mVirtureRoad;
	private Marker mMoveMarker;
    private Handler mHandler;

	// 通过设置间隔时间和距离可以控制速度和图标移动的距离
	private static final int TIME_INTERVAL = 80;
	private static final double DISTANCE = 0.0001;

	// 轨迹服务ID
    long serviceId = 129306;
	public static LBSTraceClient client = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		mMapView = (MapView) findViewById(R.id.bmapView);

		mMapView.onCreate(this, savedInstanceState);
		mBaiduMap = mMapView.getMap();
        mHandler = new Handler(Looper.getMainLooper());
		//initRoadData();
//		moveLooper();
		client=new LBSTraceClient(getApplicationContext());
		//queryHistoryTrack();
		findLocationAtTime();
		findLocationOnHistory();
	}

	private void initRoadData() {
		// init latlng data
		double centerLatitude = 126.6596070827;// 126.6596070827;
		double centerLontitude = 45.726225878191;//45.726225878191;
		double deltaAngle = Math.PI / 180 * 5;
		double radius = 0.02;
		OverlayOptions polylineOptions;

        List<LatLng> polylines = new ArrayList<LatLng>();
		for (double i = 0; i < Math.PI * 2; i = i + deltaAngle) {
			float latitude = (float) (-Math.cos(i) * radius + centerLatitude);
			float longtitude = (float) (Math.sin(i) * radius + centerLontitude);
            polylines.add(new LatLng(latitude, longtitude));
			if (i > Math.PI) {
				deltaAngle = Math.PI / 180 * 30;
			}
		}

		float latitude = (float) (-Math.cos(0) * radius + centerLatitude);
		float longtitude = (float) (Math.sin(0) * radius + centerLontitude);
        polylines.add(new LatLng(latitude, longtitude));

        polylineOptions = new PolylineOptions().points(polylines).width(10).color(Color.RED);

		mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
		OverlayOptions markerOptions;
        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory
                .fromResource(R.mipmap.mark)).position(polylines.get(0)).rotate((float) getAngle(0));
		mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);

	}

	/**
	 * 根据点获取图标转的角度
	 */
	private double getAngle(int startIndex) {
		if ((startIndex + 1) >= mVirtureRoad.getPoints().size()) {
			throw new RuntimeException("index out of bonds");
		}
		LatLng startPoint = mVirtureRoad.getPoints().get(startIndex);
		LatLng endPoint = mVirtureRoad.getPoints().get(startIndex + 1);
		return getAngle(startPoint, endPoint);
	}

	/**
	 * 根据两点算取图标转的角度
	 */
	private double getAngle(LatLng fromPoint, LatLng toPoint) {
		double slope = getSlope(fromPoint, toPoint);
		if (slope == Double.MAX_VALUE) {
			if (toPoint.latitude > fromPoint.latitude) {
				return 0;
			} else {
				return 180;
			}
		}
		float deltAngle = 0;
		if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
			deltAngle = 180;
		}
		double radio = Math.atan(slope);
		double angle = 180 * (radio / Math.PI) + deltAngle - 90;
		return angle;
	}

	/**
	 * 根据点和斜率算取截距
	 */
	private double getInterception(double slope, LatLng point) {

		double interception = point.latitude - slope * point.longitude;
		return interception;
	}

	/**
	 * 算取斜率
	 */
	private double getSlope(int startIndex) {
		if ((startIndex + 1) >= mVirtureRoad.getPoints().size()) {
			throw new RuntimeException("index out of bonds");
		}
		LatLng startPoint = mVirtureRoad.getPoints().get(startIndex);
		LatLng endPoint = mVirtureRoad.getPoints().get(startIndex + 1);
		return getSlope(startPoint, endPoint);
	}

	/**
	 * 算斜率
	 */
	private double getSlope(LatLng fromPoint, LatLng toPoint) {
		if (toPoint.longitude == fromPoint.longitude) {
			return Double.MAX_VALUE;
		}
		double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
		return slope;

	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
        mBaiduMap.clear();
	}

	/**
	 * 计算x方向每次移动的距离
	 */
	private double getXMoveDistance(double slope) {
		if (slope == Double.MAX_VALUE) {
			return DISTANCE;
		}
		return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
	}

	/**
	 * 循环进行移动逻辑
	 */
	public void moveLooper() {
		new Thread() {

			public void run() {
				while (true) {

					for (int i = 0; i < mVirtureRoad.getPoints().size() - 1; i++) {

						final LatLng startPoint = mVirtureRoad.getPoints().get(i);
						final LatLng endPoint = mVirtureRoad.getPoints().get(i + 1);
						mMoveMarker
						.setPosition(startPoint);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // refresh marker's rotate
                                if (mMapView == null) {
                                    return;
                                }
                                mMoveMarker.setRotate((float) getAngle(startPoint,
								endPoint));
                            }
                        });
						double slope = getSlope(startPoint, endPoint);
						//是不是正向的标示（向上设为正向）
						boolean isReverse = (startPoint.latitude > endPoint.latitude);

						double intercept = getInterception(slope, startPoint);

						double xMoveDistance = isReverse ? getXMoveDistance(slope)
								: -1 * getXMoveDistance(slope);


						for (double j = startPoint.latitude;
								!((j > endPoint.latitude)^ isReverse);

								j = j
								- xMoveDistance) {
							LatLng latLng = null;
							if (slope != Double.MAX_VALUE) {
								latLng = new LatLng(j, (j - intercept) / slope);
							} else {
								latLng = new LatLng(j, startPoint.longitude);
							}

                            final LatLng finalLatLng = latLng;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (mMapView == null) {
                                        return;
                                    }
                                    // refresh marker's position
                                    mMoveMarker.setPosition(finalLatLng);
                                }
                            });
							try {
								Thread.sleep(TIME_INTERVAL);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					}
				}
			}

		}.start();
	}

	/**
	 * 鹰眼查询历史轨迹
	 */
	private void findLocationOnHistory() {
		//entity标识
		String entityName = "phone";
		//是否返回精简的结果（0 : 将只返回经纬度，1 : 将返回经纬度及其他属性信息）
		int simpleReturn = 1;
		//开始时间（Unix时间戳）
		final int startTime = (int) (System.currentTimeMillis() / 1000 - 12 * 60 * 60);
		//结束时间（Unix时间戳）
		final int endTime = (int) (System.currentTimeMillis() / 1000);
		//分页大小
		int pageSize = 1000;
		//分页索引
		int pageIndex = 1;
		//轨迹查询监听器
		OnTrackListener trackListener = new OnTrackListener() {
			//请求失败回调接口
			@Override
			public void onRequestFailedCallback(String arg0) {
				Log.i(TAG, "onRequestFailedCallback" + "arg0 = " + arg0);
			}

			// 查询历史轨迹回调接口
			@Override
			public void onQueryHistoryTrackCallback(String arg0) {
				Log.i(TAG, "onQueryHistoryTrackCallback" + "arg0 = " + arg0);
				//解json
				Gson gson=new Gson();//GsonBuilder().serializeNulls().create()
				LocationResult locationResult=gson.fromJson(arg0,LocationResult.class);
				Log.d(TAG,"onQueryHistoryTrackCallback  "+locationResult.toString());

			}

		};

		//查询历史轨迹
		client.queryHistoryTrack(serviceId, entityName, simpleReturn, startTime, endTime,
				pageSize, pageIndex, trackListener);

	}
	/**
	 * 鹰眼查询实时位置
	 */
	private void findLocationAtTime() {
		//entity标识列表（多个entityName，以英文逗号"," 分割）
		String entityNames = "phone";
		//检索条件（格式为 : "key1=value1,key2=value2,....."）
		String columnKey = "";
		//返回结果的类型（0 : 返回全部结果，1 : 只返回entityName的列表）
		final int returnType = 0;
		//活跃时间，UNIX时间戳（指定该字段时，返回从该时间点之后仍有位置变动的entity的实时点集合）
		int activeTime = (int) (System.currentTimeMillis() / 1000 - 12 * 60 * 60);
		//分页大小
		int pageSize = 1000;
		//分页索引
		int pageIndex = 1;
		//Entity监听器
		OnEntityListener entityListener = new OnEntityListener() {
			// 查询失败回调接口
			@Override
			public void onRequestFailedCallback(String arg0) {
				Log.i(TAG, "onRequestFailedCallback" + "arg0 = " + arg0);

			}

			// 查询entity回调接口，返回查询结果列表
			@Override
			public void onQueryEntityListCallback(String arg0) {
				//位置坐标
				Log.i(TAG, "onQueryEntityListCallback" + " arg0 = " + arg0);

				Gson gson=new Gson() ;//GsonBuilder().serializeNulls().create()
				Log.d(TAG,"onQueryEntityListCallback "+"here");
				Result result=gson.fromJson(arg0,Result.class);
				Log.d(TAG,"onQueryEntityListCallback "+result.toString());

			}
		};



		//查询实时轨迹
		client.queryEntityList(serviceId, entityNames, columnKey, returnType, activeTime, pageSize,
				pageIndex, entityListener);
	}

}
