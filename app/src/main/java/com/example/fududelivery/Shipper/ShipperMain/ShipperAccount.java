package com.example.fududelivery.Shipper.ShipperMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.fududelivery.Login.Login;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;


public class ShipperAccount extends Fragment {

    public ShipperAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shipper_account, container, false);
        UserSessionManager userSessionManager = new UserSessionManager(requireContext());

        TextView nameText = rootView.findViewById(R.id.txt_shipper_name);
        nameText.setText(userSessionManager.getUserName());

        AppCompatTextView vehicleInformationField = rootView.findViewById(R.id.vehicleInformationField);
        vehicleInformationField.setText(userSessionManager.getUserVehicle());

        AppCompatTextView phoneNumberField = rootView.findViewById(R.id.phoneNumberField);
        phoneNumberField.setText(userSessionManager.getUserPhone());
        phoneNumberField.setEnabled(false);

        // Spinner language
        String[] languages = {"English", "Vietnamese"};

        AppCompatButton logoutBtn = rootView.findViewById(R.id.logout_button);
        Spinner spinnerLanguage = rootView.findViewById(R.id.sn_language_shipper);
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, languages);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(languageAdapter);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                // Xử lý khi một mục được chọn trong Spinner language
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Spinner theme
        String[] themes = {"Light", "Dark"};
        Spinner spinnerTheme = rootView.findViewById(R.id.sn_theme_shipper);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, themes);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheme.setAdapter(themeAdapter);

        spinnerTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                // Xử lý khi một mục được chọn trong Spinner theme
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the logout confirmation popup
                userSessionManager.logoutUser();
                Intent loginIntent = new Intent(requireContext(), Login.class);
                startActivity(loginIntent);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                requireActivity().finishAffinity();
            }
        });

        return rootView;
    }
}
