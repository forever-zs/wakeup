package com.wakeup.forever.wakeup.http.download;

import java.io.Serializable;

/**
 * Created by Wesly186 on 2016/6/13.
 */
public class ProgressModel implements Serializable {
    //标记是哪一个下载任务
    private String key;
    //当前读取字节长度
    private long currentBytes;
    //总字节长度
    private long contentLength;
    //是否读取完成
    private boolean done;

    public ProgressModel(String key, long currentBytes, long contentLength, boolean done) {
        this.key = key;
        this.currentBytes = currentBytes;
        this.contentLength = contentLength;
        this.done = done;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "ProgressModel{" +
                "currentBytes=" + currentBytes +
                ", contentLength=" + contentLength +
                ", done=" + done +
                '}';
    }
}
