package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryItemAdapter extends ArrayAdapter<Category> {
    private Context mContext;
    private FirebaseFirestore firebaseFirestore;
    private UserSessionManager userSessionManager;
    private int mResource;
    private String cateID;

    public CategoryItemAdapter(Context context, int resource, ArrayList<Category> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        firebaseFirestore = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category currentItem = getItem(position);

        if (currentItem == null) {
            return convertView;
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        TextView categoryNameText = convertView.findViewById(R.id.categoryNameText);
        EditText categoryEditText = convertView.findViewById(R.id.categoryEditText);
        ImageView deleteImage = convertView.findViewById(R.id.deleteImageView);

        categoryEditText.setVisibility(View.GONE);
        categoryNameText.setText(currentItem.getName());
        categoryNameText.setVisibility(View.VISIBLE);

        categoryNameText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                categoryNameText.setVisibility(View.GONE);
                categoryEditText.setVisibility(View.VISIBLE);
                categoryEditText.setText(currentItem.getName());
                return false;
            }
        });

        categoryNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Catelogy")
                        .whereEqualTo("CateName", currentItem.getName())
                        .whereEqualTo("userUid", userSessionManager.getUserInformation()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                    cateID = documentSnapshot.getId();

                                    Intent MenuIntent = new Intent(getContext(), CategoryDetail.class);
                                    MenuIntent.putExtra("nameMenu", currentItem.getName());
                                    MenuIntent.putExtra("cateID", cateID);
                                    getContext().startActivity(MenuIntent);
                                }
                            }
                        });
            }
        });


        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(cateID, currentItem);
            }
        });


        categoryEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String newCategoryName = categoryEditText.getText().toString();
                    if (!newCategoryName.equals(currentItem.getName())) {
                        firebaseFirestore.collection("Catelogy")
                                .whereEqualTo("CateName", currentItem.getName())
                                .whereEqualTo("userUid", userSessionManager.getUserInformation())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            QuerySnapshot queryDocumentSnapshots = task.getResult();
                                            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                    String docId = documentSnapshot.getId();

                                                    Map<String, Object> updates = new HashMap<>();
                                                    updates.put("CateName", newCategoryName);

                                                    firebaseFirestore.collection("Catelogy")
                                                            .document(docId)
                                                            .update(updates)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d("CategoryDetail", "DocumentSnapshot successfully updated!");
                                                                    currentItem.setName(newCategoryName); // Update the currentItem name
                                                                    categoryNameText.setText(newCategoryName); // Update the displayed name
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.e("CategoryDetail", "Error updating document", e);
                                                                }
                                                            });
                                                }
                                            } else {
                                                Log.d("CategoryDetail", "No matching documents found");
                                            }
                                        } else {
                                            Log.e("CategoryDetail", "Error getting documents: ", task.getException());
                                        }
                                        categoryEditText.setVisibility(View.GONE);
                                        categoryNameText.setVisibility(View.VISIBLE);
                                    }
                                });
                    } else {
                        categoryEditText.setVisibility(View.GONE);
                        categoryNameText.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                return false;
            }
        });

        return convertView;
    }

    private void showDeleteDialog(String cateID, Category currentItem) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Cancel")
                    .setMessage("Do you really want to delete this category folder and all the menu inside it?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Code to execute when OK is clicked
                        // For example, finish the activity or close the fragment
                        firebaseFirestore.collection("Catelogy").document(cateID)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Debug", "DocumentSnapshot successfully deleted!");
                                        remove(currentItem);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Debug", "Error deleting document", e);
                                    }
                                });
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
    }
}
