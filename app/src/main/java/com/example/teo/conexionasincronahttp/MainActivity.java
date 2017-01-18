package com.example.teo.conexionasincronahttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String tag = "DescargaAsyn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDescarga = (Button) findViewById(R.id.btnDescargar);
        btnDescarga.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView tvSalida = (TextView) findViewById(R.id.tvDownloadedFile);
        tvSalida.setMovementMethod(new ScrollingMovementMethod());
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        EditText etURL = (EditText) findViewById(R.id.etURL);
        String urlString = etURL.getText().toString();

        if(networkInfo != null  && networkInfo.isConnected()){
            Log.i(tag, "Descargando archivo");
            DescargarArchivo descargarArchivo = new DescargarArchivo(tvSalida);
            descargarArchivo.execute(urlString);

        }
    }
}
