package com.example.renovikov.zolotarclient1;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MenuProfileActivity extends AppCompatActivity implements View.OnClickListener{

    EditText til_name;
    EditText til_adress;
    EditText til_district;
    EditText til_phone;

    private LocationManager locationManager;
    //private Location location;
    double lat,lot;


//    StringBuilder sbGPS = new StringBuilder();
    int PERMISSIONS_REQUEST_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profile);

        til_name = (EditText) findViewById(R.id.TIL_name);
        til_adress = (EditText) findViewById(R.id.TIL_adress);
        til_district = (EditText) findViewById(R.id.TIL_district);
        til_phone = (EditText) findViewById(R.id.TIL_phone);

        AppCompatButton bt_confirm = (AppCompatButton) findViewById(R.id.bt_confirm);
        AppCompatButton bt_gps = (AppCompatButton) findViewById(R.id.bt_gps);

        bt_confirm.setOnClickListener(this);
        bt_gps.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.bt_confirm:
                //передаем введеные значения и закрываемся
                intent.putExtra("name",til_name.getText());
                intent.putExtra("adress",til_adress.getText());
                intent.putExtra("district",til_district.getText());
                intent.putExtra("phone",til_phone.getText());
                setResult(RESULT_OK,intent);
                finish();
            case R.id.bt_gps:
                //Проверяем разрешения
               // gps_LocationUpdatesON();
                //получаем координаты
                //геокодируем
                //преобразовать в строку и положить в поле
                Geocoder gCoder = new Geocoder(this);
                try {
                     til_adress.setText(gCoder.getFromLocation(lat, lot, 1).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra("latitude",lat);
                intent.putExtra("longitude",lot);
            default:
                setResult(RESULT_CANCELED,intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gps_LocationUpdatesON();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void gps_LocationUpdatesON() {
        //проверяем, есть ли разрешения permissons
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 10, 10, locationListener);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST_CODE);
        }
        //проверяем, включен ли GPS
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //!!**Добавить обработчик включения GPS**!!
            Toast.makeText(this,"Не включен GPS!",Toast.LENGTH_LONG).show();
        }

    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lot = location.getLongitude();
            //showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            /*if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //!!**Добавить обработчик включения GPS**!!
                Toast.makeText(this,"Не включен GPS!",Toast.LENGTH_LONG).show();
            }*/
            //checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            //checkEnabled();
            //showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
   //         if (provider.equals(LocationManager.GPS_PROVIDER)) {
              //  tvStatusGPS.setText("Status: " + String.valueOf(status));
     //       } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
//                tvStatusNet.setText("Status: " + String.valueOf(status));
            }
        };
}
