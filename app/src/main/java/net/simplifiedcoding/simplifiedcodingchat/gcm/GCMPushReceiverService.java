package net.simplifiedcoding.simplifiedcodingchat.gcm;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import net.simplifiedcoding.simplifiedcodingchat.R;
import net.simplifiedcoding.simplifiedcodingchat.helper.Constants;
import net.simplifiedcoding.simplifiedcodingchat.helper.Message;
import net.simplifiedcoding.simplifiedcodingchat.helper.NotificationHandler;

import java.util.List;


/**
 * Created by Belal on 4/15/2016.
 */


public class GCMPushReceiverService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String title = data.getString("title");
        String id = data.getString("id");
        sendNotification(message, title, id);
    }

    private void sendNotification(String message, String title, String id) {
        //Creating a broadcast intent
        Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
        //Adding notification data to the intent
        pushNotification.putExtra("message", message);
        pushNotification.putExtra("name", title);
        pushNotification.putExtra("id", id);

        //We will create this class to handle notifications
        NotificationHandler notificationHandler = new NotificationHandler(getApplicationContext());

        //If the app is in foreground
        if (!NotificationHandler.isAppIsInBackground(getApplicationContext())) {
            //Sending a broadcast to the chatroom to add the new message
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
        } else {
            //If app is in foreground displaying push notification
            notificationHandler.showNotificationMessage(title, message);
        }
    }


}