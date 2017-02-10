package com.example.quang_tri.chowdercast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Button startButton, fillDBButton;
    private Realm realm;
    private Document doc;
    private ArrayList<String> linkToSrc;

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
                        try {
                            linkToSrc = new linkToSrcAsync().execute("http://www.toonova.net/chowder").get();
                            //Toast.makeText(MainActivity.this, linkToSrc.get(0), Toast.LENGTH_SHORT).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        try {
                            new srcLinkAsync().execute(linkToSrc).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}
