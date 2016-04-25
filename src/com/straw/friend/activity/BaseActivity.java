package com.straw.friend.activity;


import android.annotation.TargetApi;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;


public class BaseActivity  extends FragmentActivity {
	@TargetApi(19)
	protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
