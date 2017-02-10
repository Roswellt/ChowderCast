package com.example.quang_tri.chowdercast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Button startButton, fillDBButton;
    private Realm realm;
    private Document doc;

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
                        //TODO
                    }
                }
        );
        fillDBButton = (Button) findViewById(R.id.fillDB);
        fillDBButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new JsoupAsync().execute("http://www.toonova.net/chowder");
                    }
                }
        );
    }
}
