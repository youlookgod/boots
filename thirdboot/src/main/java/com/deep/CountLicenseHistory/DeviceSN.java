package com.deep.CountLicenseHistory;

import java.io.Serializable;

/**
 * @Author: george
 * @Date: 2018/6/22-11-51
 * @Description:
 */

public class DeviceSN implements Serializable {
    private String appId;

    private String robotId;
    private String sn;

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
