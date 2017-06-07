package com.micazook.screenrecorder;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.micazook.screenrecorder.util.Constant;

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra(Constant.ACTION_NAME);
        switch (action) {
            case "exit":
                exitService(context, intent);
                break;
        }
    }

    private void exitService(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ScreenRecordService.class);
        context.stopService(serviceIntent);
    }
}
