package com.example.applimap.model;

import com.example.applimap.model.beans.Station;
import com.example.applimap.model.beans.Step;

import java.util.ArrayList;

/**
 * Created by Nicolas Th on 20/06/2017.
 */

public interface OnResquestStation {
    void onResquestStationEnd(ArrayList<Station> listStaion);

    void onResquestStationEndError(Exception e);

    void onResquestDirection(ArrayList<Step> listStep);
}
