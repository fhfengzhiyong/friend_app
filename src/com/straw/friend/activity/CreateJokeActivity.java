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
@EActivity(R.layout.createjoke)
public class CreateJokeActivity extends BaseActivity implements
		OnItemClickListener {
	SystemBarTintManager tintManager;
	@ViewById
	public EditText title;
	@ViewById
	public EditText content;

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
	}
	public void ok(View v){
		String stringName = title.getText()+"";
		String stringContent = content.getText()+"";
		commit(stringName,stringContent);
	}
	private void commit(final String stringName, 
			final String stringContent) {
		StringRequest request = new StringRequest(Method.POST,ConntentResource.JOKE_ADD, new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				if("1".equals(arg0)){
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "创建失败", Toast.LENGTH_SHORT);
				}
			}
		}, new ErrorListener() {
			public void onErrorResponse(VolleyError arg0) {
				
			}
		}){
			protected Map<String, String> getParams() throws AuthFailureError {
				// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				String imei = PhoneTools.getuniqueId(getApplicationContext());
				map.put("usreId", imei);
				map.put("title", stringName);
				map.put("content", stringContent);
				Log.i("", imei+"||"+stringName+"||"+stringContent);
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(this).add(request);
	}
	public void no(View v){
		finish();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	};
	
}
