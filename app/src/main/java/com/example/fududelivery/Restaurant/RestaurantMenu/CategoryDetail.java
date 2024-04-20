package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.R;

import java.util.ArrayList;

public class CategoryDetail extends AppCompatActivity {

    private ListView menuListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> categories;

    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String nameMenu = data.getStringExtra("nameMenu");
                            String priceMenu = data.getStringExtra("priceMenu");
                            categories.add(nameMenu);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorydetail);

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");

        TextView itemCategoryView = findViewById(R.id.CategoryTitle);
        itemCategoryView.setText(categoryName);

        AppCompatButton addBtn = findViewById(R.id.addMenu);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMenu = new Intent(v.getContext(), NewMenu.class);
                startForResult.launch(intentMenu);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        categories = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, categories)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_menu, parent, false);
                }

                TextView textView = convertView.findViewById(R.id.text1);
                textView.setText(categories.get(position));

                return convertView;
            }
        };

        menuListView = findViewById(R.id.menuListView);
        menuListView.setAdapter(adapter);
    }
}
