package com.example.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.weather.FullscreenActivity.Global1;
//import com.example.weather.

/**
 * Created by Belal on 8/29/2017.
 */

//class extending the Broadcast Receiver
public class AlarmService extends BroadcastReceiver {

    //the method will be fired when the alarm is triggerred
    @Override
    public void onReceive(Context context, Intent intent) {

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday
        //FullscreenActivity.Global1.Counter=5;
        //Global1.Counter++;
        Global1.ServiceRun=true;
        Log.d("AlarmService", "Alarm just fired"); //PREV

    }

}