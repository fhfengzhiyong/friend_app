package com.straw.friend;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import android.content.Intent;
import android.view.Window;

import com.straw.friend.activity.BaseActivity;
@WindowFeature({ Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS })
@EActivity(R.layout.welcome)
public class Welcome  extends BaseActivity{
	@AfterViews
	void initView(){
	/*	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Intent intent = new Intent(this,MainActivity_.class);
		startActivity(intent);
	}
}
