package com.example.quang_tri.chowdercast;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class WebView extends AppCompatActivity {

    private android.webkit.WebView webview;
    private Realm realm;
    private Map<String, Boolean> loadedUrls = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        realm = Realm.getDefaultInstance();
        webview = (android.webkit.WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                if(url.endsWith("ck2")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.parse(url), "video/*");
                    startActivity(i);
                    return true;
                } else {
                    //Toast.makeText(WebView.this, url, Toast.LENGTH_LONG).show();
                    return false;
                }
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
        webview.loadUrl("http://kisscartoon.se/Cartoon/Chowder");
    }
}
