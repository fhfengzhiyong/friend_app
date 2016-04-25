package com.straw.friend.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





import com.android.volley.Response.ErrorListener;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.straw.friend.R;
import com.straw.friend.bean.CircleUser_;
import com.straw.friend.bean.Circle_;
import com.straw.friend.tools.ConntentResource;
import com.straw.friend.tools.SingletonRequestQueue;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendCircleAdapter extends BaseAdapter{
    Context context;
    List<Circle_> list;
    LayoutInflater layoutInflater;
    ViewHolder viewHolder;
    Circle_ circle_;
	public FriendCircleAdapter(Context context, List<Circle_> list) {
		super();
		this.context = context;
		this.list = list;
		this.layoutInflater = LayoutInflater.from(context);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView ==null){
			convertView = layoutInflater.inflate(R.layout.item_circle, null);
			viewHolder = new ViewHolder();
			initViewHolder(viewHolder,convertView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Circle_ circle_ = list.get(position);
		//findByid(circleUser_,viewHolder);
		fillData(circle_,viewHolder);
		return convertView;
	}
	private void fillData(Circle_ circleUser_, ViewHolder viewHolder2) {
		viewHolder2.name.setText(circleUser_.getName());
		viewHolder2.content.setText(circleUser_.getContent());
		viewHolder2.id.setText(circleUser_.getId());
		
	}
	private void initViewHolder(ViewHolder viewHolder,View v) {
		viewHolder.imageView = (ImageView) v.findViewById(R.id.item_cirle_img);
		viewHolder.name = (TextView) v.findViewById(R.id.item_circle_name);
		viewHolder.content = (TextView) v.findViewById(R.id.item_circle_content);
		viewHolder.id = (TextView) v.findViewById(R.id.id);
	}
	class ViewHolder{
		public TextView id;
		public ImageView imageView;
		public TextView name;
		public TextView content;
	}
}
