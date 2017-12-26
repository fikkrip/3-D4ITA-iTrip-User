package com.ppl.fikkrip.itrip.controller.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import com.ppl.fikkrip.itrip.R;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by Pinky Cindy on 11/19/17.
 */

public class Notifikasireceiver extends BroadcastReceiver {
    MediaPlayer mp;
    String idplanning;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
//        Toast.makeText(context, "Alarm aktif!", Toast.LENGTH_LONG).show();
//        mp = MediaPlayer.create(context, R.raw.havana);
//        mp.start();

        String id = intent.getStringExtra("idPlanning");
        String judul = intent.getStringExtra("judul");
        NotificationManager notificationManager;
        Intent mIntent = new Intent(context, NotifikasiActivity.class);
        intent.putExtra("idPlanning", "2");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logotrip)
                .setTicker("notif starting")
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("i-Trip | "+judul)
                .setContentText("Jangan Lupa Anda punya Rencana Perjalanan Hari ini ! ^^");

        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());

    }




}
