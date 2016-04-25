package com.straw.friend.tools;

import java.util.Date;
import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneTools {
	public static String getuniqueId(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		String simSerialNumber = tm.getSimSerialNumber();
		String androidId = android.provider.Settings.Secure.getString(
				context.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) imei.hashCode() << 32) | simSerialNumber.hashCode());
		String uniqueIuniqueIdd = deviceUuid.toString();
		return uniqueIuniqueIdd;
	}
	public static String getApiKey() {
		return "ebab0849c7912ab2836f9c59d02d455b";
	}
    public static String getDateByLong(long l){
    	Date date=new Date(l);
		String s = (1900+date.getYear())+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日"+date.getHours()+"时"+date.getMinutes()+"分"+date.getSeconds()+"秒";
    	return s; 
    }
}
