package dragos.com.meteoapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dragos.com.meteoapp.Model.CurrentWeather;
import dragos.com.meteoapp.util.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Button btnShowLocation;
    String provider;
    LocationManager locationManager;
    TextView latitude;
    TextView longitude;
    TextView address;
    TextView postalCode;
    Geocoder geocoder;
    List<Address> addressList;
    CurrentWeather currentWeather;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("PRINTINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        geocoder = new Geocoder(this, Locale.getDefault());
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.logitude);
        address = (TextView) findViewById(R.id.address);
        postalCode = (TextView) findViewById(R.id.postalcode);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        } else {
            Log.i(TAG, "bug off");

        }

        RestClient.getApi()
                .getCurrentWeather(addressList.get(0).getLocality() + "," + addressList.get(0).getCountryCode())
                .enqueue(new Callback<CurrentWeatherResponse>() {
                    @Override
                    public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                        currentWeather = response.body().getCurrentWeather();
                        address.setText(String.valueOf(currentWeather.getDescription()));
                        postalCode.setText(String.valueOf(currentWeather.getTemp()));
                        Log.e("TAG", currentWeather.getDescription());
                    }

                    @Override
                    public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                        Log.e("TAG","FAILLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl");
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(provider, 600, 1, this);
        RestClient.getApi()
                .getCurrentWeather(addressList.get(0).getLocality() + "," + addressList.get(0).getCountryCode())
                .enqueue(new Callback<CurrentWeatherResponse>() {
                    @Override
                    public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                        currentWeather = (response.body().getCurrentWeather());
                        Log.e("TAG", String.valueOf(currentWeather.getHumidity()));
                        address.setText(String.valueOf(currentWeather.getDescription()));
                        postalCode.setText(String.valueOf(currentWeather.getTemp()));
                        Log.e("TAG", currentWeather.getDescription());
                    }

                    @Override
                    public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        latitude.setText(String.valueOf(lat));
        longitude.setText(String.valueOf(lon));


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
