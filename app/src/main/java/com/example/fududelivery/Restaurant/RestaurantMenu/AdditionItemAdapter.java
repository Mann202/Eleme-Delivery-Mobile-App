package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.R;

import java.util.List;

public class AdditionItemAdapter extends ArrayAdapter<Addition> {
    private Context mContext;
    private int mResources;

    public AdditionItemAdapter(@NonNull Context context, int resource, @NonNull List<Addition> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResources = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Addition currentItem = getItem(position);

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResources, parent, false);
        }

        if(currentItem == null) {
            return convertView;
        }

        AppCompatEditText nameAdditionEditText = convertView.findViewById(R.id.additionNameEditText);
        AppCompatEditText priceAdditionEditText = convertView.findViewById(R.id.additionPriceEditText);

        return convertView;
    }
}
