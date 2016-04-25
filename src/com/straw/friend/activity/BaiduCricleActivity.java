package com.straw.friend.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

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
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.straw.friend.R;
import com.straw.friend.bean.CircleLocation;
import com.straw.friend.bean.CircleLocationReturn;
import com.straw.friend.pojo.CircularImage;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.PhoneTools;
import com.straw.friend.tools.SingletonRequestQueue;
import com.straw.friend.tools.SystemBarTintManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

@WindowFeature({ Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS })
@EActivity(R.layout.baidumap)
public class BaiduCricleActivity extends BaseActivity {
	@ViewById
	MapView bmapView;
	private BaiduMap mBaiduMap;
	@Extra
	String circleId;
	double w;// 获取纬度
	double j;// 获取经度
	RelativeLayout mMarkerInfoLy;
	SystemBarTintManager tintManager;
	Marker marker;

	// @ViewById(R.id.personInfo)
	// RelativeLayout mMarkerInfoLy;
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mMarkerInfoLy = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.personinfo, null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 状态栏透明 需要在创建SystemBarTintManager 之前调用。
			super.setTranslucentStatus(true);
		}
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.actionbar_bg);
		tintManager.setStatusBarDarkMode(false, this);
	}

	@AfterExtras
	@AfterViews
	void initView() {
		requestData(circleId);
	}

	private void drawMap(List<CircleLocation> list) {
		// 百度map对象
		mBaiduMap = bmapView.getMap();
		locationMe();
		BitmapDescriptor bdA = BitmapDescriptorFactory
				.fromResource(R.drawable.gps);
		for (int i = 0; i < list.size(); i++) {
			CircleLocation circleLocation = list.get(i);
			LatLng llA = new LatLng(Double.parseDouble(circleLocation.getX()),
					Double.parseDouble(circleLocation.getY()));
			OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
					.zIndex(15).draggable(true);
			// 将图层加入到地图中
			marker = (Marker) (mBaiduMap.addOverlay(ooA));
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", circleLocation);
			marker.setExtraInfo(bundle);
		}
		mBaiduMap.setMyLocationEnabled(true);
		// 对Marker的点击
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				// 获得marker中的数据
				CircleLocation info = (CircleLocation) marker.getExtraInfo()
						.get("info");

				InfoWindow mInfoWindow = null;
				// 生成一个TextView用户在地图中显示InfoWindow
				TextView location = new TextView(getApplicationContext());
				location.setBackgroundResource(R.drawable.dw);
				//location.setPadding(10, 10, 10, 10);
				
				location.setText(info.getName()+"  "+PhoneTools.getDateByLong(info.getCreatedate()));
				location.setTextColor(Color.BLACK);
				// 将marker所在的经纬度的信息转化成屏幕上的坐标
				final LatLng ll = marker.getPosition();
				android.graphics.Point p = mBaiduMap.getProjection()
						.toScreenLocation(ll);
				p.y -= 110;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				mInfoWindow = new InfoWindow(location, llInfo, 3);
				// 为弹出的InfoWindow添加点击事件
				// 显示InfoWindow
				mBaiduMap.showInfoWindow(mInfoWindow);
				// 设置详细信息布局为可见
				mMarkerInfoLy.setVisibility(View.VISIBLE);
				// 根据商家信息为详细信息布局设置信息
				popupInfo(mMarkerInfoLy, info);
				return true;
			}
		});
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				// mMarkerInfoLy.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();

			}
		});
	}

	/**
	 * 根据info为布局上的控件设置信息
	 * 
	 * @param mMarkerInfo2
	 * @param info
	 */
	protected void popupInfo(RelativeLayout mMarkerLy,
			CircleLocation circleLocation) {
		ViewHolder viewHolder = null;
		if (mMarkerLy.getTag() == null) {
			viewHolder = new ViewHolder();
			viewHolder.infoImg = (CircularImage) mMarkerLy
					.findViewById(R.id.info_img);
			viewHolder.infoName = (TextView) mMarkerLy
					.findViewById(R.id.info_name);
			mMarkerLy.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) mMarkerLy.getTag();
		viewHolder.infoImg.setImageResource(R.drawable.add_gouwu);
		viewHolder.infoName.setText(circleLocation.getName());
	}

	/**
	 * 复用弹出面板mMarkerLy的控件
	 * 
	 * @author zhy
	 * 
	 */
	private class ViewHolder {
		CircularImage infoImg;
		TextView infoName;
		TextView infoDistance;
		TextView infoZan;
	}

	private void requestData(final String circleId2) {
		StringRequest request = new StringRequest(Method.POST,
				ConntentResource.FINDCIRCLEBYUSERID, new Listener<String>() {
					public void onResponse(String arg0) {
						headlerData(arg0);
					}
				}, new ErrorListener() {
					public void onErrorResponse(VolleyError arg0) {
					}
				}) {
			protected Map<String, String> getParams() throws AuthFailureError {
				// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("circleId", circleId2);
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(
				request);
	}

	private void headlerData(String arg0) {
		// 绘制到地图上
		CircleLocationReturn circleLocationReturn = new Gson().fromJson(arg0,
				CircleLocationReturn.class);
		List<CircleLocation> list = circleLocationReturn.getContent();
		drawMap(list);
	}

	void locationMe() {
		// 定位
		LocationClient locationClient = new LocationClient(this);
		locationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation arg0) {
				double w = arg0.getLatitude();// 获取纬度
				double j = arg0.getLongitude();// 获取经度
				LatLng cenpt = new LatLng(w, j);
				// 定义地图状态
				MapStatus mMapStatus = new MapStatus.Builder().target(cenpt)
						.zoom(16).build();
				// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
				MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
						.newMapStatus(mMapStatus);
				// 改变地图状态
				mBaiduMap.setMapStatus(mMapStatusUpdate);
			}
		});// 注册定位监听接口
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开GPRS
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
		option.disableCache(false);// 禁止启用缓存定位
		locationClient.setLocOption(option);
		locationClient.start();
		BDLocation lastKnownLocation = locationClient.getLastKnownLocation();
		LatLng cenpt = new LatLng(w, j);
		MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(8)
				.build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

}
