package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

public class Bienvenida extends Activity {
    Button logout;
    Button btnLocation;
    TextView txv;
    private GoogleApiClient client;
    GPS location;
    ParseUser currentUser = ParseUser.getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);

        // Localizando elementos de la Vista xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);
        final TextView txtlocation = (TextView) findViewById(R.id.txtlocation);
        logout = (Button) findViewById(R.id.logout);
        btnLocation = (Button) findViewById(R.id.location);
        txv = (TextView) findViewById(R.id.txv);


        // Evento de Boton Ubicación Actual
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startService(new Intent(getApplicationContext(), MyService.class));
                // Obteniendo locacion de clase GPS
                location = new GPS(Bienvenida.this);
                if (location.canGetLocation()) {
                    // Obteniendo Latitud y Longitud actual para ser guardadas en las variables
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    // Mostrando mensaje con las coordenadas actuales
                    Toast.makeText(getApplicationContext(), "Ubicación Actual \nLatitud: " + latitude + "\nLongitud: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    location.showSettingsAlert();
                }
                // Almacenando Latitud y Longitud en Variable "location" correspondiente a Parse Geopoint
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

        // Evento de Boton Fin de Sesion
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