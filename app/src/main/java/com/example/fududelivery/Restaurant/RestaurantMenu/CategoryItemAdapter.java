package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        RelativeLayout rlContainer = convertView.findViewById(R.id.childCategoryLayout);
        EditText newCategoryEditText = convertView.findViewById(R.id.newCategoryEditText);

        if (currentItem.getName().isEmpty()) {
            newCategoryEditText.setVisibility(View.GONE);
        } else {
            categoryNameText.setText(currentItem.getName());
            categoryNameText.setVisibility(View.VISIBLE);
            categoryEditText.setVisibility(View.GONE);
            newCategoryEditText.setVisibility(View.GONE);
        }

        categoryNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryNameText.setVisibility(View.GONE);
                newCategoryEditText.setVisibility(View.VISIBLE);
                newCategoryEditText.setText(currentItem.getName());
            }
        });

        firebaseFirestore.collection("Catelogy").whereEqualTo("CateName", currentItem.getName()).whereEqualTo("userUid", userSessionManager.getUserInformation()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    cateID = documentSnapshot.getId();

                    Intent MenuIntent = new Intent(getContext(), CategoryDetail.class);
                    MenuIntent.putExtra("nameMenu", currentItem.getName());
                    MenuIntent.putExtra("cateID", cateID);
                }
            }
        });

        rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Catelogy").whereEqualTo("CateName", currentItem.getName()).whereEqualTo("userUid", userSessionManager.getUserInformation()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            cateID = documentSnapshot.getId();

                            Intent MenuIntent = new Intent(getContext(), CategoryDetail.class);
                            MenuIntent.putExtra("nameMenu", currentItem.getName());
                            MenuIntent.putExtra("cateID", cateID);
                            getContext().startActivity(MenuIntent);
                            if (getContext() instanceof Activity) {
                                ((Activity) getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                ((Activity) getContext()).finish();
                            }
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

        categoryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    categoryEditText.setVisibility(View.GONE);
                    categoryNameText.setText(categoryEditText.getText().toString());
                    categoryNameText.setVisibility(View.VISIBLE);

                    Map<String, Object> data = new HashMap<>();
                    data.put("CateName", categoryEditText.getText().toString());
                    data.put("userUid", userSessionManager.getUserInformation());
                    firebaseFirestore.collection("Catelogy").add(data);

                    firebaseFirestore.collection("Catelogy").whereEqualTo("CateName", currentItem.getName()).whereEqualTo("userUid", userSessionManager.getUserInformation()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                cateID = documentSnapshot.getId();
                            }
                        }
                    });
                    currentItem.setName(categoryEditText.getText().toString());
                    notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        newCategoryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    newCategoryEditText.setVisibility(View.GONE);
                    categoryNameText.setText(newCategoryEditText.getText().toString());
                    categoryNameText.setVisibility(View.VISIBLE);

                    firebaseFirestore.collection("Catelogy").document(cateID).update("CateName", newCategoryEditText.getText().toString());;
                    currentItem.setName(newCategoryEditText.getText().toString());
                    notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        return convertView;
    }

    private void showDeleteDialog(String cateID, Category currentItem) {
        new AlertDialog.Builder(getContext()).setTitle("Cancel").setMessage("Do you really want to delete this category folder and all the menu inside it?").setPositiveButton("OK", (dialog, which) -> {
            firebaseFirestore.collection("Catelogy").document(cateID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    remove(currentItem);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("Debug", "Error deleting document", e);
                }
            });
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).create().show();
    }
}
