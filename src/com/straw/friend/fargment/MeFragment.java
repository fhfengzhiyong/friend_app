package com.straw.friend.fargment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.straw.friend.R;
import com.straw.friend.activity.RegisterActivity_;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
@EFragment(R.layout.mefragment)
public class MeFragment extends Fragment{
	@ViewById
	public TextView top_title;
	@ViewById
	public LinearLayout title;
	@AfterViews
	void initView(){
		top_title.setText("πÿ”⁄Œ“");
		title.setBackgroundColor(getResources().getColor(R.color.actionbar_4));
		//View me = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.me, null);
		
	}
}
