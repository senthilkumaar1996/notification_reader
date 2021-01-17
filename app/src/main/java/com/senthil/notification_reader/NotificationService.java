package com.senthil.notification_reader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationService extends NotificationListenerService {
    Context context;

    @Override

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();

    }
    @Override

    public void onNotificationPosted(StatusBarNotification notification) {

        String Texts= "No Notification Message";
        String pack = notification.getPackageName();
        String ticker = "";
        if(notification.getNotification().tickerText !=null) {
            ticker = notification.getNotification().tickerText.toString();
        }
        Bundle extras = notification.getNotification().extras;
        String title = extras.getString("android.title");
        if (extras.getCharSequence("android.text") !=null){
            Texts = extras.getCharSequence("android.text").toString();
        }

        Log.i("Package",pack);
        Log.i("Ticker",ticker);
        Log.i("Title",title);
        Log.i("Text",Texts);

        Intent incmng_notify = new Intent("Msg");
        incmng_notify.putExtra("package", pack);
        incmng_notify.putExtra("ticker", ticker);
        incmng_notify.putExtra("title", title);
        incmng_notify.putExtra("text", Texts);

        LocalBroadcastManager.getInstance(context).sendBroadcast(incmng_notify);


    }
}

