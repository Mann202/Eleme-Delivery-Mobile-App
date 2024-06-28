package com.example.fududelivery.Home;


import static java.lang.System.exit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fududelivery.Customer.CustomerProfile;
import com.example.fududelivery.Customer.MyOrder.MainOrder;
import com.example.fududelivery.Customer.TermAndCondition;
import com.example.fududelivery.ExploreTitle.Title;
import com.example.fududelivery.Home.Korean.Korean;
import com.example.fududelivery.Home.Search.ItemSearch;
import com.example.fududelivery.Home.Search.ViewAdapter_ItemSearch;
import com.example.fududelivery.Restaurant_Home.Restaurant_Home;
import com.example.fududelivery.Restaurant_Home.ViewAdapter_Food;
import com.example.fududelivery.Restaurant_Home.ViewAdapter_RestaurantHome;
import com.example.fududelivery.Home.Dessert.Dessert;
import com.example.fududelivery.Home.Drink.Drink;
import com.example.fududelivery.Home.FastFood.FastFood;
import com.example.fududelivery.Home.Fruit.Fruit;
import com.example.fududelivery.Home.Noodle.Noodle;
import com.example.fududelivery.Home.SeaFood.SeaFood;
import com.example.fududelivery.Home.Search.Search_Main;
import com.example.fududelivery.Home.Seemore.Seemore_Main;
import com.example.fududelivery.Home.Vegetable.Vegetable;
import com.example.fududelivery.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Customer extends AppCompatActivity {
    private static final String SHARED_PREF_KEY = "isNearbyChipChecked";
    private FusedLocationProviderClient fusedLocationClient;
    RecyclerView rcvFoodList;
    RecyclerView rcvExplore;
    ViewPager viewPager;
    ViewAdapter_Customer viewAdapter;
    ViewAdapter_RestaurantHome viewAdapter_RH;
    ViewAdapter_ItemSearch viewAdapter_Search1;
    ArrayList<Title> mTitles;
    ArrayList<ItemSearch> mRestaurant;
    ArrayList<Restaurant_Home> mFoods;
    GridLayout mainGrid;
    ChipGroup chipGroup;
    private DrawerLayout drawer;
    private String userAddress;
    FirebaseFirestore dbroot;
    private FirebaseAuth mAuth;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean isNearbyChipChecked = false;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        AppCompatButton settingButton = findViewById(R.id.settingButton);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        TextInputEditText searchEditText = findViewById(R.id.findrestauranttextinput);
        mAuth = FirebaseAuth.getInstance();
        chipGroup = findViewById(R.id.chip_group_main);
        rcvExplore = findViewById(R.id.rcv_item_restaurant_list);
        viewPager = findViewById(R.id.banner);
        viewAdapter = new ViewAdapter_Customer(this);
        viewPager.setAdapter(viewAdapter);
        mRestaurant = new ArrayList<>();
        mFoods = new ArrayList<Restaurant_Home>();
        mTitles = new ArrayList<Title>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerExplore = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvExplore.setLayoutManager(linearLayoutManagerExplore);
        rcvExplore.setHasFixedSize(true);
        viewAdapter_Search1 = new ViewAdapter_ItemSearch(mRestaurant, this);
        drawer = findViewById(R.id.drawer_layout);
        viewAdapter_RH = new ViewAdapter_RestaurantHome(mFoods, this);
        rcvExplore.setAdapter(viewAdapter_Search1);
        mainGrid = findViewById(R.id.category_list);
        dbroot = FirebaseFirestore.getInstance();
        getCurrentUserAddress();
        EventChangeListener();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Near by");
        arrayList.add("Fast food");
        arrayList.add("Seafood");
        arrayList.add("Healthy");
        arrayList.add("Fruits");
        arrayList.add("Desserts");
        arrayList.add("Korean");
        arrayList.add("Noodle");
        arrayList.add("Drink");
        Random random = new Random();
        for (String option : arrayList) {
            Chip chip = (Chip) LayoutInflater.from(Customer.this).inflate(R.layout.chip_layout, null);
            chip.setText(option);
            chip.setId(random.nextInt());
            chipGroup.addView(chip);
        }
            chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                    if (checkedIds.isEmpty()) {

                    } else {
                        boolean isNearByChecked = false;
                        for (int id : checkedIds) {
                            Chip chip = findViewById(id);
                            if (chip.getText().toString().equalsIgnoreCase("Near by")) {
                                isNearByChecked = true;
                                break;
                            }
                        }
                        if (isNearByChecked) {
                            // Set isNearbyChipChecked to true here
                            isNearbyChipChecked = true;
                            checkLocationPermission();
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i : checkedIds) {
                                Chip chip = findViewById(i);
                                stringBuilder.append(chip.getText());
                            }
                            filter(stringBuilder.toString());
                        }
                    }
                }
            });

