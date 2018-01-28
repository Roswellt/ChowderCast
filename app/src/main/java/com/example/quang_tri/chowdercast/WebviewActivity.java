package com.example.quang_tri.chowdercast;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class WebviewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private android.webkit.WebView webview;
    private Realm realm;
    private Map<String, Boolean> loadedUrls = new HashMap<>();
    private String htmlUrl, absoluteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        realm = Realm.getDefaultInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Configuring webview
        htmlUrl = "https://yesmovies.to/";
        String newUA= "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";
        webview = (android.webkit.WebView) findViewById(R.id.webview);
        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUserAgentString(newUA);
        webview.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                if(url.contains("yesmovies")) {
                    return false;
                } else return true;
                /*
                if(url.endsWith("ck2") || url.contains("redirector.googlevideo.com")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.parse(url), "video/*");
                    startActivity(i);
                    return true;
                } else {
                    Toast.makeText(WebviewActivity.this, url, Toast.LENGTH_LONG).show();
                    return false;
                }
                */
            }


            @Override
            public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, String url) {
                boolean ad;
                if (!loadedUrls.containsKey(url)) {
                    ad = Adblocker.AdBlocker.isAd(url);
                    loadedUrls.put(url, ad);
                } else {
                    ad = loadedUrls.get(url);
                }
                return ad ? Adblocker.AdBlocker.createEmptyResource() :
                        super.shouldInterceptRequest(view, url);
                }
            @Override
            public void onLoadResource(WebView view, String url)  {
                super.onLoadResource(view, url);
                //WORKS!!
                if(url.contains("key=ck2") || url.contains("3.bp.blogspot.com")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.parse(url), "video/*");
                    startActivity(i);
                }
            }
            });
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl(htmlUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.clearCache(true);
        if(webview!=null) {
            webview = null;
        }
    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document htmlDocument = Jsoup.connect(htmlUrl).get();
                Element imageElement = htmlDocument.select(".jw-video.jw-reset").first();
                absoluteUrl = imageElement.absUrl("src");  //absolute URL on src

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(WebviewActivity.this, absoluteUrl, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (webview.copyBackForwardList().getCurrentIndex() > 0) {
            webview.goBack();
        }
        else {
            // Your exit alert code, or alternatively line below to finish
            super.onBackPressed(); // finishes activity
        }
    }


    //Drawer layout override methods
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
