package com.wakeup.forever.wakeup.http.download;

/**
 * Created by Wesly186 on 2016/6/12.
 */
public interface ProgressListener {
    /**
     * @param progress 已经下载或上传字节数
     * @param total    总字节数
     * @param done     是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
