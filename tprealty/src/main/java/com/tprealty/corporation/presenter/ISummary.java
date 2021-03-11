package com.tprealty.corporation.presenter;

import java.util.List;

import model.Summary;

public interface ISummary {
    void init();

    void requestFirebaseOnDataChange(List<Summary> summaryList);
}
