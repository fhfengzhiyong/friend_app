package com.straw.friend.news;

import org.androidannotations.annotations.EBean;

@EBean
public class New {
     public  String  channelId;
     public  String  channelName;
     public  String  chinajoy;
     public  String  desc;
     public  Imageurls imageurls;
     public  String  link;
     public  String  nid;
     public  String  pubDate;
     public  String  source;
     public  String  title;
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChinajoy() {
		return chinajoy;
	}
	public void setChinajoy(String chinajoy) {
		this.chinajoy = chinajoy;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Imageurls getImageurls() {
		return imageurls;
	}
	public void setImageurls(Imageurls imageurls) {
		this.imageurls = imageurls;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
     
     
}
