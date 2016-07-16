package com.netizen.netizenitltd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    WebView webView_main;

    Button button_moreApps,
            button_rateUs,
            button_aboutUs;

    final String url = "http://www.google.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         * Initialization
         */
        webView_main = (WebView) findViewById(R.id.webView_main);

        button_moreApps = (Button) findViewById(R.id.button_moreApps);
        button_rateUs = (Button) findViewById(R.id.button_rateUs);
        button_aboutUs = (Button) findViewById(R.id.button_aboutUs);
        button_moreApps.setOnClickListener(this);
        button_rateUs.setOnClickListener(this);
        button_aboutUs.setOnClickListener(this);

        webView_main.loadUrl(url);

        WebSettings webSettings = webView_main.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Improve WebView performance
        webView_main.setWebChromeClient(new WebChromeClient());
        webView_main.setInitialScale(1);
        webView_main.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView_main.setScrollbarFadingEnabled(false);
        webView_main.canGoBack();
        webView_main.canGoForward();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);


        // WebChromeClient use Before HTML loading to support html5 video
        webView_main.setWebChromeClient(new WebChromeClient() {
        });

        webView_main.loadUrl(url); // To access assets folder file:///android_asset/*
        webView_main.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("market://") || url.startsWith("tel:") || url.startsWith("mailto:")) { //url.startsWith("vnd:youtube")||
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    view.loadUrl(url);
                    return true;
                }
            }
        });




    } // end of onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_moreApps:
                Toast.makeText(MainActivity.this, "More apps", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_rateUs:
                Toast.makeText(MainActivity.this, "Rate us", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_aboutUs:
                Toast.makeText(MainActivity.this, "About us", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        webView_main.onPause(); // pause the video which is playing with WebView
    }

    @Override
    public void onResume() {
        super.onResume();
        webView_main.onResume(); // implement the onResume() method or the second time don't work
    }

    // Go to previous page when pressing back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView_main.canGoBack()) {
                        webView_main.goBack();
                    } else {
                        finish();
                        webView_main.destroy();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
