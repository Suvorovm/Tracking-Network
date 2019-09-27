package com.example.networktracking.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Debug;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.networktracking.MainActivity;
import com.example.networktracking.R;

public class LocathionService extends Service {
    private NotificationManager notificationManager;
    public static final int DEFAULT_NOTIFICATION_ID = 101;
    private BroadcastReceiver mReceiver;

    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new Receiver();
        registerReceiver(mReceiver, filter);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        //Send Foreground Notification
        //sendNotification("Ticker","Title","Text");
        startForeground();
        startService(new Intent(this, LocathionService.class));
        return START_REDELIVER_INTENT;
    }

    private void doTask() {
        Log.d("Work","current time");
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

   public void startForeground() {
       //Log.d(LOCK_SCREAN_SERVICE,"Работа в серивисе");
       Notification notification = new NotificationCompat.Builder(this)
               .setContentTitle(getResources().getString(R.string.app_name))
               .setTicker(getResources().getString(R.string.app_name))
               .setContentText("Running")
               .setSmallIcon(R.mipmap.ic_launcher)
               .setContentIntent(null)
               .setOngoing(true)
               .build();
       Log.d("Serv","result");
       startForeground(9999, notification);
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