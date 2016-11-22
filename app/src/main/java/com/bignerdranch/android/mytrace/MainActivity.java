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
import com.baidu.trace.OnTrackListener;

import org.json.JSONException;
import org.json.JSONObject;

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
	long serviceId = 10000;
	// entity标识
	String entityName = "myImei";
	// 是否返回精简结果
	int simpleReturn = 0;
	// 是否纠偏
	int isProcessed = 0;
	// 纠偏选项
	String processOption = null;
	// 开始时间
	int startTime = (int)(System.currentTimeMillis()/1000);
	// 分页大小
	int pageSize = 5000;
	// 分页索引
	int pageIndex = 1;
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
//		initRoadData();
//		moveLooper();
		client=new LBSTraceClient(getApplicationContext());
		queryHistoryTrack();
	}

	private void initRoadData() {
		// init latlng data
		double centerLatitude = 39.916049;
		double centerLontitude = 116.399792;
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

	// 间隔打包周期，轮询调用queryHistoryTrack()
	private void queryHistoryTrack() {
		// 结束时间
		int endTime = (int)(System.currentTimeMillis()/1000);

		OnTrackListener trackListener = new OnTrackListener() {
			@Override
			public void onQueryHistoryTrackCallback(String message) {
				JSONObject data = null;
				try {
					data = new JSONObject(message);
					Log.d(TAG,"data"+data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				JSONObject endPoint = null;
				try {
					endPoint = data. getJSONObject ("end_point");
					Log.d(TAG,"endpoint"+endPoint);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//更新startTime，在当前查询的最后一个点的时间戳上加1，作为下一次查询的开始时间
				try {
					startTime = endPoint.getInt("loc_time") + 1;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// 解析并保存轨迹信息
			}

			@Override
			public void onRequestFailedCallback(String s) {
				Log.d(TAG,"onRequestFailedCallback");
			}
		};
		// 查询新增的轨迹
		client.queryHistoryTrack(serviceId , entityName, simpleReturn, isProcessed,
				processOption, startTime, endTime, pageSize, pageIndex, trackListener);
	}


}
