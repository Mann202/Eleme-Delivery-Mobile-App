package com.example.fududelivery.Customer.UpdateProfile;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.fududelivery.Customer.Address.AddAddressActivity;
import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private String imageUrl;
    private String userUid;

    private FirebaseFirestore firestoreInstance;
    private UserSessionManager userSessionManager;

    private ImageView backwardBtn;
    private EditText editName, editEmail, editPhoneNumber;
    String nameUser, emailUser, phoneUser;
    private Button btn_update;
    private ImageView btn_changePic;
    private ImageView profileImage;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit_profile);
        // Initialize views
        profileImage = findViewById(R.id.ProfilePicture);
        editName = findViewById(R.id.nameField);
        editPhoneNumber = findViewById(R.id.phoneField);
        editEmail = findViewById(R.id.emailField);

        //button
        btn_update = findViewById(R.id.update);
        btn_changePic = findViewById(R.id.change_pic_btn);
        backwardBtn = findViewById(R.id.backIcon);

        showData();

        // Initialize Firebase Firestore, Storage and session manager
        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(getApplicationContext());
        userUid = userSessionManager.getUserInformation();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        //Change language
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.sn_language_customer);
        spinner.setAdapter(adapter);

        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        profileImage.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
            } else {
                selectImage();
            }
        });

        btn_changePic.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
            } else {
                selectImage();
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input from the fields
                String newName = editName.getText().toString().trim();
                String newEmail = editEmail.getText().toString().trim();
                String newPhone = editPhoneNumber.getText().toString().trim();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // Validation checks
                if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                    Toast.makeText(EditProfileActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update Firestore
                firestoreInstance.collection("Users")
                        .whereEqualTo("userUid", userSessionManager.getUserInformation())
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                queryDocumentSnapshots.getDocuments().get(0).getReference()
                                        .update("userName", newName, "userGmail", newEmail, "userPhone", newPhone, "userPhoto", imageUrl)
                                        .addOnSuccessListener(aVoid -> {
                                            // Update email in Firebase Authentication
                                            if (user != null) {
                                                user.updateEmail(newEmail)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    // Update session manager
                                                                    userSessionManager.loginUserName(newName);
                                                                    userSessionManager.loginUserGmail(newEmail);
                                                                    userSessionManager.loginUserPhone(newPhone);

                                                                    //pass data to Customer profile using intent
                                                                    Intent intentProfile = new Intent(EditProfileActivity.this, CustomerProfile.class);

                                                                    intentProfile.putExtra("userName", newName);
                                                                    intentProfile.putExtra("userGmail", newEmail);
                                                                    intentProfile.putExtra("userPhone", newPhone);
                                                                    intentProfile.putExtra("userPhoto", imageUrl);

                                                                    startActivity(intentProfile);


                                                                    Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                                                    setResult(RESULT_OK);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(EditProfileActivity.this, "Failed to update email in authentication", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(EditProfileActivity.this, "Failed to update profile in Firestore", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(EditProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(EditProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        showData();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                profileImage.setImageURI(selectedImageUri);
                uploadImageToFirebase();
            }
        }
    }

    private void uploadImageToFirebase() {
        if (selectedImageUri != null) {
            StorageReference imageReference = storageReference.child("profileImages/" + userUid + ".jpg");
            imageReference.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> imageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        imageUrl = uri.toString();
                        Toast.makeText(EditProfileActivity.this, "Profile photo updated", Toast.LENGTH_SHORT).show();
                    }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(EditProfileActivity.this, "Failed to upload profile photo", Toast.LENGTH_SHORT).show();
                    });
        }
    }
    public void showData(){
        Intent intent = getIntent();

        nameUser = intent.getStringExtra("userName");
        emailUser = intent.getStringExtra("userGmail");
        phoneUser = intent.getStringExtra("userPhone");
        String photoUrl = intent.getStringExtra("userPhoto");

        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editPhoneNumber.setText(phoneUser);
        if (!TextUtils.isEmpty(photoUrl)) {
            Glide.with(this).load(photoUrl).into(profileImage);
        }
    }
}
