package com.chh.obd.ubi.portal.common.response;

/**
 * Created by Administrator on 2016/3/1.
 */
public enum RestCode {

    SUCCESS(200, "请求成功"),
    OPTION_INCORRECT(300,"此操作不正确"),
    LOGIN_INVALID(301,"账号或密码不正确"),
    TARGET_IS_NULL(400, "请求内容不存在"),
    TARGET_IS_INCOMPLETE(401,"请求的目标内容不完整"),
    TARGET_EXISTED(402,"对象已经存在"),
    OPENID_LACK(403,"缺少openid"),
    ERROR(500,"服务器执行操作发生异常");

    RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public RestCode get(int code) {
        for (RestCode c : RestCode.values()) {
            if (c.getCode() == code) {
                return c;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
