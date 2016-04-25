package com.straw.friend.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.straw.friend.R;
import com.straw.friend.bean.User;
import com.straw.friend.bean.User_;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.PhoneTools;
import com.straw.friend.tools.SingletonRequestQueue;
import com.straw.friend.tools.SystemBarTintManager;

@WindowFeature({ Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS })
@EActivity(R.layout.register)
public class RegisterActivity extends BaseActivity {
	SystemBarTintManager tintManager;
	@ViewById
	EditText name;
	String miem;
	@ViewById
	ImageView imageView;

	@AfterViews
	void initView() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 状态栏透明 需要在创建SystemBarTintManager 之前调用。
			super.setTranslucentStatus(true);
		}
		miem = PhoneTools.getuniqueId(getApplicationContext());
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.actionbar_4);
		tintManager.setStatusBarDarkMode(false, this);
		isRegister(miem);
	}

	public void commit(View v) {
		String nameString = name.getText().toString();
		// 提交到服务器
		commitData(nameString, miem);
	}

	private void isRegister(final String miem) {
		StringRequest request = new StringRequest(Method.POST,
				ConntentResource.FINDUSERBYID, new Listener<String>() {
					public void onResponse(String arg0) {
						parseData(arg0);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("deviceId", miem);
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(
				request);

	}

	private void parseData(String arg0) {
		if (arg0 != null) {
			User_ user = new Gson().fromJson(arg0, User_.class);
			if (user != null) {
				name.setText(user.getName());
			}
		}
	}

	private void commitData(final String nameString, final String miem) {
		StringRequest request = new StringRequest(Method.POST,
				ConntentResource.USER_ADD, new Listener<String>() {
					public void onResponse(String arg0) {
						finish();
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("name", nameString);
				map.put("deviceid", miem);
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(
				request);
	}

	public void addImage(View v) {
		StringRequest request = new StringRequest(Method.POST,
				ConntentResource.USER_IMAGE_ADD, new Listener<String>() {
					public void onResponse(String arg0) {
						//finish();
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("deviceid", miem);
				return map;
			}
			
		};
		SingletonRequestQueue.getRequestQueue(getApplicationContext()).add(
				request);
	}

	public void selectImage(View v) {
		// Intent intent = new Intent(MediaStore.ACTION_GET_CONTENT);
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(uri));
			//	ImageView imageView = (ImageView) findViewById(R.id.iv01);
				/* 将Bitmap设定到ImageView */
				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
