package com.straw.friend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EService;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.straw.friend.MyApplication;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.SingletonRequestQueue;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
@EService
public class GpsService extends Service{
	@App
	MyApplication application;
	Integer cacheTime = 1000 * 60*10;  
	String x;
	String y;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	public void onCreate() {
		//Toast.makeText(this, "定位已启动!", Toast.LENGTH_LONG).show();  
	};
	String imei ;
	@AfterInject
	void init(){
		 imei = application.getImei();
	        Timer miTimer = new Timer();
	        miTimer.schedule(new Task(), 10 * 1000,cacheTime);
	}
    class Task extends TimerTask {
		@Override
		public void run() {
			try{
			getGps();
			}catch(Exception e){
				
			}
		}
    }
    public void getGps(){
    	LocationClient locationClient = application.getLocationClient();
		locationClient.registerLocationListener( new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation arg0) {
				double w =  arg0.getLatitude();//获取纬度
			    double j = arg0.getLongitude();//获取经度
			    x = w+"";
			    y = j+"";
			    StringRequest request = new StringRequest(Method.POST,ConntentResource.ADDLOCATION, new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						
					}
				}){
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("userId", imei);
						map.put("x", x);
						map.put("y", y);
						return map;
					}
				};
				SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(request);
			}
		});//注册定位监听接口
		 LocationClientOption option = new LocationClientOption();
		    option.setOpenGps(true); //打开GPRS
		    option.setAddrType("all");//返回的定位结果包含地址信息
		    option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		    option.setScanSpan(5000); //设置发起定位请求的间隔时间为5000ms
		    option.disableCache(false);//禁止启用缓存定位
		    locationClient.setLocOption(option);
		    locationClient.start();
    }
}
