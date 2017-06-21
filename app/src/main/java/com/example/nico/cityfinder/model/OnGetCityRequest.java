package com.example.nico.cityfinder.model;

import com.example.nico.cityfinder.model.beans.TechnicalException;

import java.util.ArrayList;

/**
 * Created by Nicolas Th on 20/06/2017.
 */

public interface OnGetCityRequest {
    void onRequestEnd(ArrayList<Result> listCity);

    void onRequestError(TechnicalException e);

}
