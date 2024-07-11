package com.example.custom_gesture_detection_sensorbased;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null) {
                    switch (action) {
//                        case "screen":
//                            Intent screen=new Intent(Intent.ACTION_SHOW_APP_INFO);
//                            startActivity(screen);
//                            break;
                        case "dial":
                            Intent dialerIntent = new Intent(Intent.ACTION_DIAL);
                            startActivity(dialerIntent);
                            break;
                        case "camera":
                            // Open the camera
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivity(cameraIntent);
                            break;
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("screen");
        intentFilter.addAction("dial");
        intentFilter.addAction("camera");
        registerReceiver(broadcastReceiver, intentFilter);

        // Start the background service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, service_background_sensor.class));
        } else {
            startService(new Intent(this, service_background_sensor.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(this, "Welcome ", Toast.LENGTH_SHORT).show();
//    }
