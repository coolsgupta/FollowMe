package com.millennium.followme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button mGuideButton;
    private Button mFollowButton;
    private TextView mStatusTextView;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGuideButton = (Button) findViewById(R.id.guideButton);
        mFollowButton = (Button) findViewById(R.id.followButton);
        mStatusTextView = (TextView) findViewById(R.id.statusTextView);

    }

    public void guide(View view) {
        mFollowButton.setVisibility(View.INVISIBLE);
        mGuideButton.setVisibility(View.INVISIBLE);
        mStatusTextView.setVisibility(View.VISIBLE);
        final double[] latitude = new double[1];
        final double[] longitude = new double[1];
        URL = "http://tracknfollowme.achintsatsangi.com/users/receiver1.php";
        OkHttpClient client = new OkHttpClient();

        /*String post (String URL,String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, );
            Request request = new Request.Builder()
                    .url(URL)
                    .post(body)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location loc) {
                Toast.makeText(
                        getBaseContext(),
                        "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                                + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                longitude[0] = loc.getLongitude();
                latitude[0] = loc.getLatitude();
                Log.d("marvin",latitude[0]+","+longitude[0]);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mStatusTextView.setText("permission problem");
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0, 0, locationListener);



    }

    public void follow (View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
