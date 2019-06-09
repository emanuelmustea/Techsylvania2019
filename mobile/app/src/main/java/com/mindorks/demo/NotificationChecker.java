package com.mindorks.demo;


import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.biabe.DatabaseFunctionsGenerator.Models.Notification;
import com.example.biabe.DatabaseFunctionsGenerator.Notifications;

import java.util.Date;
import java.util.List;

import co.intentservice.chatui.models.ChatMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationChecker extends IntentService {
    private Date lastNotification;

    private void createNotificationChannel() {


        CharSequence name = "safas";
        String description = "hdfhdfhdf";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("1", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void sendNotification(String title, String msg) {

        NotificationManager notifManager = getSystemService(NotificationManager.class);
        if(notifManager.getNotificationChannel("1") == null)
            createNotificationChannel();


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ar)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ChatActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        //delete notification after was clicked
        mBuilder.setAutoCancel(true);


        mBuilder.setContentIntent(contentIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, mBuilder.build());

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        lastNotification = new Date(0);

        while (true)
        {
            synchronized (this) {
                try {
                    Notifications.getNotifications(new Callback<List<Notification>>() {
                        @Override
                        public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                            List<Notification> notifications = response.body();

                            if(0 < notifications.size()) {
                                int last = notifications.size() - 1;

                                if(lastNotification.before(notifications.get(last).getCreationTime())) {

                                    sendNotification(notifications.get(last).getTitle(),notifications.get(last).getMessage());
                                    lastNotification = notifications.get(last).getCreationTime();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Notification>> call, Throwable t) {

                        }
                    });

                    wait(3000);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }
            }
        }
    }


    public NotificationChecker()
    {
        super("Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d(MyIntentServiceActivity.TAG_INTENT_SERVICE, "MyIntentService onCreate() method is invoked.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

