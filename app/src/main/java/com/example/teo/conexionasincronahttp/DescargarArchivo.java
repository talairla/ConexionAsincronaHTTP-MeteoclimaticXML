package com.example.teo.conexionasincronahttp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
    protected void onPostExecute(String xml) {
        super.onPostExecute(xml);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes());
        Document doc = null;
        try {
            doc = db.parse(bis);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        Element rootElement = doc.getDocumentElement();
        String id = doc.getElementsByTagName("id").item(0).getTextContent();
        NodeList temperaturas = doc.getElementsByTagName("temperature").item(0).getChildNodes();
        String salida ="";
        for (int i=0;i<temperaturas.getLength();i++){
            temperaturas.item(i).normalize();
            salida = salida +temperaturas.item(i).getNodeName()+": "+temperaturas.item(i).getTextContent();
        }
        tvSalida.setText(salida);
    }

    protected String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
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
