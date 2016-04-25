package com.straw.friend.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.straw.friend.R;
import com.straw.friend.adapter.CircleAdapter.ViewHolder;
import com.straw.friend.bean.Joke;
import com.straw.friend.bean.User_;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.PhoneTools;
import com.straw.friend.tools.SingletonRequestQueue;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class JokeAdapter extends BaseAdapter {
	List<Joke> list;
	Context context;
	LayoutInflater layoutInflater;
	public JokeAdapter(List<Joke> list, Context context) {
		super();
		this.list = list;
		this.layoutInflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	ViewHolder viewHolder ;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			 viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.jokeitem, null);
			initHolder(viewHolder,convertView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Joke joke = list.get(position);
		viewHolder.title.setText(joke.getContent());
		viewHolder.createdate.setText("["+PhoneTools.getDateByLong(joke.getCreatedate()));
		viewHolder.title.setText(joke.getContent());
		viewHolder.id.setText(joke.getId());
		viewHolder.userSin.setText(joke.getUserid());
		getUserName(joke.getUserid(),viewHolder);
		return convertView;
	}
    private String getUserName(final String userid,final ViewHolder viewHolder ) {
    	StringRequest request = new StringRequest(Method.POST,ConntentResource.FINDUSERBYID, new  Listener<String>() {
			public void onResponse(String arg0) {
				parseData(arg0,viewHolder);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		}){
		   @Override
		protected Map<String, String> getParams() throws AuthFailureError {
			// 添加POST数据
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("deviceId", userid);
				return map;
		}
		};
		SingletonRequestQueue.getRequestQueue(context).add(request);
		return null;
	}
    private void parseData(String arg0,ViewHolder viewHolder ) {
		if(arg0 != null){
			User_ user =new  Gson().fromJson(arg0, User_.class);
			if(user !=null){
				viewHolder.userId.setText("by  "+user.getName()+"]");
			}else{
				viewHolder.userId.setText("by 匿名 ]");
			}
		}
	}
	private void initHolder(ViewHolder viewHolder, View convertView) {
    	viewHolder.title = (TextView) convertView.findViewById(R.id.title);
    	viewHolder.createdate = (TextView) convertView.findViewById(R.id.createdate);
    	viewHolder.userId = (TextView) convertView.findViewById(R.id.userId);
    	viewHolder.id = (TextView) convertView.findViewById(R.id.id);
    	viewHolder.userSin = (TextView) convertView.findViewById(R.id.userSin);
	}

	class ViewHolder{
    	TextView title;
    	TextView createdate;
    	TextView userId;
    	TextView id;
    	TextView userSin;
    }
}
