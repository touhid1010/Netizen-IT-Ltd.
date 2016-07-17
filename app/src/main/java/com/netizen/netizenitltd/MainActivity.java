package com.netizen.netizenitltd;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    WebView webView_main;

    ImageButton imageButton_back,
            imageButton_forward;

    final String url = "http://www.netizenbd.com/";


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


        imageButton_back = (ImageButton) findViewById(R.id.imageButton_back);
        imageButton_forward = (ImageButton) findViewById(R.id.imageButton_forward);
        imageButton_back.setOnClickListener(this);
        imageButton_forward.setOnClickListener(this);

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


//        // Add these lines(setWebChromeClient) before loading HTML content to your WebView to show youtube video
//        webView_main.setWebChromeClient(new WebChromeClient() {
//        });

        webView_main.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("market://") || url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("vnd:youtube")) { //url.startsWith("vnd:youtube")||
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
        webView_main.loadUrl(url); // To access assets folder file:///android_asset/*


        //  Progress bar
        final ProgressBar Pbar;
        Pbar = (ProgressBar) findViewById(R.id.progressBar1);

        webView_main.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && Pbar.getVisibility() == ProgressBar.GONE) {
                    Pbar.setVisibility(ProgressBar.VISIBLE);
                }
                Pbar.setProgress(progress);
                if (progress == 100) {
                    Pbar.setVisibility(ProgressBar.GONE);
                }
            }
        });


    } // end of onCreate


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Want to exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_feedback) {
            Intent intent = MyMenuActions.MenuFeedback();
            startActivity(Intent.createChooser(intent, "Send Feedback:"));

            return true;
        }

        if (id == R.id.action_rate) {
            Intent intent = MyMenuActions.MenuRate(MainActivity.this);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
            }

            return true;
        }

        if (id == R.id.action_share) {
            startActivity(MyMenuActions.MenuShare(MainActivity.this));

            return true;
        }

        if (id == R.id.action_moreApps) {
            Intent intent = MyMenuActions.MenuMoreApps();
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
            }

            return true;
        }

        if (id == R.id.action_about) {

            AlertDialog alertDialog = MyMenuActions.MenuAbout(this);
            alertDialog.show();
            ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance()); // to make clickable link

            return true;
        }

        if (id == R.id.action_exit) {
            MyMenuActions.MenuExit(this).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageButton_back:
                webView_main.goBack();
                break;

            case R.id.imageButton_forward:
                webView_main.goForward();
                break;

        }

    }


    @Override
    public void onPause() {
        webView_main.onPause(); // pause the video which is playing with WebView
        super.onPause();
    }

    @Override
    public void onResume() {
        webView_main.onResume(); // implement the onResume() method or the second time don't work
        super.onResume();
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
                        MyMenuActions.MenuExit(this).show();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
