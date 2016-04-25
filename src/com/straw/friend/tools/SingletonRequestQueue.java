package com.straw.friend.tools;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonRequestQueue {
	static RequestQueue mRequestQueue;
	public SingletonRequestQueue(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}
	public static  RequestQueue getRequestQueue(Context context){
		if(mRequestQueue == null){
			new SingletonRequestQueue(context);
		}
		return mRequestQueue;
	}
}
