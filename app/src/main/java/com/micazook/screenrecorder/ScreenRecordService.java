package com.micazook.screenrecorder;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.micazook.screenrecorder.util.Constant;

public class ScreenRecordService extends Service {
    private View myView;

    @Override
    public void onCreate() {
        // If the service is not already running, the system first calls onCreate()
        super.onCreate();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER;

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.overlay_view, null);
        wm.addView(myView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Called after onCreate()

        Intent actionIntent = new Intent(this, ActionReceiver.class);
        actionIntent.putExtra(Constant.ACTION_NAME, "exit");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, actionIntent, 0);

        Notification.Action exitAction = new Notification.Action.Builder(R.drawable.ic_camera, "Exit", pendingIntent)
                .build();

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message))
                .setSmallIcon(R.drawable.ic_camera)
                .addAction(exitAction)
                .build();

        startForeground(Constant.ONGOING_NOTIFICATION_ID, notification);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        if (myView != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(myView);
            myView = null;
        }
    }
}