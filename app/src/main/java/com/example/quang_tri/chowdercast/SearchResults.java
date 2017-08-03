package com.example.quang_tri.chowdercast;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SearchResults extends AppCompatActivity {

    private CustomAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Episodes> query = realm.where(Episodes.class);
        RealmResults<Episodes> results = query.findAll();
        adapter = new CustomAdapter(this, results);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent("com.example.quang_tri.chowdercast.WebviewActivity");
                Episodes ep = (Episodes) parent.getItemAtPosition(position);
                String link = ep.getLink();
                intent.putExtra("link", link);


                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setDataAndType(Uri.parse(link), "video/*");
                startActivity(intent);
            }
        });
    }
}
