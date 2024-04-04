package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fududelivery.R;

public class CategoryDetail extends AppCompatActivity {
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
    }
}
