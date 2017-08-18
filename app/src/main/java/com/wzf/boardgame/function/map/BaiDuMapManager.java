package com.wzf.boardgame.function.map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.DebugLog;

import java.text.DecimalFormat;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-05-31 16:40
 */

public class BaiDuMapManager {
    private final String TAG = getClass().getSimpleName();
    private static BaiDuMapManager INSTANCE;
    private static DecimalFormat df   = new DecimalFormat("######0.00");
    private double lon;
    private double lat;
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public double getLon() {
        return lon;
    }


    public double getLat() {
        return lat;
    }


    private BaiDuMapManager(){
        SDKInitializer.initialize(MyApplication.getAppInstance());
    }
    public static BaiDuMapManager getInstance(){
        if(INSTANCE == null){
            synchronized (BaiDuMapManager.class){
                if(INSTANCE == null){
                    INSTANCE = new BaiDuMapManager();
                }
            }
        }
        return INSTANCE;
    }



    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private OnLocationMessageGetListener listener;
    private BDLocation bdLocation;

    public void  getLocationMessage(OnLocationMessageGetListener listener){
        this.listener = listener;
        if(bdLocation == null){
            //声明LocationClient类
            mLocationClient = new LocationClient(MyApplication.getAppInstance());
            initLocation();
            mLocationClient.registerLocationListener( myListener );
            //注册监听函数
            mLocationClient.start();
            mLocationClient.requestLocation();
        }else {
            dealLocationMessage(bdLocation);
        }

   }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=1000;
//        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mLocationClient.stop();
            bdLocation = location;
            dealLocationMessage(location);
            UserInfo.getInstance().updateLbs();
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private void dealLocationMessage(BDLocation location) {
        this.lon = location.getLongitude();
        this.lat = location.getLatitude();
        this.cityName = location.getCity();
        if(listener != null){
            listener.onReceiveLocation(location.getCity(), location.getLongitude(), location.getLatitude());
            listener = null;
        }

        //获取定位结果
        StringBuffer sb = new StringBuffer(128);

        sb.append("time : ");
        sb.append(location.getTime());    //获取定位时间

        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());    //获取纬度信息

        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());    //获取经度信息

        sb.append("\nradius : ");
        sb.append(location.getRadius());    //获取定位精准度

        sb.append("\ncity : ");
        sb.append(location.getCity());    //获取定位精准度

        sb.append("\naddress : ");
        sb.append(location.getAddress().address);    //获取定位精准度

        DebugLog.i(TAG, sb.toString());
    }


    public static String getTwoPointDistance(LatLng one, LatLng two){
        double distance = DistanceUtil.getDistance(one, two);
        return getDistanceString(distance);
    }

    public static String getDistanceString(double distance){
        String endString = "米";
        if(distance > 1000){
            endString = "千米";
            distance /=1000;
        }
        return df.format(distance) + endString;
    }

    public interface OnLocationMessageGetListener{
        void onReceiveLocation(String cityName, double lon, double lat);
    }
}
