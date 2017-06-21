package com.example.applimap.model;

import android.os.AsyncTask;

import com.example.applimap.model.beans.Station;
import com.example.applimap.model.beans.Step;

import java.util.ArrayList;

import static com.example.applimap.model.RequestStationAT.TypeAT.STATION;

/**
 * Created by Nicolas Th on 20/06/2017.
 */

public class RequestStationAT extends AsyncTask {
    public enum TypeAT {STATION, DIRECTION}

    private Exception e;
    private ArrayList<Station> listStation;
    private ArrayList<Step> listStep;
    private OnResquestStation onResquestStation;
    private TypeAT typeAT;
    private String origin;
    private String destination;

    public RequestStationAT(OnResquestStation onResquestStation, TypeAT typeAT, String origin, String destination) {
        this.onResquestStation = onResquestStation;
        this.typeAT = typeAT;
        this.origin = origin;
        this.destination = destination;
    }

    public RequestStationAT(OnResquestStation onResquestStation, TypeAT typeAT) {
        this.onResquestStation = onResquestStation;
        this.typeAT = typeAT;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (typeAT == STATION) {
            try {
                listStation = WebserviceUtils.getStationByCity();
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }
            return listStation;
        } else if (typeAT == TypeAT.DIRECTION) {
            try {
                listStep = WebserviceUtils.getTrajet(origin, destination);
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }
            return listStep;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (typeAT == STATION) {
            if (e != null) {
                onResquestStation.onResquestStationEndError(e);
            } else {
                ArrayList<Station> temp = (ArrayList<Station>) o;
                onResquestStation.onResquestStationEnd(temp);
            }
        } else if (typeAT == TypeAT.DIRECTION) {
            if (e != null) {
                onResquestStation.onResquestStationEndError(e);
            } else {
                ArrayList<Step> temp = (ArrayList<Step>) o;
                onResquestStation.onResquestDirection(temp);
            }
        }
    }

}
