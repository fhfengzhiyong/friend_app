package com.straw.friend.tools;

public class ConntentResource {
	//	public static final String HOST = "http://182.254.220.115:8080/friend";
	public static final String HOST = "http://fengzhiyong.wicp.net/friend";
	public static final String CIRCLE_LIST = HOST+"/circleUser/findCircleByUserId?";
	/**û�и��û���Ȧ��*/
	public static final String CIRCLE_LIST_NO = HOST+"/circle/findCircleNoByUserId?";
	public static final String FINDCIRCLEBYID = HOST+"/circle/findbyid?";
	public static final String FINDCIRCLEBYUSERID = HOST+"/circleUser/findCircleUserByCircleId?";
	public static final String ADDLOCATION = HOST+"/location/add?";
	/**�û����뵽���˵�Ȧ��*/
	public static final String CIRCLE_USER_INSERT= HOST+"/circleUser/add?";
	/**����Ȧ��*/
	public static final String CIRCLE_ADD = HOST+"/circle/add?";
	/**����Ц��*/
	public static final String JOKE_ADD = HOST+"/joke/add?";
	/**��ѯЦ������*/
	public static final String JOKE_LIST = HOST+"/joke/findList";
	/***ɾ��һ��Ц��*/
	public static final String JOKE_DELETE = HOST+"/joke/delete?";
	/**�ϴ�ͼƬ*/
	public static final String USER_IMAGE_ADD = HOST+"/user/addImage?";
	public static final String USER_ADD = HOST+"/user/add?";
	public static final String FINDUSERBYID = HOST+"/user/showUser?";
	public static final String TIANQI = "http://apis.baidu.com/showapi_open_bus/weather_showapi/point";
    public static final String NEW	="http://apis.baidu.com/txapi/world/world?";
	//cityname
}
