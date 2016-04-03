/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParsePush;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //ParseObject datos = new ParseObject("Usuario");
    //datos.put("nombre", "Fany");
    //datos.put("edad", 22);
    //datos.saveInBackground();

    //ParseGeoPoint punto = new ParseGeoPoint(10,10);
    //datos.put ( "location" , punto);

    //Inicio de Sesion usuario actual
    if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
      Intent intent = new Intent(MainActivity.this,LoginSignupActivity.class);
      startActivity(intent);
      finish();
    } else {
      //Bienvenida de Registro
      ParseUser currentUser = ParseUser.getCurrentUser();
      if (currentUser != null) {
        Intent intent = new Intent(MainActivity.this, Bienvenida.class);
        startActivity(intent);
        finish();
      } else {
        Intent intent = new Intent(MainActivity.this,LoginSignupActivity.class);
        startActivity(intent);
        finish();
      }
    }
  }
}
