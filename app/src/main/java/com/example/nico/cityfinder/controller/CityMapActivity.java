package com.example.nico.cityfinder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.nico.cityfinder.R;
import com.example.nico.cityfinder.model.Result;

public class CityMapActivity extends AppCompatActivity {

    private WebView webView;
    private Intent intent;
    private Result city;
    private final String URL_MAP = "https://www.google.fr/maps/place/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_map);
        webView = (WebView) findViewById(R.id.webview);
        intent = getIntent();

        city = (Result) intent.getSerializableExtra("city");
        webView.loadUrl(URL_MAP + city.getCp() + "+" + city.getVille());
        webView.getSettings().setJavaScriptEnabled(true);
        WebViewClient webViewClient = new WebViewClient();
        webView.setWebViewClient(webViewClient);

    }
}
