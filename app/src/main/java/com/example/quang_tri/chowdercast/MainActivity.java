package com.example.quang_tri.chowdercast;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Button startButton, testButton;
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        addButton();
    }

    private void addButton(){
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.quang_tri.chowdercast.WebviewActivity");
                        startActivity(intent);
                    }
                }
        );

        testButton = (Button) findViewById(R.id.test);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://openload.co/stream/S5rcrkUS8JU~1501814182~70.29.0.0~DrHc21bD?mime=true";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(url), "video/*");
                startActivity(i);
            }
        });
    }

    public boolean isAd(String url) {
        Uri httpUrl = Uri.parse(url);
        return isAdHost(httpUrl != null ? httpUrl.getHost() : "");
    }

    private boolean isAdHost(String host) {
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        Ad results = realm.where(Ad.class).equalTo("hostname", host).findFirst();
        int index = host.indexOf(".");
        return index >= 0 && (results!=null ||
                index + 1 < host.length() && isAdHost(host.substring(index + 1)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
