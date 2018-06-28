package com.deep.CountLicenseHistory;

public class RobotLicenseCount {
    private String id;
    private String appId;
    private String robotId;
    private String userId;
    /*@Field("projectCode")
    private String projectCode;*/
    //总的license数
    private Integer totalLicense;
    //已激活license数
    private Integer activeLicense;
    //剩余未激活license数
    private Integer remainderLicense;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTotalLicense() {
        return totalLicense;
    }

    public void setTotalLicense(Integer totalLicense) {
        this.totalLicense = totalLicense;
    }

    public Integer getActiveLicense() {
        return activeLicense;
    }

    public void setActiveLicense(Integer activeLicense) {
        this.activeLicense = activeLicense;
    }

    public Integer getRemainderLicense() {
        return remainderLicense;
    }

    public void setRemainderLicense(Integer remainderLicense) {
        this.remainderLicense = remainderLicense;
    }
}
