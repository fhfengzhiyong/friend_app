package com.straw.friend.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.straw.friend.R;
import com.straw.friend.adapter.CircleAdapter;
import com.straw.friend.adapter.FriendCircleAdapter;
import com.straw.friend.bean.CircleUser_;
import com.straw.friend.bean.Circle_;
import com.straw.friend.pojo.CircleListReturn;
import com.straw.friend.pojo.FriendCircleListNoUserReturn;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.PhoneTools;
import com.straw.friend.tools.SingletonRequestQueue;
import com.straw.friend.tools.SystemBarTintManager;

@WindowFeature({ Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS })
@EActivity(R.layout.addfriend)
public class AddFriendActivity extends BaseActivity implements
		OnItemClickListener {
	SystemBarTintManager tintManager;
	@ViewById
	public ListView listView;
	List<Circle_> list;

	@AfterViews
	void initView() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 状态栏透明 需要在创建SystemBarTintManager 之前调用。
			super.setTranslucentStatus(true);
		}
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.actionbar_2);
		tintManager.setStatusBarDarkMode(false, this);
		requestData();
	}

	private void requestData() {
		StringRequest request = new StringRequest(Method.POST,
				ConntentResource.CIRCLE_LIST_NO, new Listener<String>() {
					public void onResponse(String arg0) {
						parseData(arg0);
					}
				}, new ErrorListener() {
					public void onErrorResponse(VolleyError arg0) {
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				String imei = PhoneTools.getuniqueId(getApplicationContext());
				map.put("userId", imei);
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(
				request);
	}
	FriendCircleAdapter circleAdapter;
	private void parseData(String arg0) {
		Gson gson = new Gson();
		FriendCircleListNoUserReturn circleListReturn = gson.fromJson(arg0,
				FriendCircleListNoUserReturn.class);
		list = circleListReturn.getContent();
		 circleAdapter = new FriendCircleAdapter(
				getApplicationContext(), list);
		listView.setAdapter(circleAdapter);
		listView.setOnItemClickListener(this);
	}
	View pass;
	AlertDialog create ;
	@SuppressLint("NewApi")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		final TextView circle = (TextView) view.findViewById(R.id.id);
		// 弹出窗口要求出入密码
		LayoutInflater layoutInflater = LayoutInflater
				.from(getApplicationContext());
		 pass = layoutInflater.inflate(R.layout.password, null);
		create = new AlertDialog.Builder(this).setView(pass)
				.create();
		Button ok = (Button) pass.findViewById(R.id.ok);
		final EditText password = (EditText) pass.findViewById(R.id.editText);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String passString = password.getText() + "";
				String circleId = circle.getText().toString();
				checkPassWord(circleId, passString);
			}

		});
		Button cancel = (Button) pass.findViewById(R.id.cancel1);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				create.cancel();
			}
		});
		create.show();
	}

	/**
	 * @Description: 核对加入圈子的密码
	 * @param circleId
	 * @param passString
	 * @return void
	 * @author fengzy 2015年9月25日 上午11:38:07
	 */
	private void checkPassWord(final String circleId, final String passString) {
		final String imei = PhoneTools.getuniqueId(getApplicationContext());
		StringRequest request = new StringRequest(Method.POST,ConntentResource.CIRCLE_USER_INSERT,
				new Listener<String>() {
					public void onResponse(String arg0) {
						parseCheckData(arg0);
					}
				}, new ErrorListener() {
					public void onErrorResponse(VolleyError arg0) {

					}
				}) {
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						// 添加POST数据
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("userId", imei);
						map.put("circleId", circleId);
						map.put("passString", passString);
						return map;
					}
		};
		SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(request);
	}
	private void parseCheckData(String arg0) {
		if("0".equals(arg0)){
			//密码错误
			TextView  error = (TextView) pass.findViewById(R.id.eror);
			error.setVisibility(View.VISIBLE);
		}else{
			//tishi();
			//Intent intent = new Intent(getApplicationContext(),
					//AddFriendActivity_.class);
			//startActivity(intent);
			create.cancel();
			requestData();
		}
	}
	@Background
	void tishi(){
		//Toast.makeText(getApplicationContext(), "加入成功", Toast.LENGTH_SHORT);
	}
	public void createCircle(View v){
		Intent intent = new Intent(this,CreateCircle_.class);
		startActivity(intent);;
	}
	
}
