package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class LoginSignupActivity extends Activity {
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

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

                startService(new Intent(getApplicationContext(),MyService.class));

                //Logeo con usuario y contrasenha existentes
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
                    ParseGeoPoint point = new ParseGeoPoint(31,14);
                    user.put("location", point);
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
    }
}
