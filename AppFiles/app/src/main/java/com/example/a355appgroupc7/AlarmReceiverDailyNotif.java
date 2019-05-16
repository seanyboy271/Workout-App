package com.example.a355appgroupc7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Objects;

public class AlarmReceiverDailyNotif extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Notification received",  "YAY");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
        SharedPreferences.Editor sharedPrefEditor = prefs.edit();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, CreateTeamActivity.class);

        try {
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);



            PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                    notificationIntent, 0);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("default",
                        "Daily Notification",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Daily Notification");
                if (nm != null) {
                    nm.createNotificationChannel(channel);
                }
            }

            NotificationCompat.Builder b = new NotificationCompat.Builder(context, "default");
            b.setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setTicker("Time to log all the things!")
                    .setContentTitle("Daily Notification")
                    .setContentText("Don't forget to log your workout today!")
                    .setContentInfo("INFO")
                    .setContentIntent(pendingI);



            if (nm != null) { //Check if the challenge is over and if so, do not set a repeat date/stop notifying
                //if(endDate != -1 && endDate )
                nm.notify(1, b.build());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}