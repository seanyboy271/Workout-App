package com.example.a355appgroupc7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

public class AlarmReceiverChallengeStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Notification received in challenge start receiver",  "YAY");

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, CreateTeamActivity.class);

        try {
            PendingIntent pendingI = PendingIntent.getActivity(context, 1,
                    notificationIntent, 0);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("ChalStart",
                        "Challenge Start",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Challenge Start");
                if (nm != null) {
                    nm.createNotificationChannel(channel);
                }
            }

            NotificationCompat.Builder b = new NotificationCompat.Builder(context, "ChalStart");
            b.setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setTicker("Challenge Starting!")
                    .setContentTitle("Challenge Start")
                    .setContentText("You have a challenge that just started!")
                    .setContentInfo("INFO")
                    .setContentIntent(pendingI);

            if (nm != null) {
                nm.notify(1, b.build());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
