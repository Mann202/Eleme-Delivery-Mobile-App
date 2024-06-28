package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SizeItemAdapter extends ArrayAdapter<Size> {
    private Context mContext;
    private FirebaseFirestore firebaseFirestore;
    private UserSessionManager userSessionManager;
    private int mResource;

    public SizeItemAdapter(@NonNull Context context, int resource, ArrayList<Size> item) {
        super(context, resource, item);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Size currentItem = getItem(position);

        if (currentItem == null) {
            return convertView;
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        AppCompatEditText nameSizeEditText = convertView.findViewById(R.id.nameEditTextSize);
        AppCompatEditText priceSizeEditText = convertView.findViewById(R.id.priceEditTextSize);

        nameSizeEditText.setText(currentItem.getNameSize());
        priceSizeEditText.setText(currentItem.getPriceSize());

        return convertView;
    }
}
