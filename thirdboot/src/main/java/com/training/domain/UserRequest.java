package com.training.domain;

/**
 * george 2018/9/14 14:55
 */
public class UserRequest {
    private String robotId;
    private String userId;
    private String date;
    private Integer count;
    public UserRequest(String robotId,String userId,String date,Integer count){
        this.robotId = robotId;
        this.userId = userId;
        this.date = date;
        this.count = count;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
