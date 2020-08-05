package com.example.sudokuscorer;


import android.app.Application;
import android.content.Context;

//ＭａｉｎＡｃｔｉｖｉｔｙ以外でリソース取得するためのクラス
public class SupportApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}