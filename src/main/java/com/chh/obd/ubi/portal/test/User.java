package com.chh.obd.ubi.portal.test;

/**
 * Created by 申卓 on 2017/7/13.
 */
public class User {
    private String name;
    private String msg;

    public User() {
    }

    public User(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
