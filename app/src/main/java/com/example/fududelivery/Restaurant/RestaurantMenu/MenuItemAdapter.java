package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MenuItemAdapter extends ArrayAdapter<Menu> {
    private Context mContext;
    private FirebaseFirestore firebaseFirestore;
    private UserSessionManager userSessionManager;
    private int mResource;
    private String menuID;

    public MenuItemAdapter(Context context, int resource, ArrayList<Menu> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        firebaseFirestore = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Menu currentItem = getItem(position);

        if (currentItem == null) {
            return convertView;
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        TextView menuName = convertView.findViewById(R.id.menuName);
        RelativeLayout rlContainter = convertView.findViewById(R.id.childCategoryLayout);
        ImageView deleteMenu = convertView.findViewById(R.id.deleteImageView);
        menuName.setText(currentItem.getName());

        rlContainter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Food").whereEqualTo("ResID", userSessionManager.getUserInformation()).whereEqualTo("FoodName", currentItem.getName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            menuID = documentSnapshot.getId();
                            Intent menuDetailItent = new Intent(getContext(), MenuDetail.class);
                            menuDetailItent.putExtra("menuID", menuID);
                            getContext().startActivity(menuDetailItent);
                            if (getContext() instanceof Activity) {
                                ((Activity) getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        }
                    }
                });
            }
        });

        deleteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Food").document(menuID).delete();
                remove(currentItem);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
