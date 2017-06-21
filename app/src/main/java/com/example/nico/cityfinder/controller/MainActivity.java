package com.example.nico.cityfinder.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nico.cityfinder.R;
import com.example.nico.cityfinder.model.ControllerUtils;
import com.example.nico.cityfinder.model.OnGetCityRequest;
import com.example.nico.cityfinder.model.RequestCityAT;
import com.example.nico.cityfinder.model.Result;
import com.example.nico.cityfinder.model.beans.TechnicalException;
import com.example.nico.cityfinder.view.CityAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CityAdapter.OnCityListener, OnGetCityRequest {


    //------------------
    //      ATTRIBUT
    //------------------

    private EditText etUrl;
    private Button btFindCity;
    private TextView tvNbcity;
    private ArrayList<Result> listCity;
    private CityAdapter cityAdapter;
    private RecyclerView recyclerView;
    private RequestCityAT requestCityAT = null;


    //------------------
    //      METHODE
    //------------------

    private void findViews() {
        etUrl = (EditText) findViewById(R.id.et_url);
        btFindCity = (Button) findViewById(R.id.bt_findCity);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        tvNbcity = (TextView) findViewById(R.id.tv_nbcity);
        btFindCity.setOnClickListener(this);
    }

    //------------------
    //      OVERRIDE
    //------------------

    @Override
    public void onClick(View v) {
        if (v == btFindCity) {
            String text = etUrl.getText().toString();

            // TEST DE LA CHAINE DE CARACTERE
            RequestCityAT.Type result = ControllerUtils.sendRequestCity(text, this);

            // EXECUTE ASYNTASK
            if (result != null) {
                if (requestCityAT == null || requestCityAT.getStatus() == AsyncTask.Status.FINISHED) {
                    requestCityAT = new RequestCityAT(result, text, this);
                    requestCityAT.execute();
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        listCity = new ArrayList<>();
        listCity.add(new Result("Toulouse", "31000"));
        cityAdapter = new CityAdapter(listCity, this);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onCityClick(Result city) {
        if (city != null) {

            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + city.getVille() + "+" + city.getCp());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse("http://maps.google.com/maps?saddr=Toulouse"));
//            startActivity(intent);

//            Intent intent = new Intent(this, CityMapActivity.class);
//            intent.putExtra("city", city);
//            startActivity(intent);
        }
    }

    @Override
    public void onRequestEnd(ArrayList<Result> listCity) {
        if (listCity != null) {
            this.listCity.clear();
            this.listCity.addAll(listCity);
            String nbville = "Nombre de ville(s) trouvées : " + listCity.size();
            tvNbcity.setText(nbville);
            cityAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Erreur de chargement !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestError(TechnicalException e) {
        Toast.makeText(this, e.getUserMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listCity", listCity);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<Result> temp = savedInstanceState.getParcelableArrayList("listCity");
        listCity.clear();
        if (temp != null) {
            listCity.addAll(temp);
            cityAdapter.notifyDataSetChanged();
        }

    }
}
