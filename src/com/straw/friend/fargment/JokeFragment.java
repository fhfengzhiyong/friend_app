package com.straw.friend.fargment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.straw.friend.R;
import com.straw.friend.adapter.JokeAdapter;
import com.straw.friend.bean.Joke;
import com.straw.friend.pojo.JokeReturn;
import com.straw.friend.pojo.JokeReturn_;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.PhoneTools;
import com.straw.friend.tools.SingletonRequestQueue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

@EFragment(R.layout.jokefragment)
public class JokeFragment extends Fragment implements OnItemLongClickListener,
		OnRefreshListener {
	@ViewById
	public TextView top_title;
	@ViewById
	public LinearLayout title;
	@ViewById
	public ListView listView;

	@AfterViews
	void initView() {
		top_title.setText("我的段子");
		title.setBackgroundColor(getResources().getColor(R.color.actionbar_3));
		requestData();
		initSwipeRefreshLayout();
	}

	private void requestData() {
		StringRequest request = new StringRequest(Method.POST,
				ConntentResource.JOKE_LIST, new Listener<String>() {
					@Override
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
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("page", "1");
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(
				getActivity().getApplicationContext()).add(request);
	}

	JokeAdapter jokeAdapter;

	private void parseData(String arg0) {
		JokeReturn jokeReturn = new Gson().fromJson(arg0, JokeReturn_.class);
		if (jokeReturn != null) {
			List<Joke> list = jokeReturn.getContent();
			jokeAdapter = new JokeAdapter(list, getActivity()
					.getApplicationContext());
			listView.setAdapter(jokeAdapter);
			listView.setOnItemLongClickListener(this);
		} else {
			Toast.makeText(getActivity(), "暂无数据!", Toast.LENGTH_SHORT).show();
			;
		}
	}

	AdapterView<?> parent;

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, final View view,
			final int position, long id) {

		this.parent = parent;
		// 弹出确认对话框
		Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle("提示:")
				.setMessage("确认删除吗?")
				.setPositiveButton("确认", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						TextView userid = (TextView) view
								.findViewById(R.id.userSin);
						if (!PhoneTools.getuniqueId(getActivity()).equals(
								userid.getText())) {
							Toast.makeText(getActivity(), "暂支持删除本人创建的条目!",
									Toast.LENGTH_SHORT).show();
						} else {
							TextView findViewById = (TextView) view
									.findViewById(R.id.id);
							String id = findViewById.getText().toString();
							Log.i("id", id);
							deleteData(id, position);
						}
					}
				}).setNegativeButton("取消", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
		dialog.show();
		return false;
	}

	private void deleteData(final String id, final int position) {
		StringRequest request = new StringRequest(Method.POST,
				ConntentResource.JOKE_DELETE, new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						parseDeleteData(arg0, position);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", id);
				return map;
			}
		};
		SingletonRequestQueue.getRequestQueue(getActivity()).add(request);
	}

	private void parseDeleteData(String arg0, int position) {
		if ("1".equals(arg0)) {
			Toast.makeText(getActivity(), "删除成功!!", Toast.LENGTH_SHORT).show();
			requestData();
		} else {
			Toast.makeText(getActivity(), "删除失败!", Toast.LENGTH_SHORT).show();
			;
		}
	}

	// 下拉刷新
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
		requestData();
		handler2.sendEmptyMessageDelayed(1, 2000);
	}
}
