package com.example.fududelivery.Restaurant.RestaurantMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuDetail extends AppCompatActivity {
    String menuID;
    FirebaseFirestore firebaseFirestore;
    UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);

        firebaseFirestore = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(this);

        AppCompatEditText nameTextField = findViewById(R.id.nameEditTextField);
        AppCompatEditText priceTextField = findViewById(R.id.priceTextField);

        Intent intent = getIntent();
        menuID = intent.getStringExtra("menuID");

        firebaseFirestore.collection("Food").document(menuID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot name = task.getResult();
                nameTextField.setText(name.getString("FoodName"));
                priceTextField.setText(name.getDouble("Price").toString());
            }
        });

        Button doneButton = findViewById(R.id.confirmButton);
        TextInputLayout nameMenuEditText = findViewById(R.id.NameEditText);
        TextInputLayout priceMenuEditText = findViewById(R.id.PriceEditText);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                String nameMenu = nameMenuEditText.getEditText().getText().toString();
                String priceMenu = priceMenuEditText.getEditText().getText().toString();
                resultIntent.putExtra("nameMenu", nameMenu);
                resultIntent.putExtra("priceMenu", priceMenu);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
