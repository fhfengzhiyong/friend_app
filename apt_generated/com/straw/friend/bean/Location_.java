//
// DO NOT EDIT THIS FILE.Generated using AndroidAnnotations 3.3.2.
//  You can create a larger work that contains this file and distribute that work under terms of your choice.
//


package com.straw.friend.bean;

import android.content.Context;

public final class Location_
    extends Location
{

    private Context context_;

    private Location_(Context context) {
        context_ = context;
        init_();
    }

    public static Location_ getInstance_(Context context) {
        return new Location_(context);
    }

    private void init_() {
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
