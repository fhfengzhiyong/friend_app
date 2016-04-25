package com.straw.friend.news;

import org.androidannotations.annotations.EBean;

@EBean
public class Imageurls {
    public  int height;
    public int width;
    public String url;
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
}
