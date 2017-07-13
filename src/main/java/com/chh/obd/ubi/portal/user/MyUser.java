package com.chh.obd.ubi.portal.user;

/**
 * Created by 申卓 on 2017/7/13.
 */
public class MyUser {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
