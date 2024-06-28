package com.example.fududelivery.Shipper.ShipperOrderDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fududelivery.R;

import java.util.ArrayList;

public class RestaurantItemAdapter extends ArrayAdapter<ItemRestaurantOrder>{
    private Context mContext;
    private int mResource;

    public RestaurantItemAdapter(Context context, int resource, ArrayList<ItemRestaurantOrder> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemRestaurantOrder currentItem = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        TextView itemQuantity = convertView.findViewById(R.id.itemQuantity);
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemPrice = convertView.findViewById(R.id.itemPrice);
        TextView itemDescription = convertView.findViewById(R.id.itemDescription);

        itemQuantity.setText(currentItem.getItemQuantity());
        itemName.setText(currentItem.getItemName());
        itemPrice.setText(currentItem.getItemPrice());
        itemDescription.setText(currentItem.getItemDescription());

        return convertView;
    }
}
