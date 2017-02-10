package com.example.quang_tri.chowdercast;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by Quang-Tri on 09/02/2017.
 */

public class CustomAdapter extends RealmBaseAdapter<Episodes> implements ListAdapter {

    private OrderedRealmCollection<Episodes> episodeList;
    private Activity context;

    public CustomAdapter(Activity context, OrderedRealmCollection<Episodes> episodes){
        super(context, episodes);
        this.episodeList = episodes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView text = (TextView) rowView.findViewById(R.id.episode);

        Episodes episodes = episodeList.get(position);
        text.setText(episodes.getLink());

        return rowView;
    }
}
