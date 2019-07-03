package com.training.domain;

/**
 * george 2019/6/26 15:55
 */
public class UserRequestSupport extends UserRequest {
    public UserRequestSupport() {
    }
    public UserRequestSupport(String robotId, String userId, String date, Integer count) {
        super(robotId, userId, date, count);
    }
}
