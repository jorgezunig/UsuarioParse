package com.parse.starter;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.security.Permissions;
import java.text.ParsePosition;
import java.util.concurrent.TimeUnit;

import bolts.Task;

public class Bienvenida extends Activity {
    Button logout;
    Button btnLocation;

    private GoogleApiClient client;
    GPS location;
    ParseUser currentUser = ParseUser.getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obteniendo la Vista XML
        setContentView(R.layout.bienvenida);

        // Localizando elementos de la Vista xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);
        final TextView txtlocation = (TextView) findViewById(R.id.txtlocation);
        logout = (Button) findViewById(R.id.logout);
        btnLocation = (Button) findViewById(R.id.location);

        btnLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                location = new GPS(Bienvenida.this);
                if (location.canGetLocation()) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Toast.makeText(getApplicationContext(), "Ubicación Actual \nLatitud: " + latitude + "\nLongitud: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    location.showSettingsAlert();
                }

                ParseGeoPoint geoPoint = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
                currentUser.put("location", geoPoint);
                currentUser.saveInBackground();
            }
        });


        // Conversion de usuario y oordenadas actuales actual a String
        String struser = currentUser.getUsername().toString();
        final String strlocation = currentUser.get("location").toString();

        // Estableciendo String de usuario y Coordenadasa TextView
        txtuser.setText(" " + struser);
        txtlocation.setText(" " + strlocation);

        // Evento de Boton Fin de Seseion
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Fin de Sesion
                ParseUser.logOut();
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


}