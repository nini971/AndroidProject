package com.example.nico.cityfinder.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nico.cityfinder.model.beans.TechnicalException;

import java.util.ArrayList;

/**
 * Created by Nico on 17/06/2017.
 */

public class RequestCityAT extends AsyncTask {

    public enum Type {NAME, CP}

    //------------------
    //  ATTRIBUT
    //------------------

    private Type type;
    private String requestCity;
    private OnGetCityRequest onGetCityRequest;
    private Exception e;

    //------------------
    //  CONSTRUCTOR
    //------------------

    public RequestCityAT(Type type, String requestCity, OnGetCityRequest onGetCityRequest) {
        this.type = type;
        this.requestCity = requestCity;
        this.onGetCityRequest = onGetCityRequest;
    }

    //------------------
    //  OVERRIDE
    //------------------

    @Override
    protected Object doInBackground(Object[] params) {
        ArrayList<Result> listCity = null;
        if (type == Type.NAME && requestCity != null) {
            try {
                listCity = WebServiceUtils.getCityByName(requestCity);
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }
        } else if (type == Type.CP && requestCity != null) {
            try {
                listCity = WebServiceUtils.getCityByCp(requestCity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listCity;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (e != null) {
            onGetCityRequest.onRequestError(new TechnicalException(e, "Une erreur est survenue"));
        } else {
            ArrayList<Result> temp = (ArrayList<Result>) o;
            Log.w("Tag", "taille list : " + temp.size());
            onGetCityRequest.onRequestEnd(temp);
        }


    }
}
