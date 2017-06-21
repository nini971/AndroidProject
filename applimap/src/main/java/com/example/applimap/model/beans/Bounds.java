package com.example.applimap.model.beans;

/**
 * Created by Nicolas Th on 21/06/2017.
 */

public class Bounds {
    private Northeast northeast;
    private Southwest southwest;

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }
}
