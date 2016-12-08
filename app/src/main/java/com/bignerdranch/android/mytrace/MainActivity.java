package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
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
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.bignerdranch.android.model.Points;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final int TIME_INTERVAL = 80;
    private static final double DISTANCE = 0.0001;
    //定位sdk
    public LocationClient mLocationClient = null;
    public MyLocationListener myListener = new MyLocationListener();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Polyline mVirtureRoad;


    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private Marker mMoveMarker;
    //    private Handler mHandler;
    private Button mAddButton;
    // 轨迹服务ID
//    long serviceId = 129914;
//	public static LBSTraceClient client = null;
    private List<Points> mPointsList = new ArrayList<>();
    private Points mPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mAddButton = (Button) findViewById(R.id.addButton);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();
//        mHandler = new Handler(Looper.getMainLooper());
//		client=new LBSTraceClient(getApplicationContext());
//		findLocationOnHistory();//查询历史轨迹
//		findLocationAtTime();

        mAddButton.setOnClickListener(this);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
        Log.d(TAG, "oncreate");
//        TypedArray ar = this.getResources().obtainTypedArray(R.array.markpicture);
//        int len = ar.length();
//        int[] resIds = new int[len];
//        for (int i = 0; i < len; i++)
//            resIds[i] = ar.getResourceId(i, 0);
//        ar.recycle();
    }

//	private void initRoadData() {
//		// init latlng data
//		double centerLatitude =45.726225878191;// 126.6596070827;
//		double centerLontitude =126.6596070827;//45.726225878191;//
//		double deltaAngle = Math.PI / 180 * 5;
//		double radius = 0.02;
//		OverlayOptions polylineOptions;
//		List<LatLng>polylines=new ArrayList<>();
//
////这是轨迹点
//
//		for (double i = 0; i < Math.PI * 2; i = i + deltaAngle) {
//			float latitude = (float) (-Math.cos(i) * radius + centerLatitude);
//			float longtitude = (float) (Math.sin(i) * radius + centerLontitude);
//            polylines.add(new LatLng(latitude, longtitude));
//			if (i > Math.PI) {
//				deltaAngle = Math.PI / 180 * 30;
//			}
//		}
//        polylineOptions = new PolylineOptions().points(polylines).width(10).color(Color.RED);
//
//		mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
//		OverlayOptions markerOptions;
//        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory
//                .fromResource(R.mipmap.icon_marka)).position(polylines.get(0)).rotate((float) getAngle(0));
//		mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
//
//	}

//	/**
//	 * 根据点获取图标转的角度
//	 */
//	private double getAngle(int startIndex) {
//		if ((startIndex + 1) >= mVirtureRoad.getPoints().size()) {
//			throw new RuntimeException("index out of bonds");
//		}
//		LatLng startPoint = mVirtureRoad.getPoints().get(startIndex);
//		LatLng endPoint = mVirtureRoad.getPoints().get(startIndex + 1);
//		return getAngle(startPoint, endPoint);
//	}
//
//	/**
//	 * 根据两点算取图标转的角度
//	 */
//	private double getAngle(LatLng fromPoint, LatLng toPoint) {
//		double slope = getSlope(fromPoint, toPoint);
//		if (slope == Double.MAX_VALUE) {
//			if (toPoint.latitude > fromPoint.latitude) {
//				return 0;
//			} else {
//				return 180;
//			}
//		}
//		float deltAngle = 0;
//		if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
//			deltAngle = 180;
//		}
//		double radio = Math.atan(slope);
//		double angle = 180 * (radio / Math.PI) + deltAngle - 90;
//		return angle;
//	}
//
//	/**
//	 * 根据点和斜率算取截距
//	 */
//	private double getInterception(double slope, LatLng point) {
//
//		double interception = point.latitude - slope * point.longitude;
//		return interception;
//	}
//
//	/**
//	 * 算取斜率
//	 */
//	private double getSlope(int startIndex) {
//		if ((startIndex + 1) >= mVirtureRoad.getPoints().size()) {
//			throw new RuntimeException("index out of bonds");
//		}
//		LatLng startPoint = mVirtureRoad.getPoints().get(startIndex);
//		LatLng endPoint = mVirtureRoad.getPoints().get(startIndex + 1);
//		return getSlope(startPoint, endPoint);
//	}
//
//	/**
//	 * 算斜率
//	 */
//	private double getSlope(LatLng fromPoint, LatLng toPoint) {
//		if (toPoint.longitude == fromPoint.longitude) {
//			return Double.MAX_VALUE;
//		}
//		double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
//		return slope;
//
//	}

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

