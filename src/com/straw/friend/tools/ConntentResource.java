package com.straw.friend.tools;

public class ConntentResource {
	//	public static final String HOST = "http://182.254.220.115:8080/friend";
	public static final String HOST = "http://fengzhiyong.wicp.net/friend";
	public static final String CIRCLE_LIST = HOST+"/circleUser/findCircleByUserId?";
	/**没有该用户的圈子*/
	public static final String CIRCLE_LIST_NO = HOST+"/circle/findCircleNoByUserId?";
	public static final String FINDCIRCLEBYID = HOST+"/circle/findbyid?";
	public static final String FINDCIRCLEBYUSERID = HOST+"/circleUser/findCircleUserByCircleId?";
	public static final String ADDLOCATION = HOST+"/location/add?";
	/**用户加入到别人的圈子*/
	public static final String CIRCLE_USER_INSERT= HOST+"/circleUser/add?";
	/**创建圈子*/
	public static final String CIRCLE_ADD = HOST+"/circle/add?";
	/**创建笑话*/
	public static final String JOKE_ADD = HOST+"/joke/add?";
	/**查询笑话数据*/
	public static final String JOKE_LIST = HOST+"/joke/findList";
	/***删除一条笑话*/
	public static final String JOKE_DELETE = HOST+"/joke/delete?";
	/**上传图片*/
	public static final String USER_IMAGE_ADD = HOST+"/user/addImage?";
	public static final String USER_ADD = HOST+"/user/add?";
	public static final String FINDUSERBYID = HOST+"/user/showUser?";
	public static final String TIANQI = "http://apis.baidu.com/showapi_open_bus/weather_showapi/point";
    public static final String NEW	="http://apis.baidu.com/txapi/world/world?";
	//cityname
}
