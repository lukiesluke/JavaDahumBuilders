package com.dahumbuilders.presenter;

import com.dahumbuilders.model.Summary;

import java.util.List;

public interface ISummary {
    void init();

    void requestFirebaseOnDataChange(List<Summary> summaryList);

    void requestFirebaseOnDataChangeLog(String datetimeLog, String userInfo);
}
