package com.straw.friend.fargment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.straw.friend.R;
import com.straw.friend.pojo.NewsReturn;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.PhoneTools;
import com.straw.friend.tools.SingletonRequestQueue;

import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
@EFragment(R.layout.friendfragment)
public class FriendFragmeng extends Fragment{
	@ViewById
	public TextView top_title;
	@ViewById
	public LinearLayout title;
	@ViewById
	public ListView listView;
	@ViewById
	public Button button;
	@ViewById
	WebView webView;
	//页数
	int page=1;
	int num= 10;
	@AfterViews
	void initView(){
		top_title.setText("新鲜事");
		title.setBackgroundColor(getResources().getColor(R.color.actionbar_2));
		//requestDate();
		parseData();
	}
	private void parseData() {
		webView.loadUrl("http://www.info.3g.qq.com/g/s?aid=index&g_ut=3&g_f=18449");
		webView.setWebViewClient(new MyWebViewClient());
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
	}
	class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	private void requestDate() {
		String url = "num="+num+"&page="+page;
		StringRequest request = new StringRequest(ConntentResource.NEW+url, new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				parseData(arg0);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("page", page+"");
				return map;
			}
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("Apikey",  PhoneTools.getApiKey());
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getActivity().getApplicationContext()).add(request);
	}
	/**
	 * @Description: 解析返回的数据
	 * @param arg0  
	 * @return void
	 * @author fengzy 2015年9月25日 下午6:44:57
	 */
	private void parseData(String arg0) {
		NewsReturn newsReturn = new Gson().fromJson(arg0, NewsReturn.class);
	}
}
