package com.example.fududelivery.Customer;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;

import java.util.ArrayList;
import java.util.List;

public class EditAddress extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_editaddress);

        Spinner sn_city = (Spinner) findViewById(R.id.sn_city);

        List<String> City = new ArrayList<>();
        City.add("Ho Chi Minh");
        City.add("Quy Nhon");
        City.add("Nha Trang");

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, City);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        sn_city.setAdapter(adapter);


    }
}
