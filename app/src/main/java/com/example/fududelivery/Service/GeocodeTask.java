package com.example.fududelivery.Service;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeocodeTask extends AsyncTask<String, Void, double[]> {

    private GeocodeCallback callback;

    public GeocodeTask(GeocodeCallback callback) {
        this.callback = callback;
    }

    @Override
    protected double[] doInBackground(String... addresses) {
        String address = addresses[0];
        try {
            address = address.replace(" ", "+");
            String url = "https://api-v2.distancematrix.ai/maps/api/geocode/json?address=" + address + "&key=X9xVA27X2AkFEYd5F7kxVjEySnUXlqeM0rrI9jv3cDaR7EDbbHazcHxeopgBcOdG";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            if ("OK".equalsIgnoreCase(json.getString("status"))) {
                JSONArray results = json.getJSONArray("result");
                JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                return new double[]{lat, lng};
            }
        } catch (Exception e) {
            Log.v("Debug", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(double[] coordinates) {
        if (callback != null) {
            callback.onGeocodeResult(coordinates);
        }
    }
}