//	/**
//	 * 计算x方向每次移动的距离
//	 */
//	private double getXMoveDistance(double slope) {
//		if (slope == Double.MAX_VALUE) {
//			return DISTANCE;
//		}
//		return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
//	}
//
//	/**
//	 * 循环进行移动逻辑 //画轨迹 历史轨迹
//	 */
//	public void moveLooper() {
//		new Thread() {
//
//			public void run() {
//					for (int i = 0; i < mVirtureRoad.getPoints().size() - 1; i++) {
//
//						final LatLng startPoint = mVirtureRoad.getPoints().get(i);
//						final LatLng endPoint = mVirtureRoad.getPoints().get(i + 1);
//						mMoveMarker.setPosition(startPoint);
//
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                // refresh marker's rotate
//                                if (mMapView == null) {
//                                    return;
//                                }
//                                mMoveMarker.setRotate((float) getAngle(startPoint,
//								endPoint));
//                            }
//                        });
//						double slope = getSlope(startPoint, endPoint);
//						//是不是正向的标示（向上设为正向）
//						boolean isReverse = (startPoint.latitude > endPoint.latitude);
//
//						double intercept = getInterception(slope, startPoint);
//
//						double xMoveDistance = isReverse ? getXMoveDistance(slope)
//								: -1 * getXMoveDistance(slope);
//
//
//						for (double j = startPoint.latitude;
//								!((j > endPoint.latitude)^ isReverse);
//
//								j = j
//								- xMoveDistance) {
//							LatLng latLng = null;
//							if (slope != Double.MAX_VALUE) {
//								latLng = new LatLng(j, (j - intercept) / slope);
//							} else {
//								latLng = new LatLng(j, startPoint.longitude);
//							}
//
//                            final LatLng finalLatLng = latLng;
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (mMapView == null) {
//                                        return;
//                                    }
//                                    // refresh marker's position
//                                    mMoveMarker.setPosition(finalLatLng);
//                                }
//                            });
//							try {
//								Thread.sleep(TIME_INTERVAL);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//
//					}
//				}
//
//
//		}.start();
//	}
//
//	/**
//	 * 鹰眼查询历史轨迹
//	 */
//	private void findLocationOnHistory() {
//		//entity标识
//		String entityName = "phone";
//		//是否返回精简的结果（0 : 将只返回经纬度，1 : 将返回经纬度及其他属性信息）
//		int simpleReturn = 0;
//		//开始时间（Unix时间戳）
//		final int startTime = (int) (System.currentTimeMillis() / 1000 - 12 * 60 * 60);
//		//结束时间（Unix时间戳）
//		final int endTime = (int) (System.currentTimeMillis() / 1000);
//		//分页大小
//		int pageSize = 1000;
//		//分页索引
//		int pageIndex = 1;
//		//轨迹查询监听器
//		OnTrackListener trackListener = new OnTrackListener() {
//			//请求失败回调接口
//			@Override
//			public void onRequestFailedCallback(String arg0) {
//				Log.i(TAG, "onRequestFailedCallback" + "arg0 = " + arg0);
//			}
//
//			// 查询历史轨迹回调接口
//			@Override
//			public void onQueryHistoryTrackCallback(String arg0) {
//				Log.i(TAG, "onQueryHistoryTrackCallback" + "arg0 = " + arg0);
//				//解json
//				Gson gson=new Gson();//GsonBuilder().serializeNulls().create()
//				LocationResult locationResult=gson.fromJson(arg0,LocationResult.class);
//				//Log.d(TAG,"onQueryHistoryTrackCallback  "+locationResult.toString());
//				Log.d(TAG,"onQueryHistoryTrackCallback size "+locationResult.getSize());
//				List<LatLng> polylines = new ArrayList<LatLng>();
//				for (int i=0;i<locationResult.getSize();i++){
//					//Log.d("TAG2","in for:"+locationResult.getPoints().get(i).get(0));
//					double latitude1=locationResult.getPoints().get(i).get(1);
//					double longtitude1=locationResult.getPoints().get(i).get(0);
//					polylines.add(new LatLng(latitude1,longtitude1));
//					//Log.d("TAG2","onQueryHistoryTrackCallback polylines "+polylines);
//				}
//				Log.d(TAG,"onQueryHistoryTrackCallback polylines "+polylines);
//				upDataLatLng(polylines);
//			}
//
//
//		};
//		//查询历史轨迹
//		client.queryHistoryTrack(serviceId, entityName, simpleReturn, startTime, endTime,
//				pageSize, pageIndex, trackListener);
//	}
//	private void upDataLatLng(List<LatLng> polylines) {
//		double centerLatitude =45.7262258791;
//		double centerLontitude = 126.6596027;
//		double deltaAngle = Math.PI / 180 * 5;
//		double radius = 0.02;
//		OverlayOptions polylineOptions;
//		Log.d(TAG,"test "+polylines.toString());
//		float latitude = (float) (-Math.cos(0) * radius + centerLatitude);
//		float longtitude = (float) (Math.sin(0) * radius + centerLontitude);
//		polylines.add(new LatLng(latitude, longtitude));
//		polylineOptions = new PolylineOptions().points(polylines).width(10).color(Color.RED);
//		mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
//		OverlayOptions markerOptions;
//		markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory
//				.fromResource(R.mipmap.icon_marka)).position(polylines.get(0)).rotate((float) getAngle(0));
//		mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
//
//	}

    //	/**
//	 * 鹰眼查询实时位置
//	 */
//	private void findLocationAtTime() {
//		//entity标识列表（多个entityName，以英文逗号"," 分割）
//		String entityNames = "phone";
//		//检索条件（格式为 : "key1=value1,key2=value2,....."）
//		String columnKey = "";
//		//返回结果的类型（0 : 返回全部结果，1 : 只返回entityName的列表）
//		final int returnType = 0;
//		//活跃时间，UNIX时间戳（指定该字段时，返回从该时间点之后仍有位置变动的entity的实时点集合）
//		int activeTime = (int) (System.currentTimeMillis() / 1000 - 12 * 60 * 60);
//		//分页大小
//		int pageSize = 1000;
//		//分页索引
//		int pageIndex = 1;
//		//Entity监听器
//		OnEntityListener entityListener = new OnEntityListener() {
//			// 查询失败回调接口
//			@Override
//			public void onRequestFailedCallback(String arg0) {
//				Log.i(TAG, "onRequestFailedCallback" + "arg0 = " + arg0);
//			}
//			// 查询entity回调接口，返回查询结果列表
//			@Override
//			public void onQueryEntityListCallback(String arg0) {
//				//位置坐标
//				Log.i(TAG, "onQueryEntityListCallback" + " arg0 = " + arg0);
//				Gson gson=new Gson() ;//GsonBuilder().serializeNulls().create()
//				Log.d(TAG,"onQueryEntityListCallback "+"here");
//				Result result=gson.fromJson(arg0,Result.class);
//				Log.d(TAG,"onQueryEntityListCallback "+result.toString());
//				setMapView(new LatLng(result.getEntities().get(0).getRealTimePoints().getLocationPoint().get(1),result.getEntities().get(0).getRealTimePoints().getLocationPoint().get(0)));
//			}
//		};
//		//查询实时轨迹
//		client.queryEntityList(serviceId, entityNames, columnKey, returnType, activeTime, pageSize,
//				pageIndex, entityListener);
////		moveLooper();
//

    /**
     * 实例化图片资源数组
     */


    /*
     *在地图上标注一点
	 */
    public void markPoint(LatLng point) {//	LatLng point = new LatLng(45.963175, 126.400244);//定义Maker坐标点
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.pushpin1);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
       // setMapView(point);
    }

    /*
     *定位
     */
    public void setMapView(LatLng point) {//设定中心点坐标 
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.mark);
        OverlayOptions options = new MarkerOptions().icon(icon).position(point);
        mBaiduMap.addOverlay(options);
        //定义地图状态  
        MapStatus mMapStatus = new MapStatus.Builder().target(point)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化  
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态  
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    public void onClick(View view) {
        int code = 0;
        Intent intent = new Intent();
        intent.setClass(this, AddPointActivity.class);
        intent.putExtra("number", 0);//把第几个点传过去
        mPoints = new Points(45, 126);
        mPointsList.add(mPoints);//把点加到列表里
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //把这个点的其他内容添加进去
            Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_LONG).show();

            markPoint(new LatLng(45.7,126.7));//纬度 经度
        } else if (resultCode == Activity.RESULT_CANCELED) {
            mPointsList.remove(mPointsList.size() - 1);

        }
    }

    /**
     * 配置百度地图定位sdk参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            Log.d(TAG, " test listener ok");
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
//                mMapView.getMap().clear();
//                setMapView(new LatLng(location.getLatitude(), location.getLongitude()));

                // 开启定位图层
                mBaiduMap.setMyLocationEnabled(true);
// 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
// 设置定位数据
                mBaiduMap.setMyLocationData(locData);
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                BitmapDescriptor  mCurrentMarker = BitmapDescriptorFactory
                        .fromResource(R.mipmap.track1);
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING , true, mCurrentMarker);
//                mBaiduMap.setMyLocationConfiguration(config);
                mBaiduMap.setMyLocationConfigeration(config);


            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i(TAG, "BaiduLocationApiDem " + sb.toString());
        }
    }
}
