package br.unicamp.ft.l201039_l201253;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ToDoListFirebaseMessaging extends FirebaseMessagingService {

    private NotificationCompat.Builder builder;

    public ToDoListFirebaseMessaging() {

    }

    @Override
    public void onNewToken(String s){
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        this.builder = new NotificationCompat.Builder(this, "IDTODO")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("ToDo List")
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_MAX);

        notificationManager.notify(5643, builder.build());


    }
}
