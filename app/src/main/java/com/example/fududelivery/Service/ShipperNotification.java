package com.example.fududelivery.Service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class ShipperNotification extends Service {

    Boolean firstTimeLaunchedService;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startFirestoreListener();
        return START_STICKY;
    }

    private void startFirestoreListener() {
        firstTimeLaunchedService = true;
        CollectionReference colRef = db.collection("Orders");
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
                                String shipperID = doc.getString("ShipperID");
                                String shippingStatus = doc.getString("ShippingStatus");

                                if (shipperID != null && shipperID.equals("") && shippingStatus != null && shippingStatus.equals("Ready")) {
                                    showNotification("New Shipping Order", "New shipping orders, please refresh your orders list!");
                                }
                                break;
                        }
                    }
                }
            }
        });
    }


    private void showNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "SHIPPING_CHANNEL_ID")
                .setSmallIcon(R.drawable.logo_eleme)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        CharSequence name = "OrderShippingChannel";
        String description = "Channel for order shipping notifications";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("SHIPPING_CHANNEL_ID", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
