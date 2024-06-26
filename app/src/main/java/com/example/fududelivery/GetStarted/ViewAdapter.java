package com.example.fududelivery.GetStarted;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fududelivery.R;

public class ViewAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.food_delivery_restaurant_review_stars, R.drawable.shipping_truck_fast, R.drawable.ic_certificate, R.drawable.streamlinehq_money_cash_bill_1____________line_48_ico_yfoicjymrxa88emm};

    private String[] quotes;

    public ViewAdapter(Context context) {
        this.context = context;
        initializeQuotes();
    }

    private void initializeQuotes() {
        String delicious = context.getString(R.string.msg_delicious_food);
        String fastFood = context.getString(R.string.msg_fast_shipping);
        String certificateFood = context.getString(R.string.msg_certificate_food);
        String paymentOnline = context.getString(R.string.msg_payment_online);
        quotes = new String[]{delicious, fastFood, certificateFood, paymentOnline};
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_getstarted, container, false);

        ImageView imageView = view.findViewById(R.id.image_view);
        imageView.setImageResource(images[position]);

        TextView textView = view.findViewById(R.id.text_view);
        textView.setText(quotes[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}