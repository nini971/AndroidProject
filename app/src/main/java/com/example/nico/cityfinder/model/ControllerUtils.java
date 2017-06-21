package com.example.nico.cityfinder.model;

import android.util.Pair;

import com.example.nico.cityfinder.model.beans.TechnicalException;

/**
 * Created by Nico on 17/06/2017.
 */

public class ControllerUtils {

    public enum Control {VALID, EMPTY, WRONG}

    private static final String listForbiden[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", ",", ":", "!", "?", ";", ")", "(", "+", "/", "*", "_"};

    public static RequestCityAT.Type sendRequestCity(String request, OnGetCityRequest onGetCityRequest) {

        Pair<ControllerUtils.Control, RequestCityAT.Type> result = controllRequestCity(request);

        // CHAINE VALIDER
        if (result.first == ControllerUtils.Control.VALID) {

            return result.second;

            // CHAINE INVALIDE
        } else if (result.first == ControllerUtils.Control.WRONG) {
            onGetCityRequest.onRequestError(new TechnicalException("Nom ou code postal invalide "));
            //return null;

            // CHAINE EMPTY
        } else if (result.first == ControllerUtils.Control.EMPTY) {
            onGetCityRequest.onRequestError(new TechnicalException("Le champ ville est vite !"));
            //return null;
        }
        return null;
    }

    public static Pair<Control, RequestCityAT.Type> controllRequestCity(String requestCity) {
        if (requestCity.length() != 0) {
            Integer cp = null;
            try {
                cp = Integer.parseInt(requestCity);
            } catch (NumberFormatException e) {
            }

            if (cp != null) {
                // TEXT IS A NUMBER
                return new Pair<>(Control.VALID, RequestCityAT.Type.CP);

            } else {
                // NOT A NUMBER
                for (int i = 0; i < listForbiden.length; i++) {
                    if (requestCity.indexOf(listForbiden[i]) >= 0) {
                        return new Pair<>(Control.WRONG, RequestCityAT.Type.NAME);
                    }
                }
                return new Pair<>(Control.VALID, RequestCityAT.Type.NAME);
            }
        } else {
            // TEXT IS EMPTY
            return new Pair<>(Control.EMPTY, RequestCityAT.Type.NAME);
        }
    }
}

