package com.example.fududelivery.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.fududelivery.GetStarted.SplashScreen;
import com.example.fududelivery.Home.Customer;
import com.example.fududelivery.R;
import com.example.fududelivery.Restaurant.MainRestaurant.MainRestaurant;
import com.example.fududelivery.Shipper.ShipperMain;

public class LoginCaseManager {
    private Context context;

    public LoginCaseManager(Context context) {
        this.context = context;
    }

    public void loginWithRoleID(String roleId) {
        Intent intent = null;

        switch (roleId) {
            case "1":
                intent = createIntent(Customer.class);
                break;
            case "2":
                intent = createIntent(MainRestaurant.class);
                break;
            case "3":
                intent = createIntent(ShipperMain.class);
                break;
            default:
                break;
        }

        if (intent != null) {
            startActivityWithAnimation(intent);
        }
    }

    private Intent createIntent(Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private void startActivityWithAnimation(Intent intent) {
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

}
