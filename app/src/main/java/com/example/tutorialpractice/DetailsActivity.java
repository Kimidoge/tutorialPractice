package com.example.tutorialpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String name = getIntent().getStringExtra("name");  //catch here

        String url = "https://en.m.wikipedia.org/wiki/Android_"+ name.replace(" ", "_");

        WebView webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}