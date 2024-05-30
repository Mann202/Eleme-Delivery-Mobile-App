package com.example.fududelivery.Home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fududelivery.Customer.CustomerProfile;
import com.example.fududelivery.Customer.MainOrder;
import com.example.fududelivery.Customer.MyFavorite;
import com.example.fududelivery.Customer.SavedPlaces;
import com.example.fududelivery.Customer.TermAndCondition;
import com.example.fududelivery.ExploreTitle.Title;
import com.example.fududelivery.ExploreTitle.ViewAdapter_Title;
import com.example.fududelivery.Restaurant_Home.Restaurant_Home;
import com.example.fududelivery.Restaurant_Home.ViewAdapter_RestaurantHome;
import com.example.fududelivery.Home.Bakery.Bakery;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Customer extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rcvFoodList;
    ViewPager viewPager;
    ViewAdapter_Customer viewAdapter;
    ViewAdapter_RestaurantHome viewAdapter_RH;
    RecyclerView rcvExploreList;
    ArrayList<Title> mTitles;
    ArrayList<Restaurant_Home> mFoods;
    GridLayout mainGrid;
    private DrawerLayout drawer;
    FirebaseFirestore dbroot;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        AppCompatButton settingButton = findViewById(R.id.settingButton);
        TextView seemore = findViewById(R.id.seemore_restaurant);
        TextInputLayout searchInputLayout = findViewById(R.id.findrestaurant);
        TextInputEditText searchEditText = findViewById(R.id.findrestauranttextinput);
        viewPager = findViewById(R.id.banner);
        viewAdapter = new ViewAdapter_Customer(this);
        viewPager.setAdapter(viewAdapter);
        rcvFoodList = findViewById(R.id.rcv_foodlist);
        rcvExploreList = findViewById(R.id.rcv_titlelist);
        mFoods = new ArrayList<Restaurant_Home>();
        mTitles = new ArrayList<Title>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvFoodList.setLayoutManager(linearLayoutManager);
        rcvFoodList.setHasFixedSize(true);
        drawer = findViewById(R.id.drawer_layout);
        rcvExploreList.setLayoutManager(linearLayoutManager1);
        rcvExploreList.setHasFixedSize(true);
        viewAdapter_RH = new ViewAdapter_RestaurantHome(mFoods, this);
        rcvFoodList.setAdapter(viewAdapter_RH);
        mainGrid = findViewById(R.id.category_list);
        dbroot = FirebaseFirestore.getInstance();
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
        DocumentReference document_Address = dbroot.collection("Address").document("xIXBN6yU11nIoNRkJLP8");
        document_Address.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String addressName = documentSnapshot.getString("Street");
                    // Get the address document using the addressID
                    dbroot.collection("Restaurant").document("uPprI6AnbvPqpby4TdtL").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot restaurantDocument) {
                            if (restaurantDocument.exists()) {
                                mFoods.add(new Restaurant_Home(restaurantDocument.getString("ImageID"),restaurantDocument.getString("AddressID"),restaurantDocument.getString("Description"),restaurantDocument.getString("ResID"),restaurantDocument.getString("ResName"), addressName));
                                mFoods.add(new Restaurant_Home(restaurantDocument.getString("ImageID"),restaurantDocument.getString("AddressID"),restaurantDocument.getString("Description"),restaurantDocument.getString("ResID"),restaurantDocument.getString("ResName"), addressName));
                                mFoods.add(new Restaurant_Home(restaurantDocument.getString("ImageID"),restaurantDocument.getString("AddressID"),restaurantDocument.getString("Description"),restaurantDocument.getString("ResID"),restaurantDocument.getString("ResName"), addressName));
                                mFoods.add(new Restaurant_Home(restaurantDocument.getString("ImageID"),restaurantDocument.getString("AddressID"),restaurantDocument.getString("Description"),restaurantDocument.getString("ResID"),restaurantDocument.getString("ResName"), addressName));
                                mFoods.add(new Restaurant_Home(restaurantDocument.getString("ImageID"),restaurantDocument.getString("AddressID"),restaurantDocument.getString("Description"),restaurantDocument.getString("ResID"),restaurantDocument.getString("ResName"), addressName));
                                mFoods.add(new Restaurant_Home(restaurantDocument.getString("ImageID"),restaurantDocument.getString("AddressID"),restaurantDocument.getString("Description"),restaurantDocument.getString("ResID"),restaurantDocument.getString("ResName"), addressName));
                                mFoods.add(new Restaurant_Home(restaurantDocument.getString("ImageID"),restaurantDocument.getString("AddressID"),restaurantDocument.getString("Description"),restaurantDocument.getString("ResID"),restaurantDocument.getString("ResName"), addressName));
                                mFoods.add(new Restaurant_Home(restaurantDocument.getString("ImageID"),restaurantDocument.getString("AddressID"),restaurantDocument.getString("Description"),restaurantDocument.getString("ResID"),restaurantDocument.getString("ResName"), addressName));
                            }
                            viewAdapter_RH.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        setSingleEvent(mainGrid);
        mTitles.add(new Title("Food 1"));
        mTitles.add(new Title("Food 1"));
        mTitles.add(new Title("Food 1"));
        mTitles.add(new Title("Food 1"));
        mTitles.add(new Title("Food 1"));
        mTitles.add(new Title("Food 1"));

        ViewAdapter_Title viewadapter_title;
        viewadapter_title = new ViewAdapter_Title(mTitles, this);
        rcvExploreList.setAdapter(viewadapter_title);


        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer.this, Search_Main.class);
                startActivity(intent);
            }
        });

        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer.this, Seemore_Main.class);
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
                    intent = new Intent(Customer.this, MyFavorite.class);
                } else if (itemId == R.id.savePlaces) {
                    intent = new Intent(Customer.this, SavedPlaces.class);
                } else if (itemId == R.id.paymentManagement) {
                    intent = new Intent(Customer.this, MainOrder.class);
                } else if (itemId == R.id.myAccount) {
                    intent = new Intent(Customer.this, CustomerProfile.class);
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
    private void setSingleEvent (GridLayout mainGrid) {
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
                    } else if(finalI == 5) {
                        Intent intent = new Intent(Customer.this, Bakery.class);
                        startActivity(intent);
                    } else if(finalI == 6) {
                        Intent intent = new Intent(Customer.this, Fruit.class);
                        startActivity(intent);
                    } else if(finalI == 7) {
                        Intent intent = new Intent(Customer.this, Dessert.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Customer.this, "No items", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    public void onClick(View v) {

    }


}

