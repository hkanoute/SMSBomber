package com.example.tasklist;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

class SMSService extends Service {
    private static final String TAG = "SMSService";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static boolean active;
    public static int countSMSReceived;
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(SMS_RECEIVED);
        registerReceiver(new SMSReceiver(), filter);
        Intent i = new Intent(this, SMSService.class);
        this.startService(i);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}