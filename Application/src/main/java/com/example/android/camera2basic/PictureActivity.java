package com.example.android.camera2basic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Esta será la actividad que nos muestre la imagen capturada
 */
public class PictureActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        File imgFile = (File) getIntent().getExtras().get("imagen");

        ImageView img = (ImageView) findViewById(R.id.imageView2);

        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        img.setImageBitmap(myBitmap);

    }

    /**
     *Controla el click para volver a la actividad inicial
     */
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.ok:
                Intent intent = new Intent(getApplication(), ScanActivity.class);
                startActivity(intent);
                break;
        }
    }
}
