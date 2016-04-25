package com.straw.friend.pojo;

import java.util.List;
import java.util.Map;

import com.straw.friend.bean.News;

public class NewsReturn {
    News news;
    int code;
    String msg;
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
