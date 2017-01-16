package com.example.teo.conexionasincronahttp;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Teo on 16/01/2017.
 */

public class DescargarArchivo extends AsyncTask<String, Void, String> {
    TextView tvSalida;

    DescargarArchivo(TextView tvSalida){
        this.tvSalida = tvSalida;
    }


    @Override
    protected String doInBackground(String... params) {
        try {
            return descargaUrl(params[0]);
        }catch (IOException e){
            return "Imposible descargar el archivo!";
        }
    }

    @Override
    protected void onPostExecute(String salida) {
        super.onPostExecute(salida);
        tvSalida.setText(salida);
    }

    private String descargaUrl(String param) throws IOException{
        InputStream is = null;
        try {
            URL urlDescarga = new URL(param);
            HttpURLConnection conexion =
                    (HttpURLConnection) urlDescarga.openConnection();
            conexion.setReadTimeout(10000);
            conexion.setConnectTimeout(10000);
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);
            conexion.connect();
            int codigoRespuesta = conexion.getResponseCode();

            is=conexion.getInputStream();
            return leer(is);

        }finally {
            if( is != null){
                is.close();
            }
        }

    }

    private String leer(InputStream is) {

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1){
                bo.write(i);
                i=is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "No se ha leído nada";
        }
    }
}
