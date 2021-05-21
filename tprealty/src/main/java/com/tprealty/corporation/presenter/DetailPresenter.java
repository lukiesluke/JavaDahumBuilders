package com.tprealty.corporation.presenter;

import android.content.Context;

public class DetailPresenter {

    private final Context context;
    private final IDetail view;

    public DetailPresenter(Context context, IDetail view) {
        this.context = context;
        this.view = view;
    }

    public void ready() {
        view.init();
    }
}
