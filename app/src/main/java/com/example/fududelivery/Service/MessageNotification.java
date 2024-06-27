package com.example.fududelivery.Service;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;

public class MessageNotification extends Service {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Boolean firstTimeLaunchedService;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startFirestoneListener();
        return START_STICKY;
    }

    private void startFirestoneListener() {
        firstTimeLaunchedService = true;
        CollectionReference collectionReference = firestore.collection("Chat");
        UserSessionManager userSessionManager = new UserSessionManager(getApplicationContext());
        collectionReference.addSnapshotListener(MetadataChanges.INCLUDE, (value, error) -> {
            if (error != null) {
                return;
            }

            if (value != null) {
                if (firstTimeLaunchedService) {
                    firstTimeLaunchedService = false;
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            DocumentSnapshot doc = dc.getDocument();
                            String receiverId = doc.getString("receiverId");
                            String senderName = doc.getString("senderName");
                            String message = doc.getString("body");
                            if (receiverId.equals(userSessionManager.getUserInformation())) {
                                showNotificationMessage(senderName, message);
                            }
                            break;
                    }
                }
            }
        });
    }

    private void showNotificationMessage(String sender, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "MESSAGE_CHANNEL_ID")
                .setSmallIcon(R.drawable.logo_eleme)
                .setContentTitle(sender)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission for notifications not granted");
            return;
        }
        notificationManagerCompat.notify(2, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(1, getSilentNotification());
    }

    private Notification getSilentNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "SHIPPING_CHANNEL_ID")
                .setSmallIcon(R.drawable.logo_eleme)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setSilent(true);

        return builder.build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MessageChannel";
            String description = "Channel for message notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MESSAGE_CHANNEL_ID", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
