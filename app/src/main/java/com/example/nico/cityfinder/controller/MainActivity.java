package com.example.nico.cityfinder.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nico.cityfinder.R;
import com.example.nico.cityfinder.model.ControllerUtils;
import com.example.nico.cityfinder.model.RequestCityAT;
import com.example.nico.cityfinder.model.Result;
import com.example.nico.cityfinder.view.CityAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CityAdapter.OnCityListener, RequestCityAT.OnGetCityRequest {


    //------------------
    //      ATTRIBUT
    //------------------

    private final String listForbiden[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", ",", ":", "!", "?", ";", ")", "(", "+", "/", "*", "_"};
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
            // Handle clicks for btFindCity

            String text = etUrl.getText().toString();
            Log.w("Tag_info", " text = " + text);

            // TEST DE LA CHAINE DE CARACTERE
            Pair<ControllerUtils.Control, RequestCityAT.Type> result = ControllerUtils.controllRequestCity(text);

            // CHAINE VALIDER
            if (result.first == ControllerUtils.Control.VALID) {

                // CHAINE EST UN STRING
                if (result.second == RequestCityAT.Type.NAME) {
                    if (requestCityAT == null || requestCityAT.getStatus() == AsyncTask.Status.FINISHED) {
                        requestCityAT = new RequestCityAT(RequestCityAT.Type.NAME, text, this);
                        requestCityAT.execute();
                    }

                    // CHAINE EST UN NOMBRE
                } else if (result.second == RequestCityAT.Type.CP) {

                    if (requestCityAT == null || requestCityAT.getStatus() == AsyncTask.Status.FINISHED) {
                        requestCityAT = new RequestCityAT(RequestCityAT.Type.CP, text, this);
                        requestCityAT.execute();
                    }
                }

                // CHAINE INVALIDE
            } else if (result.first == ControllerUtils.Control.WRONG) {
                Toast.makeText(this, "Il y a un caractère incorrect.", Toast.LENGTH_SHORT).show();

                // CHAINE EMPTY
            } else if (result.first == ControllerUtils.Control.EMPTY) {
                Toast.makeText(this, "Le champ 'ville' est vide.", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(this, CityMapActivity.class);
            intent.putExtra("city", city);
            startActivity(intent);
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
}
