package com.deep.CountLicenseHistory;


import java.io.Serializable;

/**
 * @Author: george
 * @Date: 2018/6/22-11-27
 * @Description:
 */

public class TestDto implements Serializable {
    private String appId;

    private String robotId;
    private Integer number;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
