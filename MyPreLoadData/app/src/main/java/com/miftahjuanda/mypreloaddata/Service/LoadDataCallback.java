package com.miftahjuanda.mypreloaddata.Service;

public interface LoadDataCallback {
    void onPreLoad();
    void onProgressUpdate(long progress);
    void onLoadSuccess();
    void onLoadFailed();
    void onLoadCancel();
}
