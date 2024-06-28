package com.example.fududelivery.Service;

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

public class RestaurantNotificationService extends Service {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserSessionManager userSessionManager;
    Boolean firstTimeLaunchedService;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(1, getSilentNotification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startFirestoreListener();
        return START_STICKY;
    }

    private void startFirestoreListener() {
        firstTimeLaunchedService = true;
        CollectionReference colRef = db.collection("Orders");
        userSessionManager = new UserSessionManager(getApplicationContext());
        colRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.err.println("Listen failed: " + error);
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
                                String userUidInDoc = doc.getString("ResID");
                                String userUid = userSessionManager.getUserInformation();

                                if (userUidInDoc != null && userUidInDoc.equals(userUid)) {
                                    showNotification("New Order From Customer", "A new order has been add to your order list, please refresh your order app!");
                                }
                                break;
                        }
                    }
                }
            }
        });
    }

    private void showNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "ORDER_CHANNEL_ID").setSmallIcon(R.drawable.logo_eleme).setContentTitle(title).setContentText(content).setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification getSilentNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "ORDER_CHANNEL_ID").setSmallIcon(R.drawable.logo_eleme).setPriority(NotificationCompat.PRIORITY_MIN).setSilent(true);

        return builder.build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel("ORDER_CHANNEL_ID", "Order Notifications", NotificationManager.IMPORTANCE_HIGH);
            serviceChannel.setDescription("Channel for order notifications");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}