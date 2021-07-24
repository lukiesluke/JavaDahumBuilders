package com.tprealty.corporation.presenter;

import java.util.List;

import com.tprealty.corporation.model.Summary;

public interface ISummary {
    void init();

    void requestFirebaseOnDataChange(List<Summary> summaryList);

    void requestFirebaseOnDataChangeLog(String datetimeLog, String userInfo);
}
