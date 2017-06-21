package com.example.applimap.model.beans;

import java.util.List;


/**
 * Created by Nicolas Th on 21/06/2017.
 */

public class Direction {
    private List<Geocoded_waypoint> geocoded_waypoints = null;
    private List<Route> routes = null;
    private String status;

    public List<Geocoded_waypoint> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<Geocoded_waypoint> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
