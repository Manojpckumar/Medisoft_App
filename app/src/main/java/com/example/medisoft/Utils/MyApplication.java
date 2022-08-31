package com.example.medisoft.Utils;
import android.app.Application;
import android.content.Context;

//FOR NAMING THE ENTIRE APP WITH THE CONTEXT
public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }
}