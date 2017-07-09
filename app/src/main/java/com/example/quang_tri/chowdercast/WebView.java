package com.example.quang_tri.chowdercast;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceResponse;
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

public class WebView extends AppCompatActivity {

    private android.webkit.WebView webview;
    private Realm realm;
    private Map<String, Boolean> loadedUrls = new HashMap<>();
    private Button testButton;
    private String htmlUrl, absoluteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        realm = Realm.getDefaultInstance();
        htmlUrl = "https://9anime.to/";
        addButton();
        webview = (android.webkit.WebView) findViewById(R.id.webview);
        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {

            //Commenting out some of this part so I can test Jsoup

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                /*
                if(url.endsWith("ck2") || url.contains("redirector.googlevideo.com")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.parse(url), "video/*");
                    startActivity(i);
                    return true;
                } else {
                    Toast.makeText(WebView.this, url, Toast.LENGTH_LONG).show();
                    return false;
                }
                */
                htmlUrl = url;
                Toast.makeText(WebView.this, htmlUrl, Toast.LENGTH_LONG);
                return false;
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
            });
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

    private void addButton() {
        testButton = (Button) findViewById(R.id.test);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });
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
            Toast.makeText(WebView.this, absoluteUrl, Toast.LENGTH_LONG).show();
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
}
