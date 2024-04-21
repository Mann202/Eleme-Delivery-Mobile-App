package com.example.fududelivery.Customer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.fududelivery.R;
public class ChangeName extends AppCompatActivity {

    private EditText nameEditText;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_changename);

        nameEditText = findViewById(R.id.ProfileName);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameEditText.getText().toString().trim();
                // Validate new name (e.g., not empty)
                if (!newName.isEmpty()) {
                    // Save the new name (add your logic here)
                    Toast.makeText(ChangeName.this, "Name saved: " + newName, Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(ChangeName.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity without saving changes
            }
        });
    }
}
