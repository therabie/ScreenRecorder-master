package com.micazook.screenrecorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.micazook.screenrecorder.util.Constant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkDrawOverlayPermission();
//        Intent intent = new Intent(this, ScreenRecordService.class);
//        startService(intent);
        finish();
    }

    public void checkDrawOverlayPermission() {
        // check if we already  have permission to draw over other apps
        if (!Settings.canDrawOverlays(this)) {
            // if not construct intent to request permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            // request permission via start activity for result
            startActivityForResult(intent, Constant.REQUEST_CODE);
        } else {
            Intent intent = new Intent(this, ScreenRecordService.class);
            startService(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check if received result code is equal our requested code for draw permission
        if (requestCode == Constant.REQUEST_CODE) {
            // if so check once again if we have permission
            if (Settings.canDrawOverlays(this)) {
                // continue here - permission was granted
                Intent intent = new Intent(this, ScreenRecordService.class);
                startService(intent);
            }
        }
    }
}
