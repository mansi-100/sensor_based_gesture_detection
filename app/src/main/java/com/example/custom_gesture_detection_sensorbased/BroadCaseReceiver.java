package com.example.custom_gesture_detection_sensorbased;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

public class BroadCaseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("Screen_on")){
            Intent screenon=new Intent(Intent.ACTION_SCREEN_ON);
            context.startActivity(screenon);
        }
        else if(intent.getAction().equals("dial")){
            Intent dial=new Intent(Intent.ACTION_DIAL);
            context.startActivity(dial);
        }
        else if(intent.getAction().equals("image_capture")){
            Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            context.startActivity(camera);
        }
    }
}
