package com.example.quang_tri.chowdercast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Realm realm = Realm.getDefaultInstance();
    }
}
