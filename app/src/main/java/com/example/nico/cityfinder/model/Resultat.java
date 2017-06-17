package com.example.nico.cityfinder.model;

import java.util.ArrayList;

/**
 * Created by Nico on 17/06/2017.
 */

public class Resultat {

    //------------------
    //   ATTRIBUT
    //------------------

    private ArrayList<Result> results = null;
    private Integer nbr;

    //------------------
    //  CONSTRUCTOR
    //------------------

    public Resultat(ArrayList<Result> results, Integer nbr) {
        this.results = results;
        this.nbr = nbr;
    }

    //------------------
    //  GETTER / SETTER
    //------------------

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public Integer getNbr() {
        return nbr;
    }

    public void setNbr(Integer nbr) {
        this.nbr = nbr;
    }
}
