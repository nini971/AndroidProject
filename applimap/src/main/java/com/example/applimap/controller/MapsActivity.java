package com.example.applimap.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applimap.R;
import com.example.applimap.model.OnResquestStation;
import com.example.applimap.model.RequestStationAT;
import com.example.applimap.model.beans.Station;
import com.example.applimap.model.beans.Step;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, OnResquestStation, View.OnClickListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ArrayList<Station> listStation;
    private RequestStationAT requestStationAT = null;
    private LatLng toulouse;
    private Button bt;
    private TextView tvTitle;
    private TextView tvNbvelo;
    private TextView tvNbplace;
    private LocationManager locationMgr;
    private Location location;


    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //Minimum (et non égale, c’est Android qui gère) 5 secondes et 200m de difference
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationMgr.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 200, this);
        if (locationMgr.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 200, this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        bt = (Button) findViewById(R.id.bt);

        bt.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        toulouse = new LatLng(43.60, 1.44);

        listStation = new ArrayList<>();

        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        if (requestStationAT == null || requestStationAT.getStatus() == AsyncTask.Status.FINISHED) {
            requestStationAT = new RequestStationAT(this, RequestStationAT.TypeAT.STATION);
            requestStationAT.execute();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                getLocation();
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toulouse, 12));
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);
        //googleMap.addMarker(new MarkerOptions().position(toulouse).title("Marker in toulouse"));
        //refreshMarker();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getLocation();
        }


    }

    private void refreshMarker() {

    }

    @Override
    public void onResquestStationEnd(ArrayList<Station> listStaion) {

        //Log.w("tag", "contenu" + listStaion.toString());
        for (int i = 0; i < listStaion.size(); i++) {
            Station toulouse = listStaion.get(i);
            MarkerOptions markerStation = new MarkerOptions();
            markerStation.position(new LatLng(toulouse.getPosition().getLat(), toulouse.getPosition().getLng()));
            markerStation.title(toulouse.getName());

            if (toulouse.getStatus().equals("OPEN")) {
                // OUVERT
                if (toulouse.getAvailable_bikes() > 0) {
                    // VELO DISPO
                    if (toulouse.getAvailable_bike_stands() > 0) {
                        // PLACE DISPO
                        // VERT
                        markerStation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    } else {
                        // PLACE PAS DISPO
                        // BLEU
                        markerStation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    }
                } else {
                    // VELO PAS DISPO MAIS PLACE DISPO
                    // ORANGE
                    markerStation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }
            } else {
                // FERMER
                // VIOLET
                markerStation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            }
            mMap.addMarker(markerStation).setTag(toulouse);
        }
    }

    @Override
    public void onResquestStationEndError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResquestDirection(ArrayList<Step> listStep) {
        //Toast.makeText(this, "" + listPolyline.size(), Toast.LENGTH_SHORT).show();
        ArrayList<LatLng> startListlatlng = new ArrayList<>();
        ArrayList<LatLng> endListlatlng = new ArrayList<>();
        for (Step s : listStep) {
            startListlatlng.add(new LatLng(s.getStart_location().getLat(), s.getStart_location().getLng()));
            endListlatlng.add(new LatLng(s.getEnd_location().getLat(), s.getEnd_location().getLng()));
        }
        mMap.addPolyline(new PolylineOptions().addAll(startListlatlng));
    }


    @Override
    public void onClick(View v) {
        if (requestStationAT == null || requestStationAT.getStatus() == AsyncTask.Status.FINISHED) {
            requestStationAT = new RequestStationAT(this, RequestStationAT.TypeAT.STATION);
            requestStationAT.execute();
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(this).inflate(R.layout.marker_station, null);

        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvNbvelo = (TextView) view.findViewById(R.id.tv_nbvelo);
        tvNbplace = (TextView) view.findViewById(R.id.tv_nbplace);

        Station station = (Station) marker.getTag();

        tvTitle.setText(station.getName());
        tvNbvelo.setText(station.getAvailable_bikes() + "");
        tvNbplace.setText(station.getAvailable_bike_stands() + "");
        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (requestStationAT == null || requestStationAT.getStatus() == AsyncTask.Status.FINISHED) {
            requestStationAT = new RequestStationAT(this, RequestStationAT.TypeAT.DIRECTION, this.location.getLatitude() + "," + this.location.getLongitude(), marker.getPosition().latitude + "," + marker.getPosition().longitude);
            requestStationAT.execute();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
