package com.example.networktracking.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.networktracking.MainActivity;
import com.example.networktracking.R;

public class LocathionService extends Service {
    private NotificationManager notificationManager;
    public static final int DEFAULT_NOTIFICATION_ID = 101;

    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        //Send Foreground Notification
        sendNotification("Ticker","Title","Text");
       // doTask();
        return START_REDELIVER_INTENT;
    }


    public void sendNotification(String Ticker,String Title,String Text) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(contentIntent)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.test)
                .setTicker(Ticker)
                .setContentTitle(Title)
                .setContentText(Text)
                .setWhen(System.currentTimeMillis());
        Log.d("Work","current time");
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT<=15) {
            notification = builder.getNotification();
        }else{
            notification = builder.build();
        }

        startForeground(DEFAULT_NOTIFICATION_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        notificationManager.cancel(DEFAULT_NOTIFICATION_ID);
        stopSelf();
    }
}