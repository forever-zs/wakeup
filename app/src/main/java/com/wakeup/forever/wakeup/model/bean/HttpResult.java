package com.wakeup.forever.wakeup.model.bean;

/**
 * Created by forever on 2016/8/4.
 */
public class HttpResult <T> {
    private int resultCode;

    private String message;

    private int state;

    private T data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getState() {
        return state;
    }

    public void setState(int resultCode) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
