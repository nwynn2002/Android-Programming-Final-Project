package com.example.finalprojectapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class SendNotification extends BroadcastReceiver {
    // The below parameters are some user-defined constants for creating notifications.
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    public static final String CHANNEL_ID = "Channel1";
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;


    @Override
    public void onReceive(Context context, Intent intent) {
        //create an instance of NotificationManager, call getSystemService(), passing in the NOTIFICATION_SERVICE constant.
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;

        Notification notification = getNotification(context,"Congrats, you did your reading!!") ;

        //the device running the app has Android SDK 26 or up
        //createNotificationChannel method requires API level 26 or higher.
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            //Notifications may have different importance and priorities values.
            //https://developer.android.com/develop/ui/views/notifications/channels
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        notificationManager.notify(0 , notification) ; //Here notification id is user-defined. deliver the notification
    }

    private Notification getNotification(Context context, String s) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(s) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ; //the notification cancels itself automatically
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;  //return notification object
    }
}
