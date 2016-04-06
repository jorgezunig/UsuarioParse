package com.parse.starter;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.text.ParsePosition;
import java.util.concurrent.TimeUnit;

public class Bienvenida extends Activity {
    Button logout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obteniendo la Vista XML
        setContentView(R.layout.bienvenida);

        // Localizando elementos de la Vista xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);
        final TextView txtlocation = (TextView) findViewById(R.id.txtlocation);
        logout = (Button) findViewById(R.id.logout);

        // Obetniendo usuario actual
        ParseUser currentUser = ParseUser.getCurrentUser();

        // Conversion de usuario actual a String
        String struser = currentUser.getUsername().toString();
        // Conversion de coordenadas actuales a String
        final String strlocation = currentUser.get("location").toString();

        // Estableciendo String de usuario a TextView
        txtuser.setText(" " + struser);
        // Estableciendo String de Coordenadas a TextView
        txtlocation.setText(" " + strlocation);

        // Evento de Boton Fin de Seseion
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Fin de Sesion
                ParseUser.logOut();
                finish();
            }
        });
    }
}
