//
// DO NOT EDIT THIS FILE.Generated using AndroidAnnotations 3.3.2.
//  You can create a larger work that contains this file and distribute that work under terms of your choice.
//


package com.straw.friend.pojo;

import android.content.Context;

public final class JokeReturn_
    extends JokeReturn
{

    private Context context_;

    private JokeReturn_(Context context) {
        context_ = context;
        init_();
    }

    public static JokeReturn_ getInstance_(Context context) {
        return new JokeReturn_(context);
    }

    private void init_() {
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
