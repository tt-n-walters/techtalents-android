package com.example.countries;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, OnMessageShown {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        APIComms.parent = this;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {




//                APIComms.reverseGeoAPI(latLng, getResources().getDrawable(R.drawable.flag));
            }
        });
    }

    public void showMessage(Bitmap bitmap, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);

        View customView = getLayoutInflater().inflate(R.layout.custom_flag_alert, null);
        ImageView flag = customView.findViewById(R.id.flag);
        TextView text = customView.findViewById(R.id.text);
        flag.setImageBitmap(bitmap);
        text.setText(message);

        builder.create().show();
    }
}
