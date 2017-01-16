package com.example.teo.conexionasincronahttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void descargarFichero(View view) {
        TextView tvSalida = (TextView) findViewById(R.id.tvDownloadedFile);
        tvSalida.append("\n Hola!");
    }
}
