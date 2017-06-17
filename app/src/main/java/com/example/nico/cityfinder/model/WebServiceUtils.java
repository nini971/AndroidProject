package com.example.nico.cityfinder.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Nico on 17/06/2017.
 */

public class WebServiceUtils {
    private static final String URL = "http://www.citysearch-api.com/fr/city?login=cityfinder&apikey=so1e824d9070a669c0d8b0ff399fa0b6744faf50f9&";
    private static final String URL_GETBYCP = URL + "cp=";
    private static final String URL_GETBYNAME = URL + "ville=";
    private static final Gson GSON = new Gson();

    public static ArrayList<Result> getCityByCp(String cp) throws Exception {
        Resultat resultat;
        String reponse = OkHttpUtils.sendGetOkHttpRequest(URL_GETBYCP + cp);
        System.out.println("reception ok : " + reponse);
        resultat = GSON.fromJson(reponse, Resultat.class);
        Log.w("Tag_info", resultat.getResults().toString());
        return resultat.getResults();
    }

    public static ArrayList<Result> getCityByName(String name) throws Exception {
        Resultat resultat;
        String reponse = OkHttpUtils.sendGetOkHttpRequest(URL_GETBYNAME + name);
        System.out.println("reception ok : " + reponse);
        resultat = GSON.fromJson(reponse, Resultat.class);
        return resultat.getResults();
    }
}
