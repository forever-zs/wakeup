package com.wakeup.forever.wakeup.http.download;


import java.io.IOException;

import okhttp3.Call;


/**
 * Created by Wesly186 on 2016/6/15.
 */
public class FailureModel {
    private String key;
    private Call call;
    private IOException e;

    public FailureModel(String key, Call call, IOException e) {
        this.key = key;
        this.call = call;
        this.e = e;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public IOException getE() {
        return e;
    }

    public void setE(IOException e) {
        this.e = e;
    }
}
