package com.example.fududelivery.HelperFirebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private static FirebaseFirestore firestoreInstance;

    private FirebaseHelper() {
    }

    public static FirebaseFirestore getFirestoreInstance(Context context) {
        if (firestoreInstance == null) {
            synchronized (FirebaseHelper.class) {
                if (firestoreInstance == null) {
                    firestoreInstance = FirebaseFirestore.getInstance();
                    Log.d(TAG, "Firebase Firestore instance initialized.");
                }
            }
        }
        return firestoreInstance;
    }
}
