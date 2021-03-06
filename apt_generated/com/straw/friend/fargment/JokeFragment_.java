//
// DO NOT EDIT THIS FILE.Generated using AndroidAnnotations 3.3.2.
//  You can create a larger work that contains this file and distribute that work under terms of your choice.
//


package com.straw.friend.fargment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.straw.friend.R.layout;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class JokeFragment_
    extends com.straw.friend.fargment.JokeFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.jokefragment, container, false);
        }
        return contentView_;
    }

    @Override
    public void onDestroyView() {
        contentView_ = null;
        super.onDestroyView();
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static JokeFragment_.FragmentBuilder_ builder() {
        return new JokeFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        swipeRefreshLayout = ((SwipeRefreshLayout) hasViews.findViewById(com.straw.friend.R.id.swip));
        title = ((LinearLayout) hasViews.findViewById(com.straw.friend.R.id.title));
        listView = ((ListView) hasViews.findViewById(com.straw.friend.R.id.listView));
        top_title = ((TextView) hasViews.findViewById(com.straw.friend.R.id.top_title));
        initView();
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<JokeFragment_.FragmentBuilder_, com.straw.friend.fargment.JokeFragment>
    {


        @Override
        public com.straw.friend.fargment.JokeFragment build() {
            JokeFragment_ fragment_ = new JokeFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }

    }

}
