package com.example.countries;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIComms {

    static OnMessageShown parent;
    static Bitmap image;

    public static void reverseGeoAPI(LatLng latLng, Bitmap bitmap) {
        image = bitmap;
        String url = "https://eu1.locationiq.com/v1/reverse.php";
        String apiKey = "9f5d9fff00849b";
        (new AsyncNetworkTask()).execute(url +
                "?key=" + apiKey +
                "&lat=" + latLng.latitude +
                "&lon=" + latLng.longitude +
                "&format=json");
    }

    public static void countryAPI(String name) {

        String url = "https://countryapi.gear.host/v1/Country/getCountries";
        (new AsyncNetworkTask()).execute(url + "?pName=" + name);
    }

    private static class AsyncNetworkTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String ...urls) {

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(2000);
                connection.setReadTimeout(2000);
                connection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();

                String smallChunk;
                while ((smallChunk = reader.readLine()) != null) {
                    builder.append(smallChunk);
                }

                reader.close();
                connection.disconnect();

                return new JSONObject(builder.toString());

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final JSONObject result) {
            try {
                if (result.has("place_id")) {
                    // Has to be the result from the first API
                    countryAPI(result.getJSONObject("address").getString("country"));
                } else if (result.has("FLAGPNG")) {
                    // Has to be the result from the second API

                    parent.showMessage(image, "You clicked on America.");
                }


//                JSONObject address = result.getJSONObject("address");
//                if (address.has("county")) {
//                    parent.showMessage("You clicked on " + address.getString("country") + ", " + address.getString("county"));
//                } else {
//                    parent.showMessage("You clicked on " + address.getString("country"));
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
