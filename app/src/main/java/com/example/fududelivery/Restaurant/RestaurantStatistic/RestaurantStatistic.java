package com.example.fududelivery.Restaurant.RestaurantStatistic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Reference.ChangeCurrency;
import com.example.fududelivery.Restaurant.History.RestaurantHistoryAdapter;
import com.example.fududelivery.Restaurant.MainRestaurant.ItemDetailRestaurant;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RestaurantStatistic extends AppCompatActivity {
    private ArrayList<ItemDetailRestaurant> items;
    private BarChart barChart;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseFirestore firestoreInstance;
    private Map<String, Float> dailyRevenue;
    private UserSessionManager userSessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantstatistic);

        firestoreInstance = FirebaseFirestore.getInstance();
        userSessionManager = new UserSessionManager(getApplicationContext());

        swipeRefreshLayout = findViewById(R.id.refreshLayoutStatistcRestaurant);
        dailyRevenue = new HashMap<>();
        ImageView backwardHistory = findViewById(R.id.backwardButton);
        backwardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        barChart = findViewById(R.id.statisticRestaurant);
        barChart.getAxisRight().setDrawLabels(false);
        items = new ArrayList<>();
        initData();
    }

    private void initData() {
        firestoreInstance.collection("Orders").whereEqualTo("ResStatus", "Done").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                items.clear();
                ArrayList<String> restaurantIds = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    restaurantIds.add(document.getId());
                    items.add(document.toObject(ItemDetailRestaurant.class));
                }

                Log.d("Debug", "Number of orders retrieved: " + queryDocumentSnapshots.size());

                if (!restaurantIds.isEmpty()) {
                    firestoreInstance.collection("Restaurant").whereEqualTo("ResID", userSessionManager.getUserInformation()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot task) {
                            for (DocumentSnapshot doc : task) {
                                for (String orderId : restaurantIds) {
                                    QueryDocumentSnapshot document = getOrderById(queryDocumentSnapshots, orderId);
                                    if (document != null) {
                                        items.add(new ItemDetailRestaurant(doc.getString("ImageID"), document.getString("Date"), document.getString("TotalQuantity") + "items", document.getString("name"), ChangeCurrency.formatPrice(document.getDouble("ShippingFee") + document.getDouble("serviceFee") + document.getDouble("subTotal")), document.getString("address"), orderId, document.getString("CusID"), document.getString("ShipperID")));
                                        dailyRevenue = calculateDailyRevenue(items);
                                        displayChart(dailyRevenue);
                                    }
                                }
                            }
                            findViewById(R.id.loadingStatistic).setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Debug", "Error getting Restaurant documents.", e);
                        }
                    });
                } else {
                    findViewById(R.id.loadingStatistic).setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Debug", "Error getting Orders documents.", e);
            }
        });
    }

    private QueryDocumentSnapshot getOrderById(QuerySnapshot queryDocumentSnapshots, String orderId) {
        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
            if (document.getId().equals(orderId)) {
                return document;
            }
        }
        return null;
    }

    public static Map<String, Float> calculateDailyRevenue(ArrayList<ItemDetailRestaurant> orders) {
        Map<String, Float> dailyRevenueMap = new HashMap<>();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        for (ItemDetailRestaurant order : orders) {
            String dateKey = order.getDateText();
            String totalPriceStr = order.getTotalPriceText();
            if (totalPriceStr != null && !totalPriceStr.trim().isEmpty()) {
                try {
                    totalPriceStr = totalPriceStr.replace(",", "").replace("VND", "");
                    float totalPrice = Float.parseFloat(totalPriceStr.trim());
                    dailyRevenueMap.put(dateKey, dailyRevenueMap.getOrDefault(dateKey, 0f) + totalPrice);
                } catch (NumberFormatException e) {
                    Log.e("Error", "Invalid number format for totalPrice: " + totalPriceStr, e);
                }
            } else {
                Log.e("Error", "totalPriceText is null or empty for date: " + dateKey);
            }
        }

        for (Map.Entry<String, Float> entry : dailyRevenueMap.entrySet()) {
            Log.d("Debug", "Date: " + entry.getKey() + ", Revenue: " + entry.getValue());
        }

        return dailyRevenueMap;
    }

    private void displayChart(Map<String, Float> dailyRevenue) {
        if (dailyRevenue == null || dailyRevenue.isEmpty()) {
            Log.d("Debug", "No data to display on chart");
            return;
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int index = 0;

        // Sắp xếp dailyRevenue theo ngày tăng dần
        List<Map.Entry<String, Float>> sortedEntries = new ArrayList<>(dailyRevenue.entrySet());
        Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Float>>() {
            @Override
            public int compare(Map.Entry<String, Float> entry1, Map.Entry<String, Float> entry2) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                try {
                    Date date1 = dateFormatter.parse(entry1.getKey());
                    Date date2 = dateFormatter.parse(entry2.getKey());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    Log.e("Error", "Error parsing date: " + e.getMessage());
                    return 0;
                }
            }
        });

        for (Map.Entry<String, Float> entry : sortedEntries) {
            labels.add(entry.getKey());
            barEntries.add(new BarEntry(index++, entry.getValue()));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Revenue");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
    }
}