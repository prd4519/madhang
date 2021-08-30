package com.android.madhang_ae;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServicePenjual extends Service {
    public ServicePenjual() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Test Service","Service Started");

        return START_NOT_STICKY;
        
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Test Service","Service Destroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Test Service","Service Bind");
        return null;
    }

}