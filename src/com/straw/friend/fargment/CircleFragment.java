package com.straw.friend.fargment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.google.gson.Gson;
import com.straw.friend.MyApplication_;
import com.straw.friend.R;
 




import com.straw.friend.activity.BaiduCricleActivity_;
import com.straw.friend.adapter.CircleAdapter;
import com.straw.friend.bean.CircleUser_;
import com.straw.friend.bean.Circle_;
import com.straw.friend.bean.Weather_;
import com.straw.friend.pojo.CircleListReturn;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.PhoneTools;
import com.straw.friend.tools.SingletonRequestQueue;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView; 
import android.widget.Toast;
@EFragment(R.layout.circlefragment)
public class CircleFragment extends Fragment implements OnItemClickListener ,OnRefreshListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@ViewById
	public TextView top_title;
	@ViewById
	public LinearLayout title;
	@ViewById
	public ListView listView;
	List<CircleUser_> list;
	@ViewById
	public TextView city;
	String location = "定位中";
	@ViewById
	public TextView weather;
	public TextView day_wind_power;
	@AfterViews
	void initView(){
		top_title.setText("我的圈");
		title.setBackgroundColor(getResources().getColor(R.color.actionbar_bg));
		initTitle();
	}
	private void initTitle() {
		// 定位
		LocationClient locationClient = new LocationClient(getActivity());
		locationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation arg0) {
				double w = arg0.getLatitude();// 获取纬度
				double j = arg0.getLongitude();// 获取经度
				location = arg0.getAddress().street;
				city = (TextView) title.findViewById(R.id.city);
				city.setText(location);
				getWeather( j , w);
			}
		});// 注册定位监听接口
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开GPRS
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.disableCache(false);// 禁止启用缓存定位
		locationClient.setLocOption(option);
		locationClient.start();
		list = getDate(); 
		initSwipeRefreshLayout();
	}
	private void getWeather( final double j ,final double w) {
		String url = ConntentResource.TIANQI;
		StringRequest request = new StringRequest(Method.POST,url, new Listener<String>() {
			public void onResponse(String arg0) {
				parseData(arg0);
			}
		}, new ErrorListener() {
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(getActivity(), "天气信息获取失败!",
						Toast.LENGTH_SHORT);
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("lng", j+"");
				map.put("lat", w+"");
				map.put("needIndex", "1");
				map.put("needMoreDay", "1");
				map.put("from", "1");
				return map;
			}
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("apikey", PhoneTools.getApiKey());
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getActivity()).add(request);
	}
	protected void parseData(String arg0) {
		Weather_ weather_ = new  Gson().fromJson(arg0, Weather_.class);
		weather = (TextView) title.findViewById(R.id.weather);
		day_wind_power = (TextView) title.findViewById(R.id.day_wind_power);
		weather.setText(weather_.getShowapi_res_body().getF1().getDay_weather());
		day_wind_power.setText(weather_.getShowapi_res_body().getF1().getDay_wind_power());
	}

	private List<CircleUser_> getDate() {
		StringRequest request = new StringRequest(Method.POST,ConntentResource.CIRCLE_LIST, new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				headleDate(arg0);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(getActivity().getApplicationContext(),"数据读取失败!",Toast.LENGTH_SHORT).show();
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("deviceId",  PhoneTools.getuniqueId(getActivity()));
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getActivity().getApplicationContext()).add(request);
		return null;
	}
	private void headleDate(String arg0) {
		Gson gson = new Gson();
		CircleListReturn circleListReturn = gson.fromJson(arg0, CircleListReturn.class);
		list = circleListReturn.getContent();
		CircleAdapter circleAdapter = new CircleAdapter(getActivity().getApplicationContext(), list);
		listView.setAdapter(circleAdapter);
		listView.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(),BaiduCricleActivity_.class);
		TextView cricleId = (TextView) view.findViewById(R.id.id);
		intent.putExtra("circleId", cricleId.getText());
		startActivity(intent);
	}
	//下拉刷新
	@ViewById(R.id.swip)
	public SwipeRefreshLayout swipeRefreshLayout;  
	@SuppressWarnings("deprecation")
	private void initSwipeRefreshLayout() {
		// TODO Auto-generated method stub
		swipeRefreshLayout.setOnRefreshListener(this); 
		
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,  
                android.R.color.holo_green_light,  
                android.R.color.holo_orange_light,  
                android.R.color.holo_red_light);  
	}
	Handler handler2 = new Handler() {
		public void handleMessage(Message msg2) {
			swipeRefreshLayout.setRefreshing(false);
		};
	};
	@Override
	public void onRefresh() {
		list = getDate();
		handler2.sendEmptyMessageDelayed(1, 2000);  
	}
}
