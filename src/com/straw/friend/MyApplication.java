package com.straw.friend;


import java.util.List;

import org.androidannotations.annotations.EApplication;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.straw.friend.bean.Weather_;
import com.straw.friend.tools.PhoneTools;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

@EApplication
public class MyApplication extends android.app.Application{
	String imei="";
	LocationClient locationClient;
	Weather_ weather;
	public static final String APP_ID = "2882303761517395702";
	public static final String APP_KEY = "5951739524702";
	public static final String TAG = "com.straw.friend";
	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		imei = PhoneTools.getuniqueId(getApplicationContext());
		initXiaoMiPush();
	}
	private void initXiaoMiPush() {
		// 初始化push推送服务
		if (shouldInit()) {
			MiPushClient.registerPush(this, APP_ID, APP_KEY);
		}
		// 打开Log
		LoggerInterface newLogger = new LoggerInterface() {

			@Override
			public void setTag(String tag) {
				// ignore
			}

			@Override
			public void log(String content, Throwable t) {
				Log.d(TAG, content, t);
			}

			@Override
			public void log(String content) {
				Log.d(TAG, content);
			}
		};
		Logger.setLogger(this, newLogger);
	}

	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public LocationClient getLocationClient() {
		return locationClient;
	}
	public void setLocationClient(LocationClient locationClient) {
		this.locationClient = locationClient;
	}
	public Weather_ getWeather() {
		return weather;
	}
	public void setWeather(Weather_ weather) {
		this.weather = weather;
	}
	
}
