package com.example.tasklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    public SMSReceiver(){
        Log.v("SMSReceiver","SMSReceiver()");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("SMSReceiver","onReceive()");
        if(SMSService.active) SMSService.countSMSReceived++;
    }
}