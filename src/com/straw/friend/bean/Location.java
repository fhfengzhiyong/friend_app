package com.straw.friend.bean;

import java.util.Date;
import java.util.UUID;

import org.androidannotations.annotations.EBean;
@EBean
public class Location {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column location.Id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column location.userid
     *
     * @mbggenerated
     */
    private String userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column location.x
     *
     * @mbggenerated
     */
    private String x;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column location.y
     *
     * @mbggenerated
     */
    private String y;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column location.createDate
     *
     * @mbggenerated
     */
    private Date createdate = new Date();

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column location.Id
     *
     * @return the value of location.Id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column location.Id
     *
     * @param id the value for location.Id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column location.userid
     *
     * @return the value of location.userid
     *
     * @mbggenerated
     */
    public String getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column location.userid
     *
     * @param userid the value for location.userid
     *
     * @mbggenerated
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column location.x
     *
     * @return the value of location.x
     *
     * @mbggenerated
     */
    public String getX() {
        return x;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column location.x
     *
     * @param x the value for location.x
     *
     * @mbggenerated
     */
    public void setX(String x) {
        this.x = x == null ? null : x.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column location.y
     *
     * @return the value of location.y
     *
     * @mbggenerated
     */
    public String getY() {
        return y;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column location.y
     *
     * @param y the value for location.y
     *
     * @mbggenerated
     */
    public void setY(String y) {
        this.y = y == null ? null : y.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column location.createDate
     *
     * @return the value of location.createDate
     *
     * @mbggenerated
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column location.createDate
     *
     * @param createdate the value for location.createDate
     *
     * @mbggenerated
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}