package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.MainRestaurant;

import java.util.ArrayList;

public class RestaurantMenu extends AppCompatActivity {

    private ListView categoryListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantmenu);

        categoryListView = findViewById(R.id.categoryListView);
        Button addCategoryButton = findViewById(R.id.addCategoryButton);

        categories = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, categories)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_category, parent, false);
                }

                RelativeLayout childCategoryLayout = convertView.findViewById(R.id.childCategoryLayout);
                EditText editText = convertView.findViewById(R.id.categoryEditText);
                TextView textView = convertView.findViewById(R.id.text1);
                ImageView deleteView = convertView.findViewById(R.id.deleteImageView);
                TextView itemCategoryView = convertView.findViewById(R.id.ItemCategory);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            String newName = editText.getText().toString().trim();
                            if (!newName.isEmpty()) {
                                categories.set(position, newName);
                                textView.setText(newName);
                                textView.setVisibility(View.VISIBLE);
                                editText.setVisibility(View.GONE);
                                itemCategoryView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText(textView.getText());
                        textView.setVisibility(View.GONE);
                        editText.setVisibility(View.VISIBLE);
                        editText.requestFocus();
                    }
                });

                childCategoryLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), CategoryDetail.class);
                        intent.putExtra("categoryName", categories.get(position));
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

                deleteView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        categories.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                return convertView;
            }
        };

        categoryListView.setAdapter(adapter);

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCategory();
            }
        });
    }

    private void addNewCategory() {
        categories.add("New Category");
        adapter.notifyDataSetChanged();
    }
}
