package com.dahumbuilders;

import com.dahumbuilders.model.Summary;

import java.util.List;

public interface ISummary {
    void init();

    void requestFirebaseOnDataChange(List<Summary> summaryList);
}
