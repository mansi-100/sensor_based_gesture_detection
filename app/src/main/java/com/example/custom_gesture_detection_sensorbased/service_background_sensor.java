package com.example.custom_gesture_detection_sensorbased;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class service_background_sensor extends Service implements SensorEventListener {
    private static final int NOTIFICATION_ID = 123;

    private static final int threshold=13;
    int shake_count=0;
    long last_shake=0;
    SensorManager sensorManager;
    Sensor accelerometer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer==null){
            Toast.makeText(this, "Accelerometer sensor is not avaliable in device", Toast.LENGTH_SHORT).show();
        }
        else{
            sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(NOTIFICATION_ID, createNotificationChannel());
        return START_STICKY;
    }

//    private Notification createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    "101",
//                    "mansi",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(serviceChannel);
//
//            // Build the notification using the channel
//            return new NotificationCompat.Builder(this, "101")
//                    .setContentTitle("Your App Name")
//                    .setContentText("Running in background")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .build();
//        }
//        return null;
//    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x=sensorEvent.values[0];
        float y=sensorEvent.values[1];
        float z=sensorEvent.values[2];

        long curtime=System.currentTimeMillis();
        double acceleration=Math.sqrt(x*x+y*y+z*z)-SensorManager.GRAVITY_EARTH;
        if(acceleration>threshold){
            last_shake=curtime;
            shake_count++;
            switch (shake_count) {
                case 1:
                    Intent whatsappIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    if (whatsappIntent != null) {
                        whatsappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(whatsappIntent);
                    } else {
                        Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    Intent dialIntent = new Intent("dial");
                    sendBroadcast(dialIntent);
                    break;
                case 3:
                    Intent cameraIntent = new Intent("camera");
                    sendBroadcast(cameraIntent);
                    break;
                default:
                    break;
            }

            // Reset shake_count if you want to start counting shakes again
             shake_count = 0;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

}
