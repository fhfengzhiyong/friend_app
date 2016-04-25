package com.straw.friend;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.straw.friend.activity.AddFriendActivity_;
import com.straw.friend.activity.BaseActivity;
import com.straw.friend.activity.CreateJokeActivity_;
import com.straw.friend.activity.RegisterActivity_;
import com.straw.friend.fargment.CircleFragment_;
import com.straw.friend.fargment.FriendFragmeng_;
import com.straw.friend.fargment.JokeFragment_;
import com.straw.friend.fargment.MeFragment_;
import com.straw.friend.service.GpsService_;
import com.straw.friend.tools.SystemBarTintManager;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

@WindowFeature({ Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS })
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnClickListener {
	@App
	MyApplication myApplication;
	@ViewById(R.id.viewPager)
	public ViewPager mViewPager;
	List<Fragment> fragmentList;
	@ViewById
	public ImageView f_tab_img1;
	@ViewById
	public TextView f_tab_tv_1;
	@ViewById
	public ImageView f_tab_img2;
	@ViewById
	public TextView f_tab_tv_2;
	@ViewById
	public ImageView f_tab_img3;
	@ViewById
	public TextView f_tab_tv_3;
	@ViewById
	public ImageView f_tab_img4;
	@ViewById
	public TextView f_tab_tv_4;
	@ViewById
	public LinearLayout f_tab_1;
	@ViewById
	public LinearLayout f_tab_2;
	@ViewById
	public LinearLayout f_tab_3;
	@ViewById
	public LinearLayout f_tab_4;
	@ViewById
	public TextView city;
	String location = "定位中";
	@ViewById
	public TextView weather;
	public TextView day_wind_power;
	SystemBarTintManager tintManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	void initView() {
		Intent intent = new Intent(this, GpsService_.class);
		LocationClient locationClient = new LocationClient(this);
		myApplication.setLocationClient(locationClient);
		startService(intent);
		// 状态栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 状态栏透明 需要在创建SystemBarTintManager 之前调用。
			super.setTranslucentStatus(true);
		}
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.actionbar_bg);
		tintManager.setStatusBarDarkMode(false, this);
		f_tab_1.setOnClickListener(this);
		f_tab_2.setOnClickListener(this);
		f_tab_3.setOnClickListener(this);
		f_tab_4.setOnClickListener(this);
		PageChangeListener pageChangeListener = new PageChangeListener();
		mViewPager.setOnPageChangeListener(pageChangeListener);
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new CircleFragment_());
		fragmentList.add(new FriendFragmeng_());
		fragmentList.add(new JokeFragment_());
		fragmentList.add(new MeFragment_());
		FragmentPagerAdapter madapter = new FragmentPagerAdapter(
				getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return fragmentList.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragmentList.get(arg0);
			}
		};
		mViewPager.setAdapter(madapter);
		mViewPager.setOffscreenPageLimit(4);
	}

	class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			initFooter();
			if (arg0 == 0) {
				tintManager.setStatusBarTintResource(R.color.actionbar_bg);
				f_tab_img1.setImageResource(R.drawable.img_index_normal);
				f_tab_tv_1.setTextColor(Color.rgb(51, 153, 255));
				f_tab_1.setBackgroundColor(Color.rgb(243, 243, 243));
			} else if (arg0 == 1) {
				tintManager.setStatusBarTintResource(R.color.actionbar_2);
				f_tab_img2.setImageResource(R.drawable.img_server_normal);
				f_tab_tv_2.setTextColor(Color.rgb(51, 153, 255));
				f_tab_2.setBackgroundColor(Color.rgb(243, 243, 243));
			} else if (arg0 == 2) {
				tintManager.setStatusBarTintResource(R.color.actionbar_3);
				f_tab_img3.setImageResource(R.drawable.img_shop_normal);
				f_tab_tv_3.setTextColor(Color.rgb(51, 153, 255));
				f_tab_3.setBackgroundColor(Color.rgb(243, 243, 243));
			} else if (arg0 == 3) {
				tintManager.setStatusBarTintResource(R.color.actionbar_4);
				f_tab_img4.setImageResource(R.drawable.img_my_normal);
				f_tab_tv_4.setTextColor(Color.rgb(51, 153, 255));
				f_tab_4.setBackgroundColor(Color.rgb(243, 243, 243));
			}

		}

	}

	void initFooter() {
		f_tab_img1.setImageResource(R.drawable.img_index_pressed);
		f_tab_img2.setImageResource(R.drawable.img_server_pressed);
		f_tab_img3.setImageResource(R.drawable.img_shop_pressed);
		f_tab_img4.setImageResource(R.drawable.img_my_pressed);
		f_tab_tv_1.setTextColor(Color.rgb(174, 174, 174));
		f_tab_tv_2.setTextColor(Color.rgb(174, 174, 174));
		f_tab_tv_3.setTextColor(Color.rgb(174, 174, 174));
		f_tab_tv_4.setTextColor(Color.rgb(174, 174, 174));
		f_tab_1.setBackgroundColor(Color.WHITE);
		f_tab_2.setBackgroundColor(Color.WHITE);
		f_tab_3.setBackgroundColor(Color.WHITE);
		f_tab_4.setBackgroundColor(Color.WHITE);
	}

	private long firstTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 1500) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				firstTime = secondTime;// 更新firstTime
				return true;
			} else { // 两次按键小于2秒时，退出应用
				System.exit(0);
			}
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		initFooter();
		if (v.getId() == f_tab_1.getId()) {
			tintManager.setStatusBarTintResource(R.color.actionbar_bg);
			mViewPager.setCurrentItem(0);
			f_tab_img1.setImageResource(R.drawable.img_index_normal);
			f_tab_tv_1.setTextColor(Color.rgb(51, 153, 255));
			f_tab_1.setBackgroundColor(Color.rgb(243, 243, 243));
		} else if (v.getId() == f_tab_2.getId()) {
			tintManager.setStatusBarTintResource(R.color.actionbar_2);
			mViewPager.setCurrentItem(1);
			f_tab_img2.setImageResource(R.drawable.img_server_normal);
			f_tab_tv_2.setTextColor(Color.rgb(51, 153, 255));
			f_tab_2.setBackgroundColor(Color.rgb(243, 243, 243));
		} else if (v.getId() == f_tab_3.getId()) {
			tintManager.setStatusBarTintResource(R.color.actionbar_3);
			mViewPager.setCurrentItem(2);
			f_tab_img3.setImageResource(R.drawable.img_shop_normal);
			f_tab_tv_3.setTextColor(Color.rgb(51, 153, 255));
			f_tab_3.setBackgroundColor(Color.rgb(243, 243, 243));
		} else if (v.getId() == f_tab_4.getId()) {
			tintManager.setStatusBarTintResource(R.color.actionbar_4);
			mViewPager.setCurrentItem(3);
			f_tab_img4.setImageResource(R.drawable.img_my_normal);
			f_tab_tv_4.setTextColor(Color.rgb(51, 153, 255));
			f_tab_4.setBackgroundColor(Color.rgb(243, 243, 243));
		}

	}

	public void register(View v) {
		Intent intent = new Intent(getApplicationContext(),
				RegisterActivity_.class);
		startActivity(intent);
	}

	public void addfriend(View v) {
		Intent intent = new Intent(getApplicationContext(),
				AddFriendActivity_.class);
		startActivity(intent);
	}

	public void createjoke(View v) {
		Intent intent = new Intent(getApplicationContext(),
				CreateJokeActivity_.class);
		startActivity(intent);
	}

}
