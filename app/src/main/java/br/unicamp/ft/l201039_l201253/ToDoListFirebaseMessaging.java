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
        this.builder = new NotificationCompat.Builder(this, "IDTODO")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_MAX);
    }

    @Override
    public void onNewToken(String s){
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        System.out.println("SERVICE --> Mensagem chegou");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(5643, builder.build());
        if (remoteMessage.getNotification() != null){
            System.out.println("SERVICE -->"+remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0){
            System.out.println("SERVICE --> DADOS: "+remoteMessage.getData());
        }
        mySendBroadcast();
    }

    private void mySendBroadcast(){
        /* Enviando um Broadcast Message */
        Intent intent = new Intent();
        intent.setAction("REDIRECTING");
        sendBroadcast(intent);
    }

}
