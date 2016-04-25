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
	double w;// ��ȡγ��
	double j;// ��ȡ����
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
			// ״̬��͸�� ��Ҫ�ڴ���SystemBarTintManager ֮ǰ���á�
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
		// �ٶ�map����
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
			// ��ͼ����뵽��ͼ��
			marker = (Marker) (mBaiduMap.addOverlay(ooA));
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", circleLocation);
			marker.setExtraInfo(bundle);
		}
		mBaiduMap.setMyLocationEnabled(true);
		// ��Marker�ĵ��
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				// ���marker�е�����
				CircleLocation info = (CircleLocation) marker.getExtraInfo()
						.get("info");

				InfoWindow mInfoWindow = null;
				// ����һ��TextView�û��ڵ�ͼ����ʾInfoWindow
				TextView location = new TextView(getApplicationContext());
				location.setBackgroundResource(R.drawable.dw);
				//location.setPadding(10, 10, 10, 10);
				
				location.setText(info.getName()+"  "+PhoneTools.getDateByLong(info.getCreatedate()));
				location.setTextColor(Color.BLACK);
				// ��marker���ڵľ�γ�ȵ���Ϣת������Ļ�ϵ�����
				final LatLng ll = marker.getPosition();
				android.graphics.Point p = mBaiduMap.getProjection()
						.toScreenLocation(ll);
				p.y -= 110;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				mInfoWindow = new InfoWindow(location, llInfo, 3);
				// Ϊ������InfoWindow��ӵ���¼�
				// ��ʾInfoWindow
				mBaiduMap.showInfoWindow(mInfoWindow);
				// ������ϸ��Ϣ����Ϊ�ɼ�
				mMarkerInfoLy.setVisibility(View.VISIBLE);
				// �����̼���ϢΪ��ϸ��Ϣ����������Ϣ
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
	 * ����infoΪ�����ϵĿؼ�������Ϣ
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
	 * ���õ������mMarkerLy�Ŀؼ�
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
				// ���POST����
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("circleId", circleId2);
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(
				request);
	}

	private void headlerData(String arg0) {
		// ���Ƶ���ͼ��
		CircleLocationReturn circleLocationReturn = new Gson().fromJson(arg0,
				CircleLocationReturn.class);
		List<CircleLocation> list = circleLocationReturn.getContent();
		drawMap(list);
	}

	void locationMe() {
		// ��λ
		LocationClient locationClient = new LocationClient(this);
		locationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation arg0) {
				double w = arg0.getLatitude();// ��ȡγ��
				double j = arg0.getLongitude();// ��ȡ����
				LatLng cenpt = new LatLng(w, j);
				// �����ͼ״̬
				MapStatus mMapStatus = new MapStatus.Builder().target(cenpt)
						.zoom(16).build();
				// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
				MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
						.newMapStatus(mMapStatus);
				// �ı��ͼ״̬
				mBaiduMap.setMapStatus(mMapStatusUpdate);
			}
		});// ע�ᶨλ�����ӿ�
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��GPRS
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000); // ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.disableCache(false);// ��ֹ���û��涨λ
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
