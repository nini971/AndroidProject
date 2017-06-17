package com.example.nico.cityfinder.model;

import java.io.Serializable;

/**
 * Created by Nico on 17/06/2017.
 */

public class Result implements Serializable {

    //------------------
    // ATTRIBUT
    //------------------

    private String ville;
    private String cp;

    //------------------
    //  CONSTRUCTOR
    //------------------

    public Result(String ville, String cp) {
        this.ville = ville;
        this.cp = cp;
    }

    //------------------
    //  GETTER / SETTER
    //------------------

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
}
