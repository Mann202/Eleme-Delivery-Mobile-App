package com.example.fududelivery.Service;

import android.Manifest;
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
import com.google.firebase.firestore.*;

public class RestaurantNotificationService extends Service {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserSessionManager userSessionManager;
    Boolean firstTimeLaunchedService;

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
                                    System.out.println("Document with matching userUid added: " + doc.getId());
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "ORDER_CHANNEL_ID")
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "OrderChannel";
            String description = "Channel for order notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("ORDER_CHANNEL_ID", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
