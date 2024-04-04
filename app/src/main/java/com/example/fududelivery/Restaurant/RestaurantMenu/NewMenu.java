package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;
import com.google.android.material.textfield.TextInputLayout;

public class NewMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);

        Button doneButton = findViewById(R.id.confirmButton);
        TextInputLayout nameMenuEditText = findViewById(R.id.NameEditText);
        TextInputLayout priceMenuEditText = findViewById(R.id.PriceEditText);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                String nameMenu = nameMenuEditText.getEditText().toString();
                String priceMenu = priceMenuEditText.getEditText().toString();
                resultIntent.putExtra("nameMenu", nameMenu);
                resultIntent.putExtra("priceMenu", priceMenu);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
