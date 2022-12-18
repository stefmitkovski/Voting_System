package com.example.voting_system;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

    public static final String ALERT = "alert";
    public static final String ALERT_DONE = "alert_done";

    public static final int ID_NOTIFICATION_DL_COMPLETE = 1234;

    private HandlerThread hThread;

    @Override
    public void onCreate() {
        super.onCreate();
        hThread = new HandlerThread("NotificationThread");
        hThread.start();
    }

    public NotificationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if(action.equals(ALERT)){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String title = intent.getStringExtra("title");
                    String time = intent.getStringExtra("time");

                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    String CHANNEL_ID = "my_channel_01";
                    CharSequence name = "my_channel";
                    String Description = "This is my channel";

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        int importance = NotificationManager.IMPORTANCE_LOW;
                        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                        mChannel.setDescription(Description);
                        //mChannel.enableLights(true);
                        //mChannel.setLightColor(Color.RED);
                        //mChannel.enableVibration(true);
                        //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                        //mChannel.setShowBadge(true);
                        manager.createNotificationChannel(mChannel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_baseline_info_24)
                            .setContentTitle(title)
                            .setContentText("Има уште: "+ time +" секунди")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                            .setAutoCancel(true)
                    Notification notification = builder.build();

                    manager.notify(ID_NOTIFICATION_DL_COMPLETE, notification);

                    Intent done = new Intent();
                    done.setAction(ALERT_DONE);
                    done.putExtra("username", intent.getStringExtra("username"));
                    done.putExtra("type",intent.getStringExtra("type"));
                    sendBroadcast(done);
                }
            };
            Handler handler = new Handler(hThread.getLooper());
            handler.post(runnable);
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}