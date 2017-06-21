package com.example.applimap.model;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Nico on 17/06/2017.
 */

public class OkHttpUtils {
    public static String sendGetOkHttpRequest(String url) throws Exception {
        Log.w("tag", "" + url);
        OkHttpClient client = new OkHttpClient();
        // Création de la requete
        Request request = new Request.Builder().url(url).build();
        // Execution de la requête
        Response response = client.newCall(request).execute();
        // Analyse du code retour
        if (response.code() != 200) {
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        } else {
            // Résultat de la requete.
            return response.body().string();
        }
    }

    public static String sendPostOkHttpRequest(String url, String paramJson) throws Exception {
        Log.w("tag", "" + url);
        System.out.println(url);
        System.out.println(paramJson);
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // Corps de la requête
        RequestBody body = RequestBody.create(JSON, paramJson);
        // Création de la requete
        Request request = new Request.Builder().url(url).post(body).build();
        // Executionde la requête
        Response response = client.newCall(request).execute();
        // Analyse du code retour
        if (response.code() == 204) {
            return null;
        } else if (response.code() != 200) {
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        } else {
            // Résultat de la requete.
            return response.body().string();
        }
    }
}
