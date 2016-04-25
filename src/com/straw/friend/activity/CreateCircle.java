package com.straw.friend.activity;

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WakeLock;
import org.androidannotations.annotations.WindowFeature;

import android.os.Build;
import android.provider.Contacts.PhotosColumns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.straw.friend.R;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.PhoneTools;
import com.straw.friend.tools.SingletonRequestQueue;
import com.straw.friend.tools.SystemBarTintManager;
@WindowFeature({ Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS })
@EActivity(R.layout.createcricle)
public class CreateCircle extends BaseActivity{
	@ViewById
	public EditText name;
	@ViewById
	public EditText password;
	@ViewById
	public EditText content;
	SystemBarTintManager tintManager;
	@AfterViews
	void initView(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 状态栏透明 需要在创建SystemBarTintManager 之前调用。
			super.setTranslucentStatus(true);
		}
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.actionbar_bg);
		tintManager.setStatusBarDarkMode(false, this);
	}
	public void ok(View v){
		String stringName = name.getText()+"";
		String stringPass = password.getText()+"";
		String stringContent = content.getText()+"";
		commit(stringName,stringPass,stringContent);
	}
	private void commit(final String stringName, final String stringPass,
			final String stringContent) {
		StringRequest request = new StringRequest(Method.POST,ConntentResource.CIRCLE_ADD, new Listener<String>() {
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
				map.put("name", stringName);
				map.put("password", stringPass);
				map.put("content", stringContent);
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(request);
	}
	public void no(View v){
		finish();
	};
}
