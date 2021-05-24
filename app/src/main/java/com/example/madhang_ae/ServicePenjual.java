package com.example.madhang_ae;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServicePenjual extends Service {
    public ServicePenjual() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Test Service","Service Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Test Service","Service Started");
        return START_STICKY;
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