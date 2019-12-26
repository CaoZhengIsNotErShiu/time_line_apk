package com.sc.per.time_line.entity;

/**
 * 返回数据
 */
public class HttpResult {

    /**
     * 返回状态
     */
    private int status;
    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回对象
     */
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