//        DocumentReference document_Restaurant = dbroot.collection("Restaurant").document("uPprI6AnbvPqpby4TdtL");
//        document_Restaurant.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    mFoods.add(new Restaurant_Home(documentSnapshot.getString("ImageID"),documentSnapshot.getString("AddressID"),documentSnapshot.getString("Description"),documentSnapshot.getString("ResID"),documentSnapshot.getString("ResName"), addressName));
//                    mFoods.add(new Restaurant_Home(documentSnapshot.getString("ImageID"),documentSnapshot.getString("AddressID"),documentSnapshot.getString("Description"),documentSnapshot.getString("ResID"),documentSnapshot.getString("ResName"), addressName));
//                    mFoods.add(new Restaurant_Home(documentSnapshot.getString("ImageID"),documentSnapshot.getString("AddressID"),documentSnapshot.getString("Description"),documentSnapshot.getString("ResID"),documentSnapshot.getString("ResName"), addressName));
//                    mFoods.add(new Restaurant_Home(documentSnapshot.getString("ImageID"),documentSnapshot.getString("AddressID"),documentSnapshot.getString("Description"),documentSnapshot.getString("ResID"),documentSnapshot.getString("ResName"), addressName));
//                    mFoods.add(new Restaurant_Home(documentSnapshot.getString("ImageID"),documentSnapshot.getString("AddressID"),documentSnapshot.getString("Description"),documentSnapshot.getString("ResID"),documentSnapshot.getString("ResName"), addressName));
//                    mFoods.add(new Restaurant_Home(documentSnapshot.getString("ImageID"),documentSnapshot.getString("AddressID"),documentSnapshot.getString("Description"),documentSnapshot.getString("ResID"),documentSnapshot.getString("ResName"), addressName));
//                    mFoods.add(new Restaurant_Home(documentSnapshot.getString("ImageID"),documentSnapshot.getString("AddressID"),documentSnapshot.getString("Description"),documentSnapshot.getString("ResID"),documentSnapshot.getString("ResName"), addressName));
//                    mFoods.add(new Restaurant_Home(documentSnapshot.getString("ImageID"),documentSnapshot.getString("AddressID"),documentSnapshot.getString("Description"),documentSnapshot.getString("ResID"),documentSnapshot.getString("ResName"), addressName));
//                }
//                viewAdapter_RH.notifyDataSetChanged();
//            }
//        });
        //DocumentReference document_Address = dbroot.collection("Address").document("xIXBN6yU11nIoNRkJLP8");
        //}
        //}
        //});
        setSingleEvent(mainGrid);


        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer.this, Search_Main.class);
                startActivity(intent);
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int transitionIn = R.anim.slide_in_right;
                int transitionOut = R.anim.slide_out_left;

                int itemId = item.getItemId();

                if (itemId == R.id.myOrder) {
                    intent = new Intent(Customer.this, MainOrder.class);
                } else if (itemId == R.id.termAndCondition) {
                    intent = new Intent(Customer.this, TermAndCondition.class);
                } else if (itemId == R.id.myFavorites) {
//                    intent = new Intent(Customer.this, MyFavorite.class);
                } else if (itemId == R.id.savePlaces) {
//                    intent = new Intent(Customer.this, SavedPlaces.class);
                } else if (itemId == R.id.paymentManagement) {
                    intent = new Intent(Customer.this, MainOrder.class);
                } else if (itemId == R.id.myAccount) {
//                    intent = new Intent(Customer.this, CustomerProfile.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    overridePendingTransition(transitionIn, transitionOut);
                }

                drawer.closeDrawer(GravityCompat.END);
                return true;
            }

        });
    }

    private void filter(String text) {
        if (text.equalsIgnoreCase("Near by")) {
            // Handle "Near By" chip selection here
        } else {
            ArrayList<ItemSearch> filteredList = new ArrayList<>();
            for (ItemSearch item : mRestaurant) {
                if (item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            viewAdapter_Search1.filterList(filteredList);
        }
    }

    private void EventChangeListener() {
        dbroot.collection("Restaurant").orderBy("ResID", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        // Tạo một danh sách tạm để lưu trữ các dữ liệu mới
                        ArrayList<ItemSearch> tempItems = new ArrayList<>();

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                tempItems.add(dc.getDocument().toObject(ItemSearch.class));
                            }
                        }

                        // Thêm tất cả các dữ liệu mới vào mRestaurant
                        mRestaurant.addAll(tempItems);

                        // Thông báo cho adapter cập nhật dữ liệu
                        viewAdapter_Search1.notifyDataSetChanged();
                    }
                });
    }

    private void setSingleEvent(GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == 0) {
                        Intent intent = new Intent(Customer.this, FastFood.class);
                        startActivity(intent);
                    } else if (finalI == 1) {
                        Intent intent = new Intent(Customer.this, Drink.class);
                        startActivity(intent);
                    } else if (finalI == 2) {
                        Intent intent = new Intent(Customer.this, Vegetable.class);
                        startActivity(intent);
                    } else if (finalI == 3) {
                        Intent intent = new Intent(Customer.this, Noodle.class);
                        startActivity(intent);
                    } else if (finalI == 4) {
                        Intent intent = new Intent(Customer.this, SeaFood.class);
                        startActivity(intent);
                    } else if (finalI == 5) {
                        Intent intent = new Intent(Customer.this, Korean.class);
                        startActivity(intent);
                    } else if (finalI == 6) {
                        Intent intent = new Intent(Customer.this, Fruit.class);
                        startActivity(intent);
                    } else if (finalI == 7) {
                        Intent intent = new Intent(Customer.this, Dessert.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Customer.this, "No items", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentUserAddress();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentUserAddress();
            } else {
                Toast.makeText(this, "Permission denied. Unable to get user location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getCurrentUserAddress() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Log.d("User ID", "ID: " + userId);
            getDocumentIdFromUserId(userId);
        } else {
            Log.d("User Address", "No user is logged in.");
            Toast.makeText(this, "No user is logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    public void getUserAddressFromFirestore(String documentId) {
        DocumentReference userRef = dbroot.collection("Users").document(documentId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String userAddress = documentSnapshot.getString("address");
                    Log.d("User Address", "Address: " + userAddress);

                    // Chuyển đổi địa chỉ thành tọa độ
                    Pair<Double, Double> userLatLong = getLatLongFromAddress(userAddress);
                    if (userLatLong != null) {
                        handleUserCoordinates(userLatLong.first, userLatLong.second);
                    } else {
                        Log.d("User Address", "Unable to get coordinates from address.");
                    }
                } else {
                    Log.d("User Address", "No such document.");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("User Address", "Failed to get address", e);
            }
        });
    }

    public void getDocumentIdFromUserId(String userId) {
        CollectionReference usersRef = dbroot.collection("Users");
        usersRef.whereEqualTo("userUid", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentId = document.getId();
                                Log.d("document", "document found with userId: " + documentId);

                                // Sau khi có documentId, gọi hàm để lấy địa chỉ
                                getUserAddressFromFirestore(documentId);
                                break; // Dừng lại sau khi tìm thấy document đầu tiên
                            }
                        } else {
                            Log.d("User Address", "No document found with userId: " + userId);
                            Toast.makeText(Customer.this, "No user document found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public Pair<Double, Double> getLatLongFromAddress(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                return new Pair<>(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void handleUserCoordinates(double userLatitude, double userLongitude) {
        if (isNearbyChipChecked) {
            sortAndDisplayRestaurants(userLatitude, userLongitude);
        } else {
            Log.d("User Coordinates", "Lat: " + userLatitude + ", Long: " + userLongitude);
            // Xử lý khác nếu không chọn chip "Near by"
        }
    }

    public void sortAndDisplayRestaurants(double userLatitude, double userLongitude) {
        List<ItemSearch> sortedRestaurants = sortRestaurantsByDistance(userLatitude, userLongitude, mRestaurant);
        viewAdapter_Search1.filterList(sortedRestaurants);
    }

    public List<ItemSearch> sortRestaurantsByDistance(double currentLat, double currentLong, List<ItemSearch> restaurants) {
        for (ItemSearch restaurant : restaurants) {
            Pair<Double, Double> latLong = getLatLongFromAddress(restaurant.getAddress());
            if (latLong != null) {
                restaurant.setDistance(distanceBetween(currentLat, currentLong, latLong.first, latLong.second));
                viewAdapter_Search1.notifyDataSetChanged();
            }
        }
        Collections.sort(restaurants, new Comparator<ItemSearch>() {
            @Override
            public int compare(ItemSearch o1, ItemSearch o2) {
                return Float.compare(o1.distance, o2.distance);
            }
        });
        return restaurants;
    }

    public float distanceBetween(double lat1, double lon1, double lat2, double lon2) {
        float[] result = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, result);
        return result[0] / 1000;
    }
}
