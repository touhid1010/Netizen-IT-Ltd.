package com.netizen.netizenitltd;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
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

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomBar mBottomBar;

    WebView webView_main;

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



        /**
         * Bottom bar
         */
        myBottomBar(savedInstanceState);








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

//        switch (v.getId()) {
//            case R.id.imageButton_back:
//                webView_main.goBack();
//                break;
//
//            case R.id.imageButton_forward:
//                webView_main.goForward();
//                break;
//
//        }

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


    public void myBottomBar(Bundle savedInstanceState) {

        // bottom bar
        mBottomBar = BottomBar.attach(this, savedInstanceState);

        // Disable the left bar on tablets and behave exactly the same on mobile and tablets instead.
        mBottomBar.noTabletGoodness();

        // Show all titles even when there's more than three tabs.
        mBottomBar.useFixedMode();
        mBottomBar.noTopOffset();

        mBottomBar.setItems(R.menu.bottom_bar_menu);

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItem_rateUs) {
                    Intent intent = MyMenuActions.MenuRate(MainActivity.this);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItemId == R.id.bottomBarItem_moreApps) {
                    Intent intent = MyMenuActions.MenuMoreApps();
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItemId == R.id.bottomBarItem_aboutUs) {
                    AlertDialog alertDialog = MyMenuActions.MenuContactUs(MainActivity.this);
                    alertDialog.show();
                    ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance()); // to make clickable link
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItem_rateUs) {
                    Intent intent = MyMenuActions.MenuRate(MainActivity.this);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItemId == R.id.bottomBarItem_moreApps) {
                    Intent intent = MyMenuActions.MenuMoreApps();
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_google_play_not_installed, Toast.LENGTH_LONG).show();
                    }
                }

                if (menuItemId == R.id.bottomBarItem_aboutUs) {
                    AlertDialog alertDialog = MyMenuActions.MenuContactUs(MainActivity.this);
                    alertDialog.show();
                    ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance()); // to make clickable link
                }
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");




        // Use the dark theme.
        mBottomBar.useDarkTheme();
        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        mBottomBar.setActiveTabColor("#009688");

        // Use custom text appearance in tab titles.
        mBottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
//        mBottomBar.setTypeFace("MyFont.ttf");


    }

}
