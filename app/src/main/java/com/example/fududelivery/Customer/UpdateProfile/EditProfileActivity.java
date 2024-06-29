//package com.example.fududelivery.Customer.UpdateProfile;
//
//import static androidx.test.InstrumentationRegistry.getContext;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.fududelivery.Customer.Address.AddAddressActivity;
//import com.example.fududelivery.Login.UserSessionManager;
//import com.example.fududelivery.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//
//public class EditProfileActivity extends AppCompatActivity {
//    private ActivityResultLauncher<Intent> imagePickerLauncher;
//    private String imageUrl;
//    private String userUid;
//
//    private FirebaseFirestore firestoreInstance;
//    private UserSessionManager userSessionManager;
//
//    private ImageView iv_profileImage;
//    private ImageView backwardBtn;
//    private EditText userName, emaill, phoneNumber;
//    private Button btn_update;
//    private Button btn_changePic, btn_removePic;
//    private Dialog loadingDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_customer_edit_profile);
//
//        // Initialize Firebase Firestore and session manager
//        firestoreInstance = FirebaseFirestore.getInstance();
//        userSessionManager = new UserSessionManager(getApplicationContext());
//        userUid = userSessionManager.getUserInformation();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//
//        //////////loading dialog
//        loadingDialog = new Dialog(EditProfileActivity.this);
//        loadingDialog.setContentView(R.layout.item_loading_progress_dialog);
//        loadingDialog.setCancelable(false);
//        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
//        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //////////loading dialog
//
//        // Initialize views
//        iv_profileImage = findViewById(R.id.ProfilePicture);
//        userName = findViewById(R.id.nameField);
//        emaill = findViewById(R.id.emailField);
//        phoneNumber = findViewById(R.id.phoneField);
//        //button
//        btn_update = findViewById(R.id.update);
//        btn_changePic = findViewById(R.id.change_pic_btn);
//        btn_removePic = findViewById(R.id.remove_pic_btn);
//        backwardBtn = findViewById(R.id.backward);
//
//        backwardBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(RESULT_CANCELED);
//                finish();
//            }
//        });
//
//        userName.setText(userSessionManager.getUserName());
//        phoneNumber.setText(userSessionManager.getUserPhone());
//        emaill.setText(userSessionManager.getUserGmail());
//
//        getInfo();
//
//
////        iv_profileImage.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                openImagePicker();
////            }
////        });
////        btn_changePic.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                openImagePicker();
////            }
////        });
//
//
//        btn_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateInfo();
//            }
//        });
//
//        userName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String newName = userName.getText().toString().trim();
//                firestoreInstance.collection("Users")
//                        .whereEqualTo("userUid", userSessionManager.getUserInformation())
//                        .get()
//                        .addOnSuccessListener(queryDocumentSnapshots -> {
//                            if (!queryDocumentSnapshots.isEmpty()) {
//                                queryDocumentSnapshots.getDocuments().get(0).getReference()
//                                        .update("name", newName)
//                                        .addOnSuccessListener(aVoid -> {
//                                            Toast.makeText(EditProfileActivity.this, "Name saved: " + newName, Toast.LENGTH_SHORT).show();
//                                            userSessionManager.loginUserName(newName);
//                                            setResult(RESULT_OK);
//                                            finish();
//                                        })
//                                        .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Failed to update name", Toast.LENGTH_SHORT).show());
//                            } else {
//                                Toast.makeText(EditProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
//            }
//        });
//
//        phoneNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String newPhone = phoneNumber.getText().toString().trim();
//                firestoreInstance.collection("Users")
//                        .whereEqualTo("userUid", userSessionManager.getUserInformation())
//                        .get()
//                        .addOnSuccessListener(queryDocumentSnapshots -> {
//                            if (!queryDocumentSnapshots.isEmpty()) {
//                                queryDocumentSnapshots.getDocuments().get(0).getReference()
//                                        .update("phone", newPhone)
//                                        .addOnSuccessListener(aVoid -> {
//                                            Toast.makeText(EditProfileActivity.this, "Phone number saved: " + newPhone, Toast.LENGTH_SHORT).show();
//                                            userSessionManager.loginUserName(newPhone);
//                                            setResult(RESULT_OK);
//                                            finish();
//                                        })
//                                        .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Failed to update phone", Toast.LENGTH_SHORT).show());
//                            } else {
//                                Toast.makeText(EditProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
//            }
//        });
//
//        emaill.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String email = emaill.getText().toString().trim();
//                if (!email.isEmpty()) {
//                    user.updateEmail(email)
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.v("Debug", e.toString());
//                                }
//                            })
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Log.d("Debug", "User email address updated.");
//                                        firestoreInstance.collection("Users")
//                                                .whereEqualTo("userUid", userSessionManager.getUserInformation())
//                                                .get()
//                                                .addOnSuccessListener(queryDocumentSnapshots -> {
//                                                    if (!queryDocumentSnapshots.isEmpty()) {
//                                                        queryDocumentSnapshots.getDocuments().get(0).getReference()
//                                                                .update("email", email)
//                                                                .addOnSuccessListener(aVoid -> {
//                                                                    Toast.makeText(EditProfileActivity.this, "User email address updated.", Toast.LENGTH_SHORT).show();
//                                                                    userSessionManager.loginUserGmail(email);
//                                                                    setResult(RESULT_OK);
//                                                                    finish();
//                                                                })
//                                                                .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Failed to update email", Toast.LENGTH_SHORT).show());
//                                                    } else {
//                                                        Toast.makeText(EditProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                })
//                                                .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
//                                    } else {
//                                        Toast.makeText(EditProfileActivity.this, task.toString(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                } else {
//                    Toast.makeText(EditProfileActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    //update user info
//    private void updateInfo() {
//        String emailTxt = emaill.getText().toString().trim();
//        String phoneNumberTxt = phoneNumber.getText().toString().trim();
//        String userNameTxt = userName.getText().toString().trim();
//
//        if (emailTxt.equals("")) {
//            Toast.makeText(EditProfileActivity.this, "Email must not be empty!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
//            Toast.makeText(EditProfileActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (phoneNumberTxt.equals("")) {
//            Toast.makeText(EditProfileActivity.this, "Phone number must not be empty!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (userNameTxt.equals("")) {
//            Toast.makeText(EditProfileActivity.this, "User name must not be empty!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        HashMap<String, Object> map = new HashMap<>();
////        map.put("avatarURL", imageUrl);
//        map.put("email", emailTxt);
//        map.put("phoneNumber", phoneNumberTxt);
//        map.put("userName", userNameTxt);
//
//        firestoreInstance.collection("Users").document(userUid).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(EditProfileActivity.this, "Update successfully!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        finish();
//    }
//
//    // get info user
//    private void getInfo() {
//        firestoreInstance.collection("Users")
//                .document(userUid)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
////                                    String avatarURL = document.getString("avatarURL");
//                                String userName = document.getString("userName");
//                                String email = document.getString("email");
//                                String phoneNumber = document.getString("phoneNumber");
//
////                                    if (avatarURL != null) {
////                                        Glide.with(getApplicationContext()).load(avatarURL).placeholder(R.drawable.default_avatar).into(iv_profileImage);
////                                        imageUrl = avatarURL;
////                                    }
//
//                                if (userName != null) {
//                                    EditProfileActivity.this.userName.setText(userName);
//                                }
//
//                                if (emaill != null) {
//                                    emaill.setText(email);
//                                }
//
//                                if (phoneNumber != null) {
//                                    EditProfileActivity.this.phoneNumber.setText(phoneNumber);
//                                }
//                            } else {
//                                Toast.makeText(EditProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(EditProfileActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//}
