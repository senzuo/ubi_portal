package com.chh.obd.ubi.portal.common.token;

/**
 * Created by niow on 2017/7/18.
 */
public class Token {

    private String id;

    private Long userId;

    private Long expirationTime;

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
