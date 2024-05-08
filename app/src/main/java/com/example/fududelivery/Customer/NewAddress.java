package com.example.fududelivery.Customer;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;

public class NewAddress extends AppCompatActivity {
    private EditText nameEditText, phoneEditText, streetEditText;
    private Spinner citySpinner, districtSpinner, wardSpinner;
    private CheckBox defaultAddressCheckBox;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_newaddress);

        nameEditText = findViewById(R.id.ProfileName);
        phoneEditText = findViewById(R.id.ProfilePhone);
        streetEditText = findViewById(R.id.StreetInput);
        citySpinner = findViewById(R.id.sn_city);
        districtSpinner = findViewById(R.id.sn_district);
        wardSpinner = findViewById(R.id.sn_ward);
        defaultAddressCheckBox = findViewById(R.id.defaultAddressCheckBox);
        saveButton = (Button) findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String street = streetEditText.getText().toString().trim();
                String city = citySpinner.getSelectedItem().toString();
                String district = districtSpinner.getSelectedItem().toString();
                String ward = wardSpinner.getSelectedItem().toString();
                boolean isDefaultAddress = defaultAddressCheckBox.isChecked();

            }
        });
    }
}
