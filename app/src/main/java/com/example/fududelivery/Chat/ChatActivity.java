package com.example.fududelivery.Chat;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private List<Chat> chatList = new ArrayList<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private UserSessionManager userSessionManager;
    private String senderId;
    private ChatAdapter chatAdapter;
    private Boolean isFirstTimeOpen = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userSessionManager = new UserSessionManager(this);
        senderId = "khtF55v1RuSywETvSRoFg87qeuA2";

        RecyclerView rvChatMessage = findViewById(R.id.rv_chat_message);
        rvChatMessage.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, chatList);
        rvChatMessage.setAdapter(chatAdapter);

        loadChatMessages(rvChatMessage);

        AppCompatEditText chatEdt = findViewById(R.id.et_chat_message);
        AppCompatImageView sendBtn = findViewById(R.id.iv_chat_send);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatEdt.getText().toString().trim();
                if (message.isEmpty()) {
                    return;
                }

                sendMessage(message);
                chatEdt.setText("");
            }
        });

        notifyMessageComing();
    }

    private void loadChatMessages(RecyclerView rvChatMessage) {
        firestore.collection("Chat")
                .whereEqualTo("receiverId", userSessionManager.getUserInformation())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Chat chat = new Chat(
                                        documentSnapshot.getString("senderId"),
                                        documentSnapshot.getString("receiverId"),
                                        documentSnapshot.getString("time"),
                                        documentSnapshot.getString("body"),
                                        documentSnapshot.getString("senderName"),
                                        userSessionManager.getUserName()
                                );
                                chatList.add(chat);
                            }
                            loadSenderMessages(rvChatMessage);
                        } else {
                            Log.v(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void loadSenderMessages(RecyclerView rvChatMessage) {
        firestore.collection("Chat")
                .whereEqualTo("senderId", userSessionManager.getUserInformation())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Chat chat = new Chat(
                                        documentSnapshot.getString("senderId"),
                                        documentSnapshot.getString("receiverId"),
                                        documentSnapshot.getString("time"),
                                        documentSnapshot.getString("body"),
                                        userSessionManager.getUserName(),
                                        documentSnapshot.getString("senderName")
                                );
                                chatList.add(chat);
                            }
                            sortChatListByTime();
                            chatAdapter.notifyDataSetChanged();
                        } else {
                            Log.v(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void sortChatListByTime() {
        Collections.sort(chatList, new Comparator<Chat>() {
            @Override
            public int compare(Chat o1, Chat o2) {
                return Long.compare(Long.parseLong(o1.getTime()), Long.parseLong(o2.getTime()));
            }
        });
    }

    private void sendMessage(String message) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        Map<String, String> data = new HashMap<>();
        data.put("receiverId", senderId);
        data.put("senderId", userSessionManager.getUserInformation());
        data.put("time", currentTime);
        data.put("body", message);
        data.put("senderName", userSessionManager.getUserName());

        Chat chat = new Chat(
                userSessionManager.getUserInformation(),
                senderId,
                currentTime,
                message,
                userSessionManager.getUserName(),
                "Man"
        );

        firestore.collection("Chat").add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            chatList.add(chat);
                            sortChatListByTime();
                            chatAdapter.notifyDataSetChanged();
                            Log.d(TAG, "Message sent successfully");
                        } else {
                            Log.e(TAG, "Error sending message", task.getException());
                        }
                    }
                });
    }

    private void notifyMessageComing() {
        CollectionReference chatCollection = firestore.collection("Chat");
        chatCollection.addSnapshotListener(MetadataChanges.INCLUDE, (value, error) -> {
            if (error != null) {
                return;
            }

            if (value != null) {
                if(isFirstTimeOpen) {
                    isFirstTimeOpen = false;
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            DocumentSnapshot doc = dc.getDocument();
                            String receiverId = doc.getString("receiverId");
                            if (receiverId.equals(userSessionManager.getUserInformation())) {
                                chatList.add(new Chat(doc.getString("senderId"), doc.getString("receiverId"), doc.getString("time"), doc.getString("body"), doc.getString("senderName"), userSessionManager.getUserName()));
                                sortChatListByTime();
                                chatAdapter.notifyDataSetChanged();
                            }
                            break;
                    }
                }
            }
        });
    }
}
