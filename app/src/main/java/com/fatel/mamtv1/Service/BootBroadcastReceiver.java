package com.fatel.mamtv1.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Monthon on 25/10/2558.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                // Set the alarm here.
                /*Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                int interval = 60*1000*1;
                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);*/
                if(UserManage.getInstance(context).getCurrentUser()!=null){
                    Intent i = new Intent(context, AlarmReceiver.class);
                    Bundle b = new Bundle();
                    b.putString("key", "first");
                    i.putExtras(b);
                    context.sendBroadcast(i);
                    Log.i("Boot", "Boot cpu complete can set");
                }
                else{
                    Log.i("Boot", "Boot cpu can't set");
                }

            }
        }
}
