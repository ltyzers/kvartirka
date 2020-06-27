package com.example.kvartirka;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class FlatsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Adapter mExampleAdapter;
    private ArrayList<Model> mExampleList;
    private RequestQueue mRequestQueue;
    private boolean gps_enable = false;
    private boolean network_enable = false;

    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationListener();

    String lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flats);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermission();
        getMyLocation();

    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                locationManager.removeUpdates(locationListener);
                lat = "" + location.getLatitude();
                lon = "" + location.getLongitude();

                parseJSON(lat,lon);

            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    public void getMyLocation() {

        try {
            gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {}

        try {
            network_enable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception ex) {}

        if (!gps_enable && !network_enable) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                    FlatsActivity.this);

            builder.setTitle("Не удалось определить местоположение");
            builder.create().show();
            lon = "37.620393";
            lat = "55.753960";
            parseJSON(lat,lon);
        }

        if (gps_enable) {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, locationListener);
        }

        if (network_enable) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, locationListener);

        }
    }
    private boolean checkLocationPermission(){

        int location = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermission = new ArrayList<>();

        if (location != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (location2 != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!listPermission.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermission.toArray(
                    new String[listPermission.size()]),
                    1);}

        return true;
    }

    private void parseJSON(String lat, String lon) {
        String url = "https://api.kvartirka.com/client/1.4/flats/?offset=0&point_lng=" + lon +
                "&point_lat=" + lat;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("flats");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject flat = jsonArray.getJSONObject(i);
                                String flatAddress = flat.getString("address");
                                String title = flat.getString("title");
                                String imageUrl = null;
                                int dayPrice = 0;

                                if (flat.get("photo_default") instanceof JSONObject) {
                                    JSONObject object = (JSONObject) flat.get("photo_default");
                                    imageUrl = object.getString("url");
                                }

                                if (flat.get("prices") instanceof JSONObject) {
                                    JSONObject object = (JSONObject) flat.get("prices");
                                    dayPrice = object.getInt("day");
                                }

                                List<String> urls = new ArrayList<String>();
                                JSONArray jsonArrayPh = flat.getJSONArray("photos");
                                for (int p = 0; p < jsonArrayPh.length(); p++) {
                                    JSONObject photo = jsonArrayPh.getJSONObject(p);
                                    String url = photo.getString("url");
                                    urls.add(url);
                                }

                                String[] stringArray = urls.toArray(new String[urls.size()]);

                                mExampleList.add(new Model(imageUrl, flatAddress, dayPrice,
                                        stringArray, title));
                            }

                            mExampleAdapter = new Adapter(FlatsActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }
}
