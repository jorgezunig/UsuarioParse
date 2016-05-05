package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import bolts.Task;


public class LoginSignupActivity extends Activity {
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Localizando elementos de la Vista
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        //Evento inicio de Login
        loginbutton.setOnClickListener(new View.OnClickListener() {
            //Conversion de texto a cadena String
            public void onClick(View arg0) {
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                //Iniciando el servicio
                //startService(new Intent(getApplicationContext(), MyService.class));
                //Objeto task que Ejecuta la AsyncTask
                TaskT task = new TaskT();
                task.execute();

                //Logeo con usuario y contraseña existentes
                ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Intent intent = new Intent(LoginSignupActivity.this, Bienvenida.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Inicio de Sesión Exitosa!!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario NO registrado.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //Evento de Botón Registro
        signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                //Condicioón para Registro completo
                if (usernametxt.equals("") || passwordtxt.equals("")) {
                    Toast.makeText(getApplicationContext(), "Registro incompleto.", Toast.LENGTH_LONG).show();
                } else {
                    //Creacion de ParseUser
                    ParseUser user = new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);

                    //Estableciendo ubicacion
                    //ParseGeoPoint point = new ParseGeoPoint(31, 14);
                    //user.put("location", point);

                    user.signUpInBackground(new SignUpCallback() {

                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Registro Exitoso!, Favor de iniciar Sesión.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "El Usario ya existe", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LoginSignup Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.parse.starter/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LoginSignup Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.parse.starter/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private class TaskT extends AsyncTask<Void,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //Ejecutando tarea 3 segundos
            //Tarea();
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean bool) {

        }

        @Override
        protected void onCancelled () {
            super.onCancelled();
            Toast.makeText(getApplicationContext(), "Tarea Cancelada", Toast.LENGTH_LONG).show();
        }

        public String TAG ="Update";
        //Metodo que genera la tarea 5 segundos
        private void Tarea() {
          ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
         scheduler.scheduleAtFixedRate(new Runnable() {
          public void run() {
            Log.d(TAG, "Actualizando...");
          }
         }, 1, 5, TimeUnit.SECONDS);
        }
    }
}
