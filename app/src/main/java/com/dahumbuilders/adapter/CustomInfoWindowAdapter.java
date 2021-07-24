package com.dahumbuilders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dahumbuilders.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View view;

    public CustomInfoWindowAdapter(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        String snippet = marker.getSnippet();
        TextView tvTitle = view.findViewById(R.id.title);
        TextView tvSnippet = view.findViewById(R.id.snippet);

        if (!title.isEmpty()) {
            tvTitle.setText(title);
        }

        if (!snippet.isEmpty()) {
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, view);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, view);
        return view;
    }
}
