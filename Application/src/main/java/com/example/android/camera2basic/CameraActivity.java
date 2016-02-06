/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.camera2basic;

import android.app.AlertDialog;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener{

    private GestureLibrary gLibrary;
    int info; //Para controlar las veces que se solicitado ayuda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        info = 0;
        GestureOverlayView gesture;
        //Relacionamos con el XML
        gesture = (GestureOverlayView)findViewById(R.id.gestureOverlayView1);
        //Le añadimos el listener
        gesture.addOnGesturePerformedListener(this);
        //Creamos la carpeta res/raw y añadimos el archivo gestures
        //añadimos el raw al gLibrary
        gLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        //lo cargamos...
        gLibrary.load();
    }

    /** Gestiona los gestos realizados sobre la pantalla
     *
     * @param overlay Vista sobre la que se ha hecho el gesto
     * @param gesture Gesto hecho
     */
    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        //añadimos los diferentes resultados obtenidos
        //ya comparados con nuestro archivo y ordenados de mas fiable a menos
        ArrayList<Prediction> predictions = gLibrary.recognize(gesture);
        //Si a encontraod algun resultado
        if (predictions.size() > 0) {
            //En este caso solos nos interesa el gesto que más se parezca
            Prediction prediction = predictions.get(0);
            //Miramos que tengo un parecido mínimo
            if (prediction.score > 4) {
                //Lanzamos la cámara

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, Camera2BasicFragment.newInstance())
                        .commit();

                FloatingActionButton info = (FloatingActionButton) findViewById(R.id.info);
                info.hide();

            }else{
                //Si no supera el 4.0 de fiabilidad
                Toast.makeText(this, getString(R.string.no_reconocido), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** Gestiona los eventos de click que se realicen
     * @param view Vista (referencia) del botón que se ha pulsado
     */

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.info: {
                    if(info == 0) {
                        new AlertDialog.Builder(this)
                                .setMessage(R.string.info)
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                        info++;
                    }else {

                        ImageView imageview = (ImageView) findViewById(R.id.imageView);
                        imageview.setVisibility(View.VISIBLE);
                        FloatingActionButton info = (FloatingActionButton) findViewById(R.id.info);
                        info.hide();
                    }
            }
                break;
        }
    }
}
