package com.example.fududelivery.Restaurant.Profile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fududelivery.R;

public class RestaurantProfile extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivity_restaurantprofile);

        Button logoutBtn = findViewById(R.id.logoutBtn);

        // Set an OnClickListener on the logout button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the logout confirmation popup
                showLogoutConfirmation();
            }
        });
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Logout") // Set the title of the dialog
                .setMessage("Are you sure you want to log out?") // Set the message to show in the dialog
                .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked the "Log Out" button, add your logout logic here
                        //logoutUser(); // This is a placeholder, replace with your actual logout method
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked the "Cancel" button, dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert) // Set an icon for the dialog
                .show();
    }
}
