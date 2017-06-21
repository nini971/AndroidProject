package com.example.applimap.model;

import android.util.Log;

import com.example.applimap.model.beans.Direction;
import com.example.applimap.model.beans.Station;
import com.example.applimap.model.beans.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Nicolas Th on 20/06/2017.
 */

public class WebserviceUtils {
    private static final String API_DIRECTION = "AIzaSyAaaXbbXxwRk-sZT1y5H_Bc8Ov_-jN6du0";
    private static final String API_VELO = "02ba962f4dabb189890dd72b826f4c5489591d61";
    private static final String URL_VELO = "https://api.jcdecaux.com/vls/v1/stations?contract=toulouse&apiKey=" + API_VELO;
    private static String urlDirection = "https://maps.googleapis.com/maps/api/directions/json?"; //origin=" + origin + "&destination=" + destination + "&key=" + API_DIRECTION;
    private static final Gson GSON = new Gson();


    public static ArrayList<Station> getStationByCity() throws Exception {

        String reponse = OkHttpUtils.sendGetOkHttpRequest(URL_VELO);
        ArrayList<Station> listStation = GSON.fromJson(reponse, new TypeToken<ArrayList<Station>>() {
        }.getType());

        return listStation;
    }

    public static ArrayList<Step> getTrajet(String origin, String destination) throws Exception {
        String url = urlDirection + "origin=" + origin + "&destination=" + destination + "&key=" + API_DIRECTION;
        String reponse = OkHttpUtils.sendGetOkHttpRequest(url);
        //Log.w("tag", "Resulta requete : " + reponse);
        Direction direction = GSON.fromJson(reponse, Direction.class);
        Log.w("tag", "status : " + direction.getStatus());
        Log.w("tag", "status : " + direction.getRoutes().get(0).getLegs().get(0).getSteps().size());
        ArrayList<Step> listStep = (ArrayList<Step>) direction.getRoutes().get(0).getLegs().get(0).getSteps();
        return listStep;
    }
}